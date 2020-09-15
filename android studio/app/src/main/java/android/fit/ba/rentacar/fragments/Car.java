package android.fit.ba.rentacar.fragments;


import android.content.Intent;
import android.fit.ba.rentacar.MainActivity;
import android.fit.ba.rentacar.data.Global;
import android.fit.ba.rentacar.data.RentalPostVM;
import android.fit.ba.rentacar.helper.MyApiRequest;
import android.fit.ba.rentacar.helper.MyRunnable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.fit.ba.rentacar.R;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Car#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Car extends Fragment {

    public static final String ARG_CarId = "car-id";
    public static final String ARG_FromDate = "car-from-date";
    public static final String ARG_ToDate = "car-to-date";
    public static final String ARG_CarName = "car-name";
    public static final String ARG_TotalPrice = "car-total-price";
    private TextView nameCar;
    private int carId;
    private TextView rentTime;
    private String fromDate;
    private String toDate;
    private TextView txtPrice;
    private String carName;
    private String price;

    public static Car newInstance(String totalPrice, int carId, String carName, String fromDate, String toDate) {
        Car fragment = new Car();
        Bundle args = new Bundle();

        args.putSerializable(ARG_CarId, carId);
        args.putSerializable(ARG_FromDate, fromDate);
        args.putSerializable(ARG_ToDate, toDate);
        args.putSerializable(ARG_CarName, carName);
        args.putSerializable(ARG_TotalPrice, totalPrice);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            carId = (int) getArguments().getSerializable(ARG_CarId);
            fromDate = (String) getArguments().getSerializable(ARG_FromDate);
            toDate = (String) getArguments().getSerializable(ARG_ToDate);
            carName = (String) getArguments().getSerializable(ARG_CarName);
            price = (String) getArguments().getSerializable(ARG_TotalPrice);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rental_dates, container, false);
        nameCar = view.findViewById(R.id.carName);
        rentTime = view.findViewById(R.id.rentTime);
        txtPrice = view.findViewById(R.id.rentalPrice);

        FillData();
        view.findViewById(R.id.btnRent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RentCar();
            }
        });

        return view;
    }

    private void RentCar() {
        RentalPostVM car = new RentalPostVM(Global.AuthenticatedUser.UserId, carId, fromDate, toDate);

        MyApiRequest.post(getActivity(), "api/cars/postcar", car, new MyRunnable<RentalPostVM>() {
            public void run(RentalPostVM x) {
                Toast.makeText(getActivity(), "Auto uspjesno iznajmljeno.", Toast.LENGTH_SHORT).show();
            }
        });

        startActivity(new Intent(getActivity(), MainActivity.class));
    }

    private void FillData() {
        nameCar.setText(carName);
        rentTime.setText(fromDate + "   ---   " + toDate);
        txtPrice.setText(price + " KM");
    }
}
