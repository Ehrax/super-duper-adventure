using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;
using Newtonsoft.Json;

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
        public int MaxUsers { get; set; }

        [Required]
        public double Latitude { get; set; }

        [Required]
        public double Longitude { get; set; }

        [Required]
        public int Category { get; set; }

        public string ImageBase64 { get; set; }
    }
}