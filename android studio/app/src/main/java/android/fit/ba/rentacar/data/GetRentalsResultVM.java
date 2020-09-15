package android.fit.ba.rentacar.data;

import java.io.Serializable;
import java.util.List;

public class GetRentalsResultVM implements Serializable {
    public static class Row implements Serializable {
        public int CarId;
        public String CarName;
        public String Price;
        public String FromDate;
        public String ToDate;
    }

    public String HasData;
    public List<Row> rows;
}

