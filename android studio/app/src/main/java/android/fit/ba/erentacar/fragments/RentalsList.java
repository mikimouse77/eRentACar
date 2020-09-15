package android.fit.ba.erentacar.fragments;


import android.content.Context;
import android.fit.ba.erentacar.Util.Util;
import android.fit.ba.erentacar.data.RentalsResultVM;
import android.fit.ba.erentacar.helper.MyApiRequest;
import android.fit.ba.erentacar.helper.MyRunnable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.fit.ba.erentacar.R;

import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RentalsList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RentalsList extends Fragment {
    public static final String ARG_UserId = "Rentals-UserId";
    private ListView rentals;
    private String userId;
    private RentalsResultVM data;

    public static RentalsList newInstance(int userId) {
        RentalsList fragment = new RentalsList();
        Bundle args = new Bundle();
        args.putString(ARG_UserId, String.valueOf(userId));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(ARG_UserId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rented, container, false);
        rentals = view.findViewById(R.id.rentalsList);

        rentals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RentalsResultVM.Row row = data.rows.get(position);
                Util.otvoriFragmentKaoReplace(getActivity(), R.id.fragmentPlace, RentalItem.newInstance(row.RentalId));
            }
        });

        GetData();
        return view;
    }

    private void GetData() {
        MyApiRequest.get(getActivity(), "api/Rentals/getRentalsByUserId/" + userId, new MyRunnable<RentalsResultVM>() {
            public void run(RentalsResultVM result) {
                data = result;
                FillData();
            }
        });
    }

    private void FillData() {
        if (data.rows.size() > 0) {
            rentals.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return data.rows.size();
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    return 0;
                }

                @Override
                public View getView(int position, View view, ViewGroup parent) {
                    if (view == null) {
                        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        view = inflater.inflate(R.layout.item_rental, parent, false);
                    }

                    TextView carName = view.findViewById(R.id.carName);
                    TextView rentalDate = view.findViewById(R.id.rentalDate);
                    TextView total = view.findViewById(R.id.rentalPrice);

                    RentalsResultVM.Row x = data.rows.get(position);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String from = sdf.format(x.FromDate);
                    String to = sdf.format(x.ToDate);

                    carName.setText(x.CarName);
                    rentalDate.setText(from + "   -   " + to);
                    total.setText(x.Price + "KM");

                    return view;
                }
            });
        }
    }
}
