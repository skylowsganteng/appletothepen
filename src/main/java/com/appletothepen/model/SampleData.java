package com.appletothepen.model;

import java.util.List;

public class SampleData {

    public static List<SimulationRecord> historyRecords() {
        return List.of(
                new SimulationRecord("SIM-205", "2026-03-29", "San Francisco Market St", "12 Min", 99.1,
                        SimulationRecord.Status.COMPLETED, List.of(
                                "00:00 - Simulation Initialized Successfully",
                                "04:12 - Pedestrian Detected near Crosswalk (Slowdown executed)",
                                "12:00 - Arrival at Destination. Valet Parking Activated")),
                new SimulationRecord("SIM-204", "2026-03-28", "Highway 101 North Loop", "45 Min", 88.4,
                        SimulationRecord.Status.ABORTED, List.of(
                                "00:00 - Simulation Initialized Successfully",
                                "18:32 - Manual Override Triggered by Operator",
                                "19:05 - Simulation Aborted")),
                new SimulationRecord("SIM-203", "2026-03-22", "Oakland Port Delivery Run", "25 Min", 95.2,
                        SimulationRecord.Status.COMPLETED, List.of(
                                "00:00 - Simulation Initialized Successfully",
                                "09:47 - Vehicle Merge Detected on Ramp",
                                "25:00 - Arrival at Destination"))
        );
    }

    public static List<String> recentSimSummaries() {
        return List.of(
                "SIM-204 - San Francisco -> San Jose|2026-03-29|COMPLETED",
                "SIM-203 - Oakland Loop (Rain Environment)|2026-03-28|COMPLETED",
                "SIM-202 - Highway 101 Cruise Test|2026-03-25|ABORTED (OVERRIDE)"
        );
    }
}
