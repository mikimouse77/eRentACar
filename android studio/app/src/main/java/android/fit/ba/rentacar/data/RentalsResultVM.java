package android.fit.ba.rentacar.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RentalsResultVM implements Serializable {
    public static class Row implements Serializable {
        public int UserId;
        public String CarName;
        public Date ToDate;
        public int RentalId;
        public int CarId;
        public String Price;
        public Date FromDate;
    }

    public List<Row> rows;
}

