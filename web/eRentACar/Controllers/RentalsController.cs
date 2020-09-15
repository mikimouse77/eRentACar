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
                rows = db.Rentals.Where(s => s.UserId == id).Select(s => new RentalsResultVM.Row
                {
                    RentalId = s.RentalId,
                    UserId = s.UserId,
                    CarId = s.CarId,
                    CarName = s.Car.CarName,
                    FromDate = s.FromDate,
                    ToDate = s.ToDate,
                    Price = Math.Round(((s.ToDate.Day - s.FromDate.Day + 1) * s.Car.Price), 2).ToString()
                }).ToList()
            };

            return Ok(model);
        }
        [HttpGet]
        [Route("api/GetRentals/{rezervacijaID}")]
        public IHttpActionResult GetRentals(string rezervacijaID)
        {
            var id = Convert.ToInt32(rezervacijaID);
            var rezervacija = db.Rentals.Where(s => s.RentalId == id).FirstOrDefault();

            var model = new GetRentalResultVM
            {
                RentalId = rezervacija.RentalId,
                CarName = rezervacija.Car.CarName,
                FromDate = rezervacija.FromDate,
                ToDate = rezervacija.ToDate,
                Price =
                    Math.Round(((rezervacija.ToDate.Day - rezervacija.FromDate.Day + 1) * rezervacija.Car.Price), 2)
                    .ToString()
            };

            return Ok(model);
        }
    }
}
