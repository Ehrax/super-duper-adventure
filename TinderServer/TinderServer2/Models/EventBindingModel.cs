using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;
using Newtonsoft.Json;
using TinderServer2.Helper;

namespace TinderServer2.Models
{
    //This model is used to verify if all necessary information is given to create 
    //a new event.
    public class AddEventBindingModel
    {
        [Required]
        public string Title { get; set; }
        public string Description { get; set; }

        [Required]
        public long timeSpan { get; set; }

        [Required]
        [Range(2, 100)]
        public int MaxUsers { get; set; }

        [Required]
        [Range(-90, 90)]
        public double Latitude { get; set; }

        [Required]
        [Range(-180, 180)]
        public double Longitude { get; set; }

        [Required]
        [Range(0, 4)]
        public int Category { get; set; }

        public string ImageBase64 { get; set; }
    }

    public class UpdateEventBindingModel
    {
        public int EventModelID { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public DateTime EndDate { get; set; }
        public int MaxUsers { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
        public Categories.Category Category { get; set; }
        public string EventImageBase64 { get; set; }
    }

    public class ReturnEventBindingModel
    {
        public int EventModelID { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public DateTime EndDate { get; set; }
        public int UserCounter { get; set; }
        public int MaxUsers { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
        public Categories.Category Category { get; set; }
        public ReturnApplicationUser Creator { get; set; }
        public List<ReturnApplicationUser> Members { get; set; }
        public string EventImageBase64 { get; set; }

        public ReturnEventBindingModel(EventModel eventModel)
        {
            EventModelID = eventModel.EventModelID;
            MaxUsers = eventModel.MaxUsers;
            Title = eventModel.Title;
            Description = eventModel.Description;
            EndDate = eventModel.EndDate;
            UserCounter = eventModel.UserCounter;
            Latitude = eventModel.Latitude;
            Longitude = eventModel.Longitude;
            Category = eventModel.Category;
            Creator = new ReturnApplicationUser(eventModel.Creator);
            EventImageBase64 = ImageHelper.LoadImageToBase64(eventModel.ImagePath);

            Members = new List<ReturnApplicationUser>();

            foreach (var member in eventModel.Members)
            {
                Members.Add(new ReturnApplicationUser(member));
            }
        }
    }
}