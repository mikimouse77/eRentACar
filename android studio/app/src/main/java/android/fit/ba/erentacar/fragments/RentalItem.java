package android.fit.ba.erentacar.fragments;


import android.fit.ba.erentacar.data.GetRentalResultVM;
import android.fit.ba.erentacar.helper.MyApiRequest;
import android.fit.ba.erentacar.helper.MyRunnable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.fit.ba.erentacar.R;

import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RentalItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RentalItem extends Fragment {
    public static final String ARG_RentalId = "rentalItem-id";
    private String rentalId;
    private GetRentalResultVM rentalVM;
    private TextView rentTime;
    private TextView carName;
    private TextView price;

    public static RentalItem newInstance(int rentalId) {
        RentalItem fragment = new RentalItem();
        Bundle args = new Bundle();

        args.putString(ARG_RentalId, String.valueOf(rentalId));
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rentalId = getArguments().getString(ARG_RentalId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rental_details, container, false);

        carName = view.findViewById(R.id.carName);
        rentTime = view.findViewById(R.id.rentTime);
        price = view.findViewById(R.id.rentalPrice);

        GetData();
        return view;
    }

    private void GetData() {
        MyApiRequest.get(getActivity(), "api/GetRentals/" + rentalId, new MyRunnable<GetRentalResultVM>() {
            public void run(GetRentalResultVM result) {
                rentalVM = result;
                FillData();
            }
        });
    }

    private void FillData() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String from = sdf.format(rentalVM.FromDate);
        String to = sdf.format(rentalVM.ToDate);

        carName.setText(rentalVM.CarName);
        rentTime.setText(from + "   -   " + to);
        price.setText(rentalVM.Price + " KM");
    }
}
