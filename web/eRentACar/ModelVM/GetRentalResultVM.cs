using System;

namespace eRentACar.ModelVM
{
    public class GetRentalResultVM
    {
        public int RentalId { get; internal set; }
        public string CarName { get; internal set; }
        public DateTime ToDate { get; internal set; }
        public string Price { get; internal set; }
        public DateTime FromDate { get; internal set; }
        public string Image { get; internal set; }
    }
}