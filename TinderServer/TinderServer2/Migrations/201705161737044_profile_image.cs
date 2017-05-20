namespace TinderServer2.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class profile_image : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.AspNetUsers", "ProfileImage_ImageModelID", c => c.Int());
            CreateIndex("dbo.AspNetUsers", "ProfileImage_ImageModelID");
            AddForeignKey("dbo.AspNetUsers", "ProfileImage_ImageModelID", "dbo.ImageModels", "ImageModelID");
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.AspNetUsers", "ProfileImage_ImageModelID", "dbo.ImageModels");
            DropIndex("dbo.AspNetUsers", new[] { "ProfileImage_ImageModelID" });
            DropColumn("dbo.AspNetUsers", "ProfileImage_ImageModelID");
        }
    }
}
