package android.fit.ba.rentacar.data;

public class RentalPostVM {
    public RentalPostVM(int userId, int carId, String from, String to){
        UserId = userId;
        CarId = carId;
        From = from;
        To = to;
    }

    public int CarId;
    public int UserId;
    public String From;
    public String To;
}
