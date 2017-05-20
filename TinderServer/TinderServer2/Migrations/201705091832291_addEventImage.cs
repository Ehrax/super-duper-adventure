namespace TinderServer2.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class addEventImage : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.EventModels", "EventImage_ImageModelID", c => c.Int());
            CreateIndex("dbo.EventModels", "EventImage_ImageModelID");
            AddForeignKey("dbo.EventModels", "EventImage_ImageModelID", "dbo.ImageModels", "ImageModelID");
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.EventModels", "EventImage_ImageModelID", "dbo.ImageModels");
            DropIndex("dbo.EventModels", new[] { "EventImage_ImageModelID" });
            DropColumn("dbo.EventModels", "EventImage_ImageModelID");
        }
    }
}
