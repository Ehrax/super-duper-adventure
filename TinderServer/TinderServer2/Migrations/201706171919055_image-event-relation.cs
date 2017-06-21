namespace TinderServer2.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class imageeventrelation : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.AspNetUsers", "ProfileImage_ImageModelID", "dbo.ImageModels");
            DropForeignKey("dbo.EventModels", "EventImage_ImageModelID", "dbo.ImageModels");
            DropForeignKey("dbo.MessageModels", "Event_EventModelID", "dbo.EventModels");
            DropForeignKey("dbo.MessageModels", "Sender_Id", "dbo.AspNetUsers");
            DropIndex("dbo.EventModels", new[] { "EventImage_ImageModelID" });
            DropIndex("dbo.AspNetUsers", new[] { "ProfileImage_ImageModelID" });
            DropIndex("dbo.MessageModels", new[] { "Event_EventModelID" });
            DropIndex("dbo.MessageModels", new[] { "Sender_Id" });
            AddColumn("dbo.EventModels", "ImagePath", c => c.String());
            AddColumn("dbo.AspNetUsers", "ProfileImagePath", c => c.String());
            DropColumn("dbo.EventModels", "EventImage_ImageModelID");
            DropColumn("dbo.AspNetUsers", "ProfileImage_ImageModelID");
            DropTable("dbo.ImageModels");
            DropTable("dbo.MessageModels");
        }
        
        public override void Down()
        {
            CreateTable(
                "dbo.MessageModels",
                c => new
                    {
                        MessageModelID = c.Int(nullable: false, identity: true),
                        Content = c.String(),
                        SendingTime = c.DateTime(nullable: false),
                        Event_EventModelID = c.Int(),
                        Sender_Id = c.String(maxLength: 128),
                    })
                .PrimaryKey(t => t.MessageModelID);
            
            CreateTable(
                "dbo.ImageModels",
                c => new
                    {
                        ImageModelID = c.Int(nullable: false, identity: true),
                        Path = c.String(),
                    })
                .PrimaryKey(t => t.ImageModelID);
            
            AddColumn("dbo.AspNetUsers", "ProfileImage_ImageModelID", c => c.Int());
            AddColumn("dbo.EventModels", "EventImage_ImageModelID", c => c.Int());
            DropColumn("dbo.AspNetUsers", "ProfileImagePath");
            DropColumn("dbo.EventModels", "ImagePath");
            CreateIndex("dbo.MessageModels", "Sender_Id");
            CreateIndex("dbo.MessageModels", "Event_EventModelID");
            CreateIndex("dbo.AspNetUsers", "ProfileImage_ImageModelID");
            CreateIndex("dbo.EventModels", "EventImage_ImageModelID");
            AddForeignKey("dbo.MessageModels", "Sender_Id", "dbo.AspNetUsers", "Id");
            AddForeignKey("dbo.MessageModels", "Event_EventModelID", "dbo.EventModels", "EventModelID");
            AddForeignKey("dbo.EventModels", "EventImage_ImageModelID", "dbo.ImageModels", "ImageModelID");
            AddForeignKey("dbo.AspNetUsers", "ProfileImage_ImageModelID", "dbo.ImageModels", "ImageModelID");
        }
    }
}
