using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;
using TinderServer2.Helper;

namespace TinderServer2.Models
{
    public class EventModel
    {
        public EventModel()
        {
            Members = new List<ApplicationUser>();
        }
      
        public int EventModelID { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public DateTime StartDate { get; set; }
        public int UserCounter { get; set; }
        public int MaxUsers { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
        public string Location { get; set; }
        public Categories.Category Category { get; set; }
        public String CreatorID { get; set; }
        [ForeignKey("CreatorID")]
        public virtual ApplicationUser Creator { get; set; }

        public virtual ICollection<ApplicationUser> Members { get; set; }
        public string ImagePath { get; set; }

        public void UpdateEvent  (UpdateEventBindingModel updateEventModel)
        {
            if (String.Empty.Equals(updateEventModel.ImageBase64))
            {
                ImagePath = null;
            }
            else if (updateEventModel.ImageBase64 != null)
            {
                string filePath = ImageHelper.SaveBase64ImageToFileSystem(updateEventModel.ImageBase64, ImageHelper.ImageType.EVENTIMAGE, Title + "_" + Creator.Id);
                if (filePath.Equals("NotAImage"))
                {
                    throw new InvalidCastException("NotAImage");
                }
                if (filePath == null)
                {
                    throw new NullReferenceException("Error while uploading the Image, please try again!");
                }
                ImagePath = filePath;
            }

            Title = updateEventModel.Title;
            Description = updateEventModel.Description;
            StartDate = updateEventModel.StartDate;
            MaxUsers = updateEventModel.MaxUsers;
            Latitude = updateEventModel.Latitude;
            Longitude = updateEventModel.Longitude;
            Category = updateEventModel.Category;
            Location = updateEventModel.Location;

            
        }

    }
}