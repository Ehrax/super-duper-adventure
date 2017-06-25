namespace TinderServer2.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class user_event_relation : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.EventModels", "StartDate", c => c.DateTime(nullable: false));
            AddColumn("dbo.EventModels", "Location", c => c.String());
            DropColumn("dbo.EventModels", "EndDate");
        }
        
        public override void Down()
        {
            AddColumn("dbo.EventModels", "EndDate", c => c.DateTime(nullable: false));
            DropColumn("dbo.EventModels", "Location");
            DropColumn("dbo.EventModels", "StartDate");
        }
    }
}
