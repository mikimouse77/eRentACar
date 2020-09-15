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
        private RentACarContext baza = new RentACarContext();


        [Route("api/Cars/GetCars/{fromDate},{toDate}")]
        public IHttpActionResult GetCars(string fromDate, string toDate)
        {
            DateTime OD = Convert.ToDateTime(fromDate);

            GetCarsResultVM model = new GetCarsResultVM();
            DateTime DO = Convert.ToDateTime(toDate);

            List<Rental> sveRezervacije = baza.Rentals.ToList();
            List<Rental> zauzeteRezervacije = new List<Rental>();

            foreach (var sveRez in sveRezervacije)
            {
                if ((DO >= sveRez.FromDate) && (DO <= sveRez.ToDate))
                {
                    zauzeteRezervacije.Add(sveRez);
                }
                if ((OD >= sveRez.FromDate) && (DO <= sveRez.ToDate))
                {
                    zauzeteRezervacije.Add(sveRez);
                }
                if ((OD >= sveRez.FromDate) && (OD <= sveRez.ToDate))
                {
                    zauzeteRezervacije.Add(sveRez);
                }
                if ((OD < sveRez.FromDate) && (DO > sveRez.ToDate))
                {
                    zauzeteRezervacije.Add(sveRez);
                }
            }
            List<Car> sveSobe = baza.Cars.ToList();
            List<Car> slobodneSobe = new List<Car>();

            if (zauzeteRezervacije.Count == 0)
            {
                slobodneSobe = baza.Cars.ToList();
            }
            else
            {
                foreach (var soba in sveSobe)
                {
                    bool zabrani = false;
                    foreach (var rez in zauzeteRezervacije)
                    {
                        if (soba.CarId == rez.CarId)
                        {
                            zabrani = true;
                        }
                    }
                    if (!zabrani)
                    {
                        slobodneSobe.Add(soba);
                    }
                }
            }


            if (slobodneSobe.Count != 0)
            {
                model.rows = slobodneSobe.Select(x => new GetCarsResultVM.Row
                {
                    CarId = x.CarId,
                    CarName = x.CarName,
                    Price = x.Price,
                    FromDate = OD.ToString(),
                    ToDate = DO.ToString()
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
        public IHttpActionResult PostCar([FromBody] RentalPostVM soba)
        {
            DateTime datumOD = Convert.ToDateTime(soba.From);
            DateTime datumDO = Convert.ToDateTime(soba.To);

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            Rental rezervacija = new Rental
            {
                CarId = soba.CarId,
                UserId = soba.UserId,
                FromDate = Convert.ToDateTime(soba.From),
                ToDate = Convert.ToDateTime(soba.To)
            };

            baza.Rentals.Add(rezervacija);
            baza.SaveChanges();

            return Ok();
        }

    }
}
