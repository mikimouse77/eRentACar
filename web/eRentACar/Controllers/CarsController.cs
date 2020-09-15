using eRentACar.Helper;
using eRentACar.Models;
using eRentACar.ModelVM;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;
using System.Web.Http.Description;

namespace eRentACar.Controllers
{
    public class CarsController : AuthToken
    {
        private RentACarContext ctx = new RentACarContext();

        [Route("api/Cars/GetCars/{fromDate},{toDate}")]
        public IHttpActionResult GetCars(string fromDate, string toDate)
        {
            var from = Convert.ToDateTime(fromDate);

            var model = new GetCarsResultVM();
            var to = Convert.ToDateTime(toDate);

            var rentals = ctx.Rentals.ToList();
            var reservedRentals = new List<Rental>();

            foreach (var rental in rentals)
            {
                if ((to >= rental.FromDate) && (to <= rental.ToDate))
                {
                    reservedRentals.Add(rental);
                }
                if ((from >= rental.FromDate) && (to <= rental.ToDate))
                {
                    reservedRentals.Add(rental);
                }
                if ((from >= rental.FromDate) && (from <= rental.ToDate))
                {
                    reservedRentals.Add(rental);
                }
                if ((from < rental.FromDate) && (to > rental.ToDate))
                {
                    reservedRentals.Add(rental);
                }
            }

            var allCars = ctx.Cars.ToList();
            var freeCars = new List<Car>();

            if (reservedRentals.Count == 0)
            {
                freeCars = ctx.Cars.ToList();
            }
            else
            {
                foreach (var soba in allCars)
                {
                    bool zabrani = false;
                    foreach (var rez in reservedRentals)
                    {
                        if (soba.CarId == rez.CarId)
                        {
                            zabrani = true;
                        }
                    }
                    if (!zabrani)
                    {
                        freeCars.Add(soba);
                    }
                }
            }


            if (freeCars.Count != 0)
            {
                model.rows = freeCars.Select(x => new GetCarsResultVM.Row
                {
                    CarId = x.CarId,
                    CarName = x.CarName,
                    Price = x.Price,
                    FromDate = from.ToString(),
                    ToDate = to.ToString()
                }).ToList();

                return Ok(model);
            }
            else
            {
                model.HasData = "NoData";
                return Ok(model);
            }
        }

        [ResponseType(typeof(Car))]
        public IHttpActionResult PostCar([FromBody] RentalPostVM rental)
        {
            var fromDate = Convert.ToDateTime(rental.From);
            var toDate = Convert.ToDateTime(rental.To);

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var rezervacija = new Rental
            {
                CarId = rental.CarId,
                UserId = rental.UserId,
                FromDate = Convert.ToDateTime(rental.From),
                ToDate = Convert.ToDateTime(rental.To)
            };

            ctx.Rentals.Add(rezervacija);
            ctx.SaveChanges();

            return Ok();
        }
    }
}
