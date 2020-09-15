using eRentACar.Helper;
using eRentACar.Models;
using eRentACar.ModelVM;
using System;
using System.Linq;
using System.Web.Http;

namespace eRentACar.Controllers
{
    public class AuthenticationController : AuthToken
    {
        private RentACarContext db = new RentACarContext();

        [HttpGet]
        [Route("api/Authentication/LoginCheck/{username}/{pass}")]
        public IHttpActionResult LoginCheck(string username, string pass)
        {
            string token = Guid.NewGuid().ToString();

            User turist = db.Users.Where(s => s.UserName == username && s.PasswordSalt == pass).FirstOrDefault();
            // unutar lozinkaSalt je smjesten string password

            if (turist != null)
            {
                AuthenticationResultVM a = new AuthenticationResultVM();
                a.UserId = turist.UserId;
                a.FirstName = turist.FirstName;
                a.LastName = turist.LastName;
                a.UserName = turist.UserName;
                a.PasswordSalt = turist.PasswordSalt;
                a.Phone = turist.Phone;
                a.Email = turist.Email;
                a.Token = token;

                db.AuthenticationTokens.Add(new AuthenticationToken
                {
                    Value = a.Token,
                    UserId = a.UserId,
                    Time = DateTime.Now,
                    IpAddress = "..."
                });

                db.SaveChanges();

                return Ok(a);
            }

            AuthenticationResultVM y = new AuthenticationResultVM();
            y.FirstName = "PogresniPodaci";

            return Ok(y);
        }

        [HttpDelete]
        [Route("api/Autentifikacija/Logout")]
        public IHttpActionResult Logout()
        {
            DeleteToken();
            return Ok();
        }
    }
}
