package ca.mcgill.ca.ecse321.autorepairsystem;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class AppointmentBooking extends AppCompatActivity {

    DatePickerDialog dateSelector;
    Button dateSelectButton;
    Button timeSelectButton;
    Button serviceSelectButton;

    int selectedHour, selectedMinute;

    ArrayList<String> serviceList;
    ArrayList<Boolean> selectedList;
    ArrayList<String> desiredServices;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Username = "usernameKey";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_booking);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //Get the current day to default display
        Calendar calendar = Calendar.getInstance();
        int currYear = calendar.get(Calendar.YEAR);
        int currMonth = calendar.get(Calendar.MONTH);
        int currDay = calendar.get(Calendar.DAY_OF_MONTH);

        dateSelectButton = findViewById(R.id.dateSelectButton);
        dateSelectButton.setText(getDateString(currYear, currMonth+1, currDay));

        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            String dateString = getDateString(year, month+1, dayOfMonth);
            dateSelectButton.setText(dateString);
        };

        dateSelector = new DatePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, dateSetListener, currYear, currMonth, currDay);

        timeSelectButton = findViewById(R.id.timeSelectButton);

        serviceSelectButton = findViewById(R.id.servicesSelectButton);

        serviceList = new ArrayList<String>();
        desiredServices = new ArrayList<String>();
        selectedList = new ArrayList<Boolean>();
        getExistingServices();
    }

    /**
     * This method converts an arraylist of strings into an array of strings
     * @param list The ArrayList of type String
     * @return An array of type String
     */
    private String[] toArray(ArrayList<String> list) {
        String[] array = new String[list.size()];

        for (int i=0; i<list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    /**
     * This method calls the backend and finds all existing services in the database, and puts them into arraylist serviceList
     */
    public void getExistingServices() {
        RequestParams parameters = new RequestParams();

        HttpUtils.get("/workitems/", parameters, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                for (int i=0; i<response.length(); i++) {
                    try {
                        JSONObject service = response.getJSONObject(i);

                        String serviceString = service.getString("name") +  ", " + service.getString("duration") + " min, $" + service.getString("price");

                        serviceList.add(serviceString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Log.d("AppointmentBooking", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("AppointmentBooking", "Failure");
            }
        });
    }

    /**
     * This method is called when going to the DisplayAvailabilities page
     */
    public void goToViewAvailabilities(View view) {
        Intent intent = new Intent(this, DisplayAvailabilities.class);
        startActivity(intent);
    }

    /**
     * This method displays the date selector
     */
    public void openDateSelect(View view) {
        dateSelector.show();
    }

    /**
     * This method opens the time selector
     */
    public void openTimeSelect(View view) {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                selectedHour = hourOfDay;
                selectedMinute = minute;
                timeSelectButton.setText(String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute));
            }
        };

        TimePickerDialog timeSelector = new TimePickerDialog(this, timeSetListener, selectedHour, selectedMinute, true);
        timeSelector.setTitle("00:00");
        timeSelector.show();
    }

    /**
     * This method builds and opens the service selector
     */
    public void openServiceSelect(View view) {
        AlertDialog.Builder serviceSelector = new AlertDialog.Builder(this);
        serviceSelector.setTitle("Select Services");

        String[] existingServices = toArray(serviceList);
        boolean[] selectedServices = new boolean[existingServices.length];

        for (int i=0; i<existingServices.length; i++) {
            if (desiredServices.contains(existingServices[i])) {
                selectedServices[i] = true;
            }
            else {
                selectedServices[i] = false;
            }
        }

        serviceSelector.setMultiChoiceItems(existingServices, selectedServices, (dialog, which, isChecked) -> {
            if (isChecked) {
                desiredServices.add(existingServices[which]);
                selectedList.add(true);
            }
            else {
                selectedList.remove(which);
                desiredServices.remove(existingServices[which]);
            }
        });

        serviceSelector.setPositiveButton("Ok", (dialog, which) -> {
            String serviceString = "";
            int counter = 0;
            if (!desiredServices.isEmpty()) {
                for (String service : desiredServices) {
                    serviceString += service.split(",")[0];
                    if (counter != desiredServices.size()-1) serviceString += ", ";
                    counter++;
                }
            }
            else {
                serviceString = "No Selected Services";
            }

            serviceSelectButton.setText(serviceString);
        });

        serviceSelector.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });

        serviceSelector.show();
    }

    /**
     * This method performs the entire appointment booking procedure (warning, confirmation, booking)
     */
    public void bookAppointment(View view) {
        AlertDialog.Builder bookingWarning = new AlertDialog.Builder(this);
        bookingWarning.setTitle("Warning");

        String dateStringDisplay = (String) dateSelectButton.getText();
        String timeStringDisplay = (String) timeSelectButton.getText();
        String servicesStringDisplay = (String) serviceSelectButton.getText();

        //Change the kind of alert box displayed depending on whether or not services have been selected
        if (!servicesStringDisplay.equals("No Selected Services")) {
            //Alert box for confirming the appointment booking
            bookingWarning.setMessage("Are you sure you want to book an appointment on the "+ dateStringDisplay +" at " + timeStringDisplay + " with the following services: " + servicesStringDisplay + ".");

            bookingWarning.setPositiveButton("Ok", (dialog, which) -> {
                try {
                    performAppointmentBooking(dateStringDisplay, timeStringDisplay, servicesStringDisplay, sharedpreferences.getString(Username, ""));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            });

            bookingWarning.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.dismiss();
            });
        }
        else {
            //Alert box for telling the user to select services
            bookingWarning.setMessage("Services must be selected before booking an appointment.");

            bookingWarning.setPositiveButton("Ok", (dialog, which) -> {});
        }

        bookingWarning.show();
    }

    /**
     * This method connects with the backend to perform the actual booking of the appointment
     */
    public void performAppointmentBooking(String date, String time, String services, String customer) throws JSONException, UnsupportedEncodingException {
        JSONObject appointment = new JSONObject();
        String[] splitDate = date.split("/");
        appointment.put("date", splitDate[2] + "-" + splitDate[0] + "-" + splitDate[1]);
        appointment.put("startTime", time + ":00");

        JSONObject customerObject = new JSONObject();
        customerObject.put("username", customer);
        appointment.put("customer", customerObject);

        JSONArray serviceArray = new JSONArray();
        for (String serviceString : desiredServices) {
            String[] splitService = serviceString.split(", ");

            JSONObject service = new JSONObject();
            service.put("name", splitService[0]);
            service.put("duration", Integer.parseInt(splitService[1].split(" min")[0]));
            service.put("price", Integer.parseInt(splitService[2].substring(1)));

            serviceArray.put(service);
        }
        appointment.put("services", serviceArray);

        StringEntity stringEntity = new StringEntity(appointment.toString());

        Log.d("TEST", appointment.toString());
        Log.d("TEST", stringEntity.toString());

        RequestParams params = new RequestParams();

        HttpUtils.post(this,"/create/appointment/any/", stringEntity, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                succesfulBookingWindow();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,String message, Throwable throwable) {
                unsuccesfulBookingWindow(message);
            }
        });
    }

    /**
     * This method opens a popup used to confirm a booking took place
     */
    public void succesfulBookingWindow() {
        AlertDialog.Builder succesfulBooking = new AlertDialog.Builder(this);
        succesfulBooking.setTitle("Success");
        succesfulBooking.setMessage("Your booking has been succesful!");
        succesfulBooking.setPositiveButton("Ok", (dialog, which) -> {});
        succesfulBooking.show();
    }

    /**
     * This method opens a popup used to display an error message when a booking is unsuccessful
     * @param errorMessage The error message to display
     */
    public void unsuccesfulBookingWindow(String errorMessage) {
        AlertDialog.Builder unsuccesfulBooking = new AlertDialog.Builder(this);
        unsuccesfulBooking.setTitle("Error");
        unsuccesfulBooking.setMessage(errorMessage);
        unsuccesfulBooking.setPositiveButton("Ok", (dialog, which) -> {});
        unsuccesfulBooking.show();
    }

    /**
     * This method returns a formatted string of the date
     * @param year The year in the date
     * @param month The month in the date
     * @param day The day of the month in the date
     * @return The date formatted as a String
     */
    private String getDateString(int year, int month, int day) {
        String stringYear = Integer.toString(year);
        String stringMonth = Integer.toString(month);
        String stringDay = Integer.toString(day);

        if (stringMonth.length() != 2) {
            stringMonth = "0" + stringMonth;
        }
        if (stringDay.length() != 2) {
            stringDay = "0" + stringDay;
        }

        String dateString = stringMonth + "/" + stringDay + "/" + stringYear;
        return dateString;
    }

}
