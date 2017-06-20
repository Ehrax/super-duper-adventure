namespace TinderServer2.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class manytomanyfix : DbMigration
    {
        public override void Up()
        {
            RenameTable(name: "dbo.EventModelApplicationUsers", newName: "EventUser");
            RenameColumn(table: "dbo.EventUser", name: "EventModel_EventModelID", newName: "EventID");
            RenameColumn(table: "dbo.EventUser", name: "ApplicationUser_Id", newName: "UserID");
            RenameIndex(table: "dbo.EventUser", name: "IX_EventModel_EventModelID", newName: "IX_EventID");
            RenameIndex(table: "dbo.EventUser", name: "IX_ApplicationUser_Id", newName: "IX_UserID");
            DropStoredProcedure("dbo.EventModelApplicationUser_Insert");
            DropStoredProcedure("dbo.EventModelApplicationUser_Delete");
        }
        
        public override void Down()
        {
            RenameIndex(table: "dbo.EventUser", name: "IX_UserID", newName: "IX_ApplicationUser_Id");
            RenameIndex(table: "dbo.EventUser", name: "IX_EventID", newName: "IX_EventModel_EventModelID");
            RenameColumn(table: "dbo.EventUser", name: "UserID", newName: "ApplicationUser_Id");
            RenameColumn(table: "dbo.EventUser", name: "EventID", newName: "EventModel_EventModelID");
            RenameTable(name: "dbo.EventUser", newName: "EventModelApplicationUsers");
            throw new NotSupportedException("Gerüste für Vorgänge zum Erstellen oder Ändern von Prozeduren werden in Down-Methoden nicht unterstützt.");
        }
    }
}
