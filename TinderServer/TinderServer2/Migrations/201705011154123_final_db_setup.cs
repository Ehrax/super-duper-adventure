namespace TinderServer2.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class final_db_setup : DbMigration
    {
        public override void Up()
        {
            DropColumn("dbo.AspNetUsers", "Name");
        }
        
        public override void Down()
        {
            AddColumn("dbo.AspNetUsers", "Name", c => c.String());
        }
    }
}
