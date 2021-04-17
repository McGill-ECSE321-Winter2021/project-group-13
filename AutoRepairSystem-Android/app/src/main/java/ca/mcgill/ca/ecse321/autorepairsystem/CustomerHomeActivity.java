package ca.mcgill.ca.ecse321.autorepairsystem;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.content.SharedPreferences;

import com.loopj.android.http.JsonHttpResponseHandler;


import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class CustomerHomeActivity extends AppCompatActivity {

    private List<Appointment> appointments = new ArrayList<Appointment>();
    private String error = "";
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Username = "usernameKey";
    public static String currentUsername;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerhome);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        currentUsername = sharedpreferences.getString(Username, "ERROR");

        String mainText = "Welcome to the Los Santos Customers";
        String appointmentsHeader = currentUsername + "'s Appointments";
        TextView titleTextView = (TextView) findViewById(R.id.appointmentsTitle);
        titleTextView.setText(appointmentsHeader);

        getAppointmentsList();
        initListView();
    }

    private void initListView() {
        Log.d("CREATION", "Adapter TIME");
        ListView listView = (ListView) findViewById(R.id.appointmentListView);
        AppointmentListAdapter adapter = new AppointmentListAdapter(this, R.layout.adapter_appointment_view_layout, appointments);
        listView.setAdapter(adapter);
    }

    private void getAppointmentsList() {
        //Get This Customer's Appointments
        try {
            JSONObject customerJSON = new JSONObject();
            customerJSON.put("username", currentUsername);
            StringEntity stringEntity = new StringEntity(customerJSON.toString());
            HttpUtils.post(this, "/appointments/bycustomer/", stringEntity, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    // Clear past appointments
                    appointments.clear();

                    JSONObject data;
                    Log.d("CREATION", "DATA SIZE IS" + response.length());
                    for (int i = 0; i < response.length(); i++) {

                        try {
                            data = response.getJSONObject(i);
                            // Get service names
                            JSONArray servicesResponse = data.getJSONArray("services");
                            List<String> serviceNames = new ArrayList<>();
                            for (int j = 0; j < servicesResponse.length(); j++) {
                                serviceNames.add(servicesResponse.getJSONObject(j).getString("name"));
                            }
                            Appointment appointment = new Appointment(data.get("date").toString(), data.get("startTime").toString(), data.get("endTime").toString(), serviceNames, data.get("id").toString());
                            appointments.add(appointment);
                        } catch (Exception e) {
                            error += e.getMessage();
                        }
                        initListView();
                    }

                    manageReminders();

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    try {
                        error = errorResponse.get("message").toString();
                    } catch (JSONException e) {
                        error += e.getMessage();
                    }

                }
            });
        } catch (Exception e) {
            error += e.getMessage();
        }
    }

    private void manageReminders() {

        for (Appointment appointment : appointments) {

            int requestCode = Integer.parseInt(appointment.getId()); //set this to ID

            Log.d("ID REQUEST CODE", Integer.toString(requestCode));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss");
            Date mDate = null;
            try {
                mDate = sdf.parse(appointment.getDate() + " " + appointment.getStartTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Log.d("DATE", mDate.toString());

            long timeInMilliseconds = mDate.getTime();

            Log.d("MILLIS", Long.toString(timeInMilliseconds));


            Intent intent = new Intent(this, ReminderReceiver.class);

            intent.putExtra("time", appointment.getStartTime());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeInMilliseconds);
            calendar.add(Calendar.MINUTE, -60);

            //calendar.setTimeInMillis(System.currentTimeMillis());
            //calendar.add(Calendar.SECOND, 5);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        }
    }

    /**
     * Takes the user to the book appointments activity
     */
    public void goToBookAppointments (View v){
        Log.d("DEBUG","GOING TO BOOK APPOINTMENTS");
        Intent intent = new Intent(this, AppointmentBooking.class);
        startActivity(intent);
    }

    /**
     * Removes current username from shared preferences and takes the user to log in activity.
     * Called onClick by buttons in layout.
     */
    public void logOut(View v) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Username,"");
        editor.commit();

        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }
}
