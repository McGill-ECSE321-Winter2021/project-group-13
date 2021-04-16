package ca.mcgill.ca.ecse321.autorepairsystem;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;


import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class CustomerHomeActivity extends AppCompatActivity {

    private List<Appointment> appointments = new ArrayList<Appointment>();
    private String error = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerhome);
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
            customerJSON.put("username", "customer1"); // Need to develop current customer persistence
            StringEntity stringEntity = new StringEntity(customerJSON.toString());
            HttpUtils.post(this,"/appointments/bycustomer/", stringEntity, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    // Clear past appointments
                    appointments.clear();

                    JSONObject data;
                    Log.d("CREATION","DATA SIZE IS" + response.toString());
                    for (int i = 0; i < response.length(); i++) {

                        try {
                            data = response.getJSONObject(i);
                            // Get service names
                            JSONArray servicesResponse = data.getJSONArray("services");
                            List<String> serviceNames = new ArrayList<>();
                            for (int j = 0; j < servicesResponse.length(); j++) {
                                serviceNames.add(servicesResponse.getJSONObject(j).getString("name"));
                            }
                            Appointment appointment = new Appointment(data.get("date").toString(), data.get("startTime").toString(),data.get("endTime").toString(), serviceNames);
                            appointments.add(appointment);
                        } catch (Exception e) {
                            TextView displayError = (TextView) findViewById(R.id.errorText);
                            error += e.getMessage();
                            displayError.append(error);
                        }
                        initListView();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    try {
                        error = errorResponse.get("message").toString();
                    } catch (JSONException e) {
                        TextView displayError = (TextView) findViewById(R.id.errorText);
                        error += e.getMessage();
                        displayError.append(error);
                    }

                }
            });
        }
        catch(Exception e) {
            TextView displayError = (TextView) findViewById(R.id.errorText);
            error += e.getMessage();
            displayError.append(error);
        }
    }
}
