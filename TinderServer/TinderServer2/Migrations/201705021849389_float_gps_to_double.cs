namespace TinderServer2.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class float_gps_to_double : DbMigration
    {
        public override void Up()
        {
            AlterColumn("dbo.EventModels", "Latitude", c => c.Double(nullable: false));
            AlterColumn("dbo.EventModels", "Longitude", c => c.Double(nullable: false));
        }
        
        public override void Down()
        {
            AlterColumn("dbo.EventModels", "Longitude", c => c.Single(nullable: false));
            AlterColumn("dbo.EventModels", "Latitude", c => c.Single(nullable: false));
        }
    }
}
