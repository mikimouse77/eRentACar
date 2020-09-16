using System.ComponentModel.DataAnnotations;

namespace eRentACar.Models
{
    public class Car
    {
        [Key]
        public int CarId { get; set; }
        public string CarName { get; set; }
        public decimal Price { get; set; }
        public string Image { get; set; }
    }
}