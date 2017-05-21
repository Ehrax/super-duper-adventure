namespace TinderServer2.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class initial_db_setup : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.EventModels", "ApplicationUser_Id", c => c.String(maxLength: 128));
            CreateIndex("dbo.EventModels", "ApplicationUser_Id");
            AddForeignKey("dbo.EventModels", "ApplicationUser_Id", "dbo.AspNetUsers", "Id");
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.EventModels", "ApplicationUser_Id", "dbo.AspNetUsers");
            DropIndex("dbo.EventModels", new[] { "ApplicationUser_Id" });
            DropColumn("dbo.EventModels", "ApplicationUser_Id");
        }
    }
}
