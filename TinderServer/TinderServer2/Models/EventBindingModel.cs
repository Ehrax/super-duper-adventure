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
        [Range (2,100)]
        public int MaxUsers { get; set; }

        [Required]
        [Range(-90,90)]
        public double Latitude { get; set; }

        [Required]
        [Range(-180,180)]
        public double Longitude { get; set; }

        [Required]
        [Range (0,4)]
        public int Category { get; set; }

        public string ImageBase64 { get; set; }
    }
}