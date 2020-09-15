using System.Collections.Generic;

namespace eRentACar.ModelVM
{
    public class GetCarsResultVM
    {
        public List<Row> rows { get; set; }
        public string HasData { get; set; }
        public class Row
        {
            public int CarId { get; internal set; }
            public string CarName { get; internal set; }
            public decimal Price { get; internal set; }
            public string FromDate { get; internal set; }
            public string ToDate { get; internal set; }
        }
    }
}