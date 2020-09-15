using System.Data.Entity;

namespace eRentACar.Models
{
    public class RentACarContext : DbContext
    {
        public RentACarContext()
            : base("name=RentACarContext")
        {
        }

        public virtual DbSet<User> Users { get; set; }
        public virtual DbSet<Car> Cars { get; set; }
        public virtual DbSet<Rental> Rentals { get; set; }
        public virtual DbSet<AuthenticationToken> AuthenticationTokens { get; set; }
    }
}