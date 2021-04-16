package ca.mcgill.ca.ecse321.autorepairsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AvailabilityAdapter extends RecyclerView.Adapter<AvailabilityAdapter.AvailabilityViewHolder> {

    Context context;
    ArrayList<String> availabilityTimes;

    public AvailabilityAdapter(Context context, ArrayList<String> availabilityTimes) {
        this.context = context;
        this.availabilityTimes = availabilityTimes;
    }

    @NonNull
    @Override
    public AvailabilityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.availability_row, parent, false);
        return new AvailabilityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailabilityViewHolder holder, int position) {
        holder.availabilityTime.setText(availabilityTimes.get(position));
    }

    @Override
    public int getItemCount() {
        return availabilityTimes.size();
    }

    public class AvailabilityViewHolder extends RecyclerView.ViewHolder {

        TextView availabilityTime;

        public AvailabilityViewHolder(@NonNull View itemView) {
            super(itemView);

            availabilityTime = itemView.findViewById(R.id.availabilityTimesTxt);
        }
    }

}
