using eRentACar.Helper;
using eRentACar.Models;
using System;
using System.Web.Http;
using System.Web.Http.Description;

namespace eRentACar.Controllers
{
    public class UsersController : AuthToken
    {
        private RentACarContext db = new RentACarContext();

        [ResponseType(typeof(User))]
        [HttpPost]
        public IHttpActionResult AddUser([FromBody] User user)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Users.Add(user);

            try
            {
                db.SaveChanges();
            }
            catch (System.Data.Entity.Validation.DbEntityValidationException dbEx)
            {
                Exception raise = dbEx;
                foreach (var validationErrors in dbEx.EntityValidationErrors)
                {
                    foreach (var validationError in validationErrors.ValidationErrors)
                    {
                        string message = string.Format("{0}:{1}",
                            validationErrors.Entry.Entity.ToString(),
                            validationError.ErrorMessage);
                        // raise a new exception nesting
                        // the current instance as InnerException
                        raise = new InvalidOperationException(message, raise);
                    }
                }

                throw raise;
            }

            return Ok();
        }
    }
}
