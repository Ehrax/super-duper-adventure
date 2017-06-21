using System.Security.Claims;
using System.Threading.Tasks;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using Microsoft.AspNet.Identity.Owin;
using System.Data.Entity;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;

namespace TinderServer2.Models
{
    // You can add profile data for the user by adding more properties to your ApplicationUser class, please visit https://go.microsoft.com/fwlink/?LinkID=317594 to learn more.
    public class ApplicationUser : IdentityUser
    {
        public string ProfileImagePath { get; set; }
        [InverseProperty("Members")]
        public virtual ICollection<EventModel> Events { get; set; }
        public async Task<ClaimsIdentity> GenerateUserIdentityAsync(UserManager<ApplicationUser> manager, string authenticationType)
        {
            // Beachten Sie, dass der "authenticationType" mit dem in "CookieAuthenticationOptions.AuthenticationType" definierten Typ übereinstimmen muss.
            var userIdentity = await manager.CreateIdentityAsync(this, authenticationType);
            // Benutzerdefinierte Benutzeransprüche hier hinzufügen

            return userIdentity;
        }
    }

    public class ApplicationDbContext : IdentityDbContext<ApplicationUser>
    {
        public ApplicationDbContext()
            : base("DefaultConnection", throwIfV1Schema: false)
        {
        }

        public static ApplicationDbContext Create()
        {
            return new ApplicationDbContext();
        }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {

            modelBuilder.Entity<EventModel>().HasMany(e => e.Members).WithMany().Map(x =>
            {
                x.MapLeftKey("EventID");
                x.MapRightKey("UserID");
                x.ToTable("EventUser");

            });
            modelBuilder.Entity<EventModel>().HasKey(s => s.EventModelID);
            base.OnModelCreating(modelBuilder);
        }

        public DbSet<EventModel> Events { get; set; }

    }

    public class ReturnApplicationUser
    {
        public string UserID { get; set; }
        public string Email { get; set; }
        public string Username { get; set; }

        public ReturnApplicationUser(ApplicationUser user)
        {
            UserID = user.Id;
            Email = user.Email;
            Username = user.UserName;
        }
    }
}