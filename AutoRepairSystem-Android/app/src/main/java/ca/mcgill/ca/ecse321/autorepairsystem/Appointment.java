package ca.mcgill.ca.ecse321.autorepairsystem;

import java.util.List;

public class Appointment {
    private String date;
    private String startTime;
    private String endTime;
    private List<String> services;
    private String id;

    public Appointment(String date, String startTime, String endTime, List<String> serviceNames, String id) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.services = serviceNames;
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getId() { return id;}

    public List<String> getServices() {
        return services;
    }
}
