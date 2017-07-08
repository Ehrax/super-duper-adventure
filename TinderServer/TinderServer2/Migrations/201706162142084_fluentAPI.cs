namespace TinderServer2.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class fluentAPI : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.EventModels", "ApplicationUser_Id", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUsers", "EventModel_EventModelID", "dbo.EventModels");
            DropIndex("dbo.EventModels", new[] { "ApplicationUser_Id" });
            DropIndex("dbo.AspNetUsers", new[] { "EventModel_EventModelID" });
            CreateTable(
                "dbo.EventModelApplicationUsers",
                c => new
                    {
                        EventModel_EventModelID = c.Int(nullable: false),
                        ApplicationUser_Id = c.String(nullable: false, maxLength: 128),
                    })
                .PrimaryKey(t => new { t.EventModel_EventModelID, t.ApplicationUser_Id })
                .ForeignKey("dbo.EventModels", t => t.EventModel_EventModelID, cascadeDelete: true)
                .ForeignKey("dbo.AspNetUsers", t => t.ApplicationUser_Id, cascadeDelete: true)
                .Index(t => t.EventModel_EventModelID)
                .Index(t => t.ApplicationUser_Id);
            
            DropColumn("dbo.EventModels", "ApplicationUser_Id");
            DropColumn("dbo.AspNetUsers", "EventModel_EventModelID");
            CreateStoredProcedure(
                "dbo.EventModelApplicationUser_Insert",
                p => new
                    {
                        EventModel_EventModelID = p.Int(),
                        ApplicationUser_Id = p.String(maxLength: 128),
                    },
                body:
                    @"INSERT [dbo].[EventModelApplicationUsers]([EventModel_EventModelID], [ApplicationUser_Id])
                      VALUES (@EventModel_EventModelID, @ApplicationUser_Id)"
            );
            
            CreateStoredProcedure(
                "dbo.EventModelApplicationUser_Delete",
                p => new
                    {
                        EventModel_EventModelID = p.Int(),
                        ApplicationUser_Id = p.String(maxLength: 128),
                    },
                body:
                    @"DELETE [dbo].[EventModelApplicationUsers]
                      WHERE (([EventModel_EventModelID] = @EventModel_EventModelID) AND ([ApplicationUser_Id] = @ApplicationUser_Id))"
            );
            
        }
        
        public override void Down()
        {
            DropStoredProcedure("dbo.EventModelApplicationUser_Delete");
            DropStoredProcedure("dbo.EventModelApplicationUser_Insert");
            AddColumn("dbo.AspNetUsers", "EventModel_EventModelID", c => c.Int());
            AddColumn("dbo.EventModels", "ApplicationUser_Id", c => c.String(maxLength: 128));
            DropForeignKey("dbo.EventModelApplicationUsers", "ApplicationUser_Id", "dbo.AspNetUsers");
            DropForeignKey("dbo.EventModelApplicationUsers", "EventModel_EventModelID", "dbo.EventModels");
            DropIndex("dbo.EventModelApplicationUsers", new[] { "ApplicationUser_Id" });
            DropIndex("dbo.EventModelApplicationUsers", new[] { "EventModel_EventModelID" });
            DropTable("dbo.EventModelApplicationUsers");
            CreateIndex("dbo.AspNetUsers", "EventModel_EventModelID");
            CreateIndex("dbo.EventModels", "ApplicationUser_Id");
            AddForeignKey("dbo.AspNetUsers", "EventModel_EventModelID", "dbo.EventModels", "EventModelID");
            AddForeignKey("dbo.EventModels", "ApplicationUser_Id", "dbo.AspNetUsers", "Id");
        }
    }
}
