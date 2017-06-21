namespace TinderServer2.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class creator_event_relation : DbMigration
    {
        public override void Up()
        {
            RenameColumn(table: "dbo.EventModels", name: "Creator_Id", newName: "CreatorID");
            RenameIndex(table: "dbo.EventModels", name: "IX_Creator_Id", newName: "IX_CreatorID");
            DropPrimaryKey("dbo.EventUser");
            AddPrimaryKey("dbo.EventUser", new[] { "UserID", "EventID" });
        }
        
        public override void Down()
        {
            DropPrimaryKey("dbo.EventUser");
            AddPrimaryKey("dbo.EventUser", new[] { "EventID", "UserID" });
            RenameIndex(table: "dbo.EventModels", name: "IX_CreatorID", newName: "IX_Creator_Id");
            RenameColumn(table: "dbo.EventModels", name: "CreatorID", newName: "Creator_Id");
        }
    }
}
