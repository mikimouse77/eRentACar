using System;
using System.Collections.Generic;

namespace eRentACar.ModelVM
{
    public class RentalsResultVM
    {
        public List<Row> rows { get; set; }

        public class Row
        {
            public int UserId { get; internal set; }
            public string CarName { get; internal set; }
            public DateTime ToDate { get; internal set; }
            public int RentalId { get; internal set; }
            public int CarId { get; internal set; }
            public string Price { get; internal set; }
            public DateTime FromDate { get; internal set; }
        }
    }
}