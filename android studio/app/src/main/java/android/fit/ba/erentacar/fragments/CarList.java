package android.fit.ba.erentacar.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.fit.ba.erentacar.Util.Util;
import android.fit.ba.erentacar.data.GetRentalsResultVM;
import android.fit.ba.erentacar.helper.MyApiRequest;
import android.fit.ba.erentacar.helper.MyRunnable;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.fit.ba.erentacar.R;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Calendar;

public class CarList extends Fragment {
    private ListView carList;
    private GetRentalsResultVM rentals;
    private BaseAdapter adapter;
    private Calendar calendar;
    private DatePickerDialog dialogCalendar;
    private int fromYear;
    private int fromMonth;
    private int fromDay;
    private int toYear;
    private int toMonth;
    private int toDay;
    private Button fromBtn;
    private Button toBtn;
    private Button search;
    private String fromDate;
    private String toDate;


    public static CarList newInstance() {
        CarList fragment = new CarList();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.rentals, container, false);
        fromBtn = view.findViewById(R.id.fromDate);
        toBtn = view.findViewById(R.id.toDate);
        search = view.findViewById(R.id.search);

        fromBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                dialogCalendar = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        fromDay = dayOfMonth;
                        fromMonth = month;
                        fromYear = year;
                    }
                }, year, month, day);
                dialogCalendar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogCalendar.show();
            }
        });

        toBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calendar = Calendar.getInstance();

                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                dialogCalendar = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                toDay = dayOfMonth;
                                toMonth = month;
                                toYear = year;
                            }
                        }, year, month, day);

                dialogCalendar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogCalendar.show();
            }
        });


        carList = view.findViewById(R.id.listCars);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fillData();
            }
        });

        carList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fromDate = (fromMonth + 1) + "-" + fromDay + "-" + fromYear;
                toDate = (toMonth + 1) + "-" + toDay + "-" + toYear;

                String price = String.valueOf((int) Double.parseDouble(rentals.rows.get(position).Price));
                String totalPrice = String.valueOf((toDay - fromDay + 1) * Integer.parseInt(price));

                int carId = rentals.rows.get(position).CarId;
                String carName = rentals.rows.get(position).CarName;
                Util.otvoriFragmentKaoReplace(getActivity(), R.id.fragmentPlace, Car.newInstance(totalPrice, carId, carName, fromDate, toDate));
            }
        });


        return view;
    }

    private void fillData() {
        fromDate = (fromMonth + 1) + "-" + fromDay + "-" + fromYear;
        toDate = (toMonth + 1) + "-" + toDay + "-" + toYear;

        if(fromMonth == 0){
            Toast.makeText(getActivity(), "Izaberite datum OD!", Toast.LENGTH_LONG).show();
            return;
        }

        if(toMonth == 0){
            Toast.makeText(getActivity(), "Izaberite datum DO!", Toast.LENGTH_LONG).show();
            return;
        }

        MyApiRequest.get(getActivity(), "api/Cars/GetCars/" + fromDate + "," + toDate, new MyRunnable<GetRentalsResultVM>() {

            @Override
            public void run(GetRentalsResultVM response) {
                if (("NoData").equals(response.HasData)) {
                    Toast.makeText(getActivity(), "Nema niti jednog slobodnog auta.", Toast.LENGTH_LONG).show();
                } else {
                    rentals = response;
                    fillList();
                }
            }
        });
    }

    private void fillList() {
        if (rentals.rows.size() > 0) {
            adapter = new BaseAdapter() {
                @Override
                public int getCount() {
                    return rentals.rows.size();
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
                        final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        view = inflater.inflate(R.layout.item_rentals, parent, false);
                    }

                    TextView txtCarName = view.findViewById(R.id.carNameRentals);
                    TextView txtRentalPrice = view.findViewById(R.id.rentalPrice);

                    GetRentalsResultVM.Row x = rentals.rows.get(position);

                    txtCarName.setText(x.CarName);
                    txtRentalPrice.setText(x.Price + " KM");

                    return view;
                }
            };

            carList.setAdapter(adapter);
        }
    }
}