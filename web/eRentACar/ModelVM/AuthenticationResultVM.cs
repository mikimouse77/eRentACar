namespace eRentACar.ModelVM
{
    public class AuthenticationResultVM
    {
        public int UserId { get; internal set; }
        public string UserName { get; internal set; }
        public string PasswordSalt { get; internal set; }
        public string Phone { get; internal set; }
        public string FirstName { get; internal set; }
        public string LastName { get; internal set; }
        public string Email { get; internal set; }
        public string Token { get; internal set; }
    }
}