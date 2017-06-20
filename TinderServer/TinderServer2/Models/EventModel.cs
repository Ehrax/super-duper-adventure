using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

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
        public DateTime EndDate { get; set; }
        public int UserCounter { get; set; }
        public int MaxUsers { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
        public Categories.Category Category { get; set; }
        public ApplicationUser Creator { get; set; }
        public virtual ICollection<ApplicationUser> Members { get; set; }
        public ImageModel EventImage { get; set; }


    }
}