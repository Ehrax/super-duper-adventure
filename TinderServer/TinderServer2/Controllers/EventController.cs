using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;
using TinderServer2.Models;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.Owin;
using TinderServer2.Helper;
using System.Drawing;
using System.IO;
using System.Web;
using System.Drawing.Imaging;
using Microsoft.AspNet.Identity.EntityFramework;

namespace TinderServer2.Controllers
{
    [Authorize]
    public class EventController : ApiController
    {
        private ApplicationDbContext db
        {
            get { return Request.GetOwinContext().Get<ApplicationDbContext>(); }
        }
        private ApplicationUserManager UserManager       
        {
           get { return Request.GetOwinContext().GetUserManager<ApplicationUserManager>(); }
        }

        // GET: api/EventModels
        /// <summary>
        /// Get all Events filtered by category or distance from a given location.
        /// All parameters are optional. Distance default is 5 km . If you don't pass values for longitude and/or latitude or the passed values are 
        /// invalid you will get all events.
        /// </summary>
        /// <param name="category"></param>
        /// <param name="longitude"></param>
        /// <param name="latitude"></param>
        /// <param name="distance"></param>
        /// <returns></returns>
        public List<EventModel> GetEvents(string category="all",double longitude=-181,double latitude=-91,int distance = 5000)
        {
            var eventList = db.Events.ToList();
            if (longitude >= -180 && latitude >= -90 && longitude<=180 && latitude<=90)
            {
                eventList = db.Events.ToList().Where(e =>
                        GreatCircleDistance.CalculateDistance(new Location
                        {
                            Longitude = e.Longitude,
                            Latitude = e.Latitude
                        }, new Location
                        {
                            Longitude = longitude,
                            Latitude = latitude
                        }) <= distance
                    ).ToList();
            }
            
            switch (category.ToLower())
            {
                case "all": return eventList;
                case "sport": return eventList.Where(e => e.Category == Categories.Category.Sport).ToList();
                case "erholung": return eventList.Where(e => e.Category == Categories.Category.Erholung).ToList();
                case "kultur": return eventList.Where(e => e.Category == Categories.Category.Kultur).ToList();
                case "ausgehen": return eventList.Where(e => e.Category == Categories.Category.Ausgehen).ToList();
                case "sonstiges": return eventList.Where(e => e.Category == Categories.Category.Sonstiges).ToList();
                default: return eventList;

            }
            
        }

        /// <summary>
        /// Returns the Event with the passed ID. 
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        // GET: api/Event/5
        [ResponseType(typeof(EventModel))]
        public async Task<IHttpActionResult> GetEvent(int id)
        {
            EventModel eventModel = await db.Events.FindAsync(id);
            if (eventModel == null)
            {
                return NotFound();
            }

            return Ok(eventModel);
        }

        // PUT: api/Event/5
        /// <summary>
        /// Updates the Event with the passed id. You have to pass all attributes of this event!
        /// </summary>
        /// <param name="id"></param>
        /// <param name="eventModel"></param>
        /// <returns></returns>
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> PutEvent(int id, EventModel eventModel)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != eventModel.EventModelID)
            {
                return BadRequest();
            }
            if(User.Identity.GetUserId() != eventModel.Creator.Id)
            {
                return Unauthorized();
            }
            db.Entry(eventModel).State = EntityState.Modified;

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!EventModelExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // PUT: api/Event/5/join
        /// <summary>
        /// This method is used to join an event. Just put the id of the event in the request body.
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [HttpPut]
        [Route("api/event/{id}/join")]
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> JoinEvent(int id)
        {
            var currentEvent = db.Events.FirstOrDefault(e => e.EventModelID == id);
            if (currentEvent == null)
            {
                return BadRequest("Das Event ist nicht vorhanden.");
            }
            var currentUser = await UserManager.FindByIdAsync(User.Identity.GetUserId());
            currentEvent.Members.Add(currentUser);
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        // PUT: api/Event/5/leave
        /// <summary>
        /// Use this method to leave an event. 
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [HttpPut]
        [Route("api/event/{id}/leave")]
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> LeaveEvent(int id)
        {
            var currentEvent = db.Events.FirstOrDefault(e => e.EventModelID == id);
            if (currentEvent == null)
            {
                return BadRequest("Das Event ist nicht vorhanden.");
            }
            var currentUser = await UserManager.FindByIdAsync(User.Identity.GetUserId());
            if (currentEvent.Members.FirstOrDefault(m => m.Id == currentUser.Id) == null)
            {
                return BadRequest("Der User ist kein Member dieses Events");
            }
            currentEvent.Members.Remove(currentUser);
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        
        // POST: api/Event
        /// <summary>
        /// This method is used to create a event.
        /// </summary>
        /// <param name="eventModel"></param>
        /// <returns></returns>
        [ResponseType(typeof(EventModel))]
        public async Task<IHttpActionResult> PostEvent(AddEventBindingModel eventModel)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            if(eventModel.timeSpan <= 0)
            {
                return BadRequest("Zeitspanne muss groesser 0 sein");
            }

            var currentEvent = new EventModel {
                Title = eventModel.Title,
                Latitude = eventModel.Latitude,
                Longitude = eventModel.Longitude,
                MaxUsers = eventModel.MaxUsers
            };

            if (!String.IsNullOrEmpty(eventModel.Description))
            {
                currentEvent.Description = eventModel.Description;
            }
            currentEvent.Category = (Categories.Category) eventModel.Category;
            currentEvent.EndDate = DateTime.Now.AddMilliseconds(eventModel.timeSpan);

            var currentUser = UserManager.FindById(User.Identity.GetUserId());

            currentEvent.Creator = currentUser;
            currentEvent.Members.Add(currentUser);
            currentEvent.UserCounter = 1;

            if (!String.IsNullOrEmpty(eventModel.ImageBase64))
            {
                var imageBytes = Convert.FromBase64String(ImageHelper.FixBase64ForImage(eventModel.ImageBase64));

                string extension = ImageHelper.GetExtension(imageBytes);

                if (extension.Equals(String.Empty))
                {
                    return BadRequest("NotAImage");
                }

                string appPath = HttpContext.Current.Request.MapPath(HttpContext.Current.Request.ApplicationPath);
                string filePath = appPath + "\\images\\events\\" + eventModel.Title +"_"+ ImageHelper.createName() + extension;

                ImageHelper.SaveImage(imageBytes,filePath);

                var imageModel = new ImageModel { Path = filePath };
                db.Images.Add(imageModel);
                await db.SaveChangesAsync();

                currentEvent.EventImage = imageModel;

            }

            db.Events.Add(currentEvent);
            await db.SaveChangesAsync();

            return CreatedAtRoute("DefaultApi", new { id = currentEvent.EventModelID }, eventModel);
        }
        

        // DELETE: api/Event/5
        [ResponseType(typeof(EventModel))]
        public async Task<IHttpActionResult> DeleteEvent(int id)
        {
            EventModel eventModel = await db.Events.FindAsync(id);
            if (eventModel == null)
            {
                return NotFound();
            }
            
            if(User.Identity.GetUserId() != eventModel.Creator.Id)
            {
                return Unauthorized();
            }

            db.Events.Remove(eventModel);
            await db.SaveChangesAsync();

            return Ok(eventModel);
        }

        [Route("api/event/autodelete")]
        [HttpDelete]
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> AutoDeleteEvent()
        {
            var EventsToDelete = db.Events.Where(e => e.EndDate.CompareTo(DateTime.Now) <= 0).ToList();
            foreach (var currentEvent in EventsToDelete)
            {
                db.Events.Remove(currentEvent);
            }

            return StatusCode(HttpStatusCode.NoContent);

        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool EventModelExists(int id)
        {
            return db.Events.Count(e => e.EventModelID == id) > 0;
        }
    }
}