package ca.mcgill.ca.ecse321.autorepairsystem;

import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class DisplayAvailabilities extends AppCompatActivity {

    RecyclerView rv;
    RecyclerView.Adapter rvAdapter;
    CalendarView calendar;
    ArrayList<String> availabilityTimes;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayavailabilities);

        //Get different needed views
        rv = findViewById(R.id.availabilitiesList);
        calendar = findViewById(R.id.datePickerCalendar);

        //Listener method for when calendar date is changed
        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {

            String dateYear = Integer.toString(year);
            String dateMonth = Integer.toString(month+1); //Add one because month is from 0 to 11
            String dateDay = Integer.toString(dayOfMonth);

            if (dateMonth.length() == 1) {
                dateMonth = "0" + dateMonth;
            }
            if (dateDay.length() == 1) {
                dateDay = "0" + dateDay;
            }

            String date = dateYear + "-" + dateMonth + "-" + dateDay;

            getAvailabilitiesByDay(date);
        });

        availabilityTimes = new ArrayList<String>();
        availabilityTimes.add("Select a Date to View Availabilities");

        //Display relevant information of availabilities
        rvAdapter = new AvailabilityAdapter(this, availabilityTimes);
        rv.setAdapter(rvAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * This method connects with the backend to retrieve and display all availabilities for a specified day
     * @param availabilityDate The date for which we want to display availabilities
     */
    public void getAvailabilitiesByDay(String availabilityDate) {
        RequestParams parameters = new RequestParams();
        parameters.add("minDuration", "0");

        HttpUtils.get("/available/" + availabilityDate, parameters, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                availabilityTimes.clear();
                JSONArray availableTechnicians = response.names();

                if (availableTechnicians != null) {
                    //Iterate through all the available technicians
                    for (int i = 0; i < availableTechnicians.length (); i++) {
                        String technician;
                        try {
                            technician = availableTechnicians.getString(i);
                            JSONArray availableSlots = response.getJSONArray(technician);

                            for (int j=0; j<availableSlots.length(); j++) {
                                JSONObject slot = availableSlots.getJSONObject(j);

                                //Format string to how we want it displayed
                                String availability = slot.getString("startTime").substring(0, 5) + " - " + slot.getString("endTime").substring(0, 5);
                                availabilityTimes.add(availability);
                                rvAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    availabilityTimes.add("Not Available at Any Time");
                    rvAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DisplayAvailabilities", "Failure");
            }
        });
    }



}
