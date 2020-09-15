using eRentACar.Models;
using System;
using System.Collections.Specialized;
using System.Linq;
using System.Web;
using System.Web.Http;

namespace eRentACar.Helper
{
    public class AuthToken : ApiController
    {
        private RentACarContext db = new RentACarContext();

        protected string GetAuthToken()
        {
            string authToken = null;
            NameValueCollection headers = HttpContext.Current.Request.Headers;

            if (headers["AuthToken"] != null)
                authToken = headers["AuthToken"];

            return authToken;
        }

        protected bool CheckToken()
        {
            string token = GetAuthToken();

            AuthenticationToken TokenCheck = db.AuthenticationTokens
                .Where(s => s.Value == token)
                .FirstOrDefault();

            if (TokenCheck != null)
            {
                if (TokenCheck.Time >= DateTime.Now.AddDays(-2))
                {
                    return true;
                }
            }

            return false;
        }
        protected void DeleteToken()
        {
            string token = GetAuthToken();

            AuthenticationToken TokenCheck = db.AuthenticationTokens
                .Where(s => s.Value == token)
                .FirstOrDefault();

            if (TokenCheck != null)
            {
                db.AuthenticationTokens.Remove(TokenCheck);
                db.SaveChanges();
            }
        }
    }
}