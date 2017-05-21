using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TinderServer2.Models
{
    public class MessageModel
    {
        public MessageModel()
        {

        }

        public int MessageModelID { get; set; }
        public string Content { get; set; }
        public DateTime SendingTime { get; set; }
        public ApplicationUser Sender { get; set; }
        public EventModel Event { get; set;}

    }
}