package ca.mcgill.ca.ecse321.autorepairsystem;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AppointmentBooking extends AppCompatActivity {

    DatePickerDialog dateSelector;
    Button dateSelectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_booking);

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
    }

    public void goToViewAvailabilities(View v) {
        Intent intent = new Intent(this, DisplayAvailabilities.class);
        startActivity(intent);
    }

    public void openDateSelect(View view) {
        dateSelector.show();
    }

    private String getDateString(int year, int month, int day) {
        String dateString = month + "/" + day + "/" + year;
        return dateString;
    }
}
