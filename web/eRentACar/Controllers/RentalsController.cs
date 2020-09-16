using eRentACar.Helper;
using eRentACar.Models;
using eRentACar.ModelVM;
using System;
using System.Linq;
using System.Web.Http;

namespace eRentACar.Controllers
{
    public class RentalsController : AuthToken
    {
        private RentACarContext db = new RentACarContext();

        [HttpGet]
        [Route("api/Rentals/getRentalsByUserId/{userId}")]
        public IHttpActionResult GetRentalsByUserId(string userId)
        {
            if (CheckToken() == false)
                return Unauthorized();

            var id = Convert.ToInt32(userId);

            var model = new RentalsResultVM
            {
                rows = db.Rentals.Where(s => s.UserId == id).Select(rental => new RentalsResultVM.Row
                {
                    RentalId = rental.RentalId,
                    UserId = rental.UserId,
                    CarId = rental.CarId,
                    CarName = rental.Car.CarName,
                    FromDate = rental.FromDate,
                    ToDate = rental.ToDate,
                    Price = Math.Round((((int)(rental.ToDate - rental.FromDate).TotalDays + 1) * rental.Car.Price), 2).ToString()
                }).ToList()
            };

            return Ok(model);
        }
        [HttpGet]
        [Route("api/GetRentals/{rentalId}")]
        public IHttpActionResult GetRentals(string rentalId)
        {
            var id = Convert.ToInt32(rentalId);
            var rental = db.Rentals.Where(s => s.RentalId == id).FirstOrDefault();

            var model = new GetRentalResultVM
            {
                RentalId = rental.RentalId,
                CarName = rental.Car.CarName,
                FromDate = rental.FromDate,
                Image = rental.Car.Image,
                ToDate = rental.ToDate,
                Price =
                    Math.Round((((int)(rental.ToDate - rental.FromDate).TotalDays + 1) * rental.Car.Price), 2)
                    .ToString()
            };

            return Ok(model);
        }
    }
}
