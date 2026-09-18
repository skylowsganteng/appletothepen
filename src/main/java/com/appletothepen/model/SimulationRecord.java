package com.appletothepen.model;

import java.util.List;

public class SimulationRecord {

    public enum Status { COMPLETED, ABORTED, IN_PROGRESS }

    private final String simName;
    private final String date;
    private final String plannedRoute;
    private final String duration;
    private final double safetyScore;
    private final Status status;
    private final List<String> keyEvents;

    public SimulationRecord(String simName, String date, String plannedRoute, String duration,
                             double safetyScore, Status status, List<String> keyEvents) {
        this.simName = simName;
        this.date = date;
        this.plannedRoute = plannedRoute;
        this.duration = duration;
        this.safetyScore = safetyScore;
        this.status = status;
        this.keyEvents = keyEvents;
    }

    public String getSimName() { return simName; }
    public String getDate() { return date; }
    public String getPlannedRoute() { return plannedRoute; }
    public String getDuration() { return duration; }
    public double getSafetyScore() { return safetyScore; }
    public Status getStatus() { return status; }
    public List<String> getKeyEvents() { return keyEvents; }
}
