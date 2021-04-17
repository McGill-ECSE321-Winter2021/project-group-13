package ca.mcgill.ca.ecse321.autorepairsystem;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class AppointmentListAdapter extends ArrayAdapter<Appointment> {

    private Context context;
    private int resource;

    public AppointmentListAdapter(@NonNull Context context, int resource, @NonNull List<Appointment> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String date = getItem(position).getDate();
        String startTime = timeFormatter(getItem(position).getStartTime());
        String endTime = timeFormatter(getItem(position).getEndTime());

        List<String> services = getItem(position).getServices();

        Appointment appointment = new Appointment(date,startTime,endTime,services,"");

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource,parent,false);

        TextView tvDate = (TextView) convertView.findViewById(R.id.appointmentDate);
        TextView tvStartTime = (TextView) convertView.findViewById(R.id.appointmentStartTime);
        TextView tvEndTime = (TextView) convertView.findViewById(R.id.appointmentEndTime);
        TextView tvServices = (TextView) convertView.findViewById(R.id.appointmentServices);

        tvDate.setText(date);
        tvStartTime.setText(startTime);
        tvEndTime.setText(endTime);
        tvServices.setText(servicesFormatter(services.toString()));

        return convertView;
    }

    private String timeFormatter(String time) { //Converts time string from HH:mm:ss to HH:mm
        return time.substring(0,time.length()-3);
    }

    private String servicesFormatter(String services) {
        services = services.substring(1,services.length()-1);
        services = services.replace(",",System.lineSeparator());
        Log.d("TEST",services);
        return services;
    }
}