using eRentACar.Helper;
using eRentACar.Models;
using System;
using System.Data.Entity.Validation;
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

            try
            {
                db.Users.Add(user);
                db.SaveChanges();
            }
            catch (DbEntityValidationException dbEx)
            {
                Exception ex = dbEx;
                foreach (var validationErrors in dbEx.EntityValidationErrors)
                {
                    foreach (var validationError in validationErrors.ValidationErrors)
                    {
                        string message = string.Format("{0}:{1}",
                            validationErrors.Entry.Entity.ToString(),
                            validationError.ErrorMessage);
                        ex = new InvalidOperationException(message, ex);
                    }
                }

                throw ex;
            }

            return Ok();
        }
    }
}