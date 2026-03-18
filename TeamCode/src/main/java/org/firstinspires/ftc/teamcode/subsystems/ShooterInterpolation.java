package org.firstinspires.ftc.teamcode.subsystems;

import java.util.TreeMap;
import java.util.Map;

public class ShooterInterpolation {
    private final TreeMap<Double, Double> lut = new TreeMap<>();

    public void addDataPoint(double distanceInches, double velocityTicks) {
        lut.put(distanceInches, velocityTicks);
    }

    public double getVelocity(double distance) {
        Map.Entry<Double, Double> low = lut.floorEntry(distance);
        Map.Entry<Double, Double> high = lut.ceilingEntry(distance);

        if (low == null) return high.getValue();
        if (high == null) return low.getValue();
        if (low.getKey().equals(high.getKey())) return low.getValue();

        return low.getValue() + (distance - low.getKey()) * ((high.getValue() - low.getValue()) / (high.getKey() - low.getKey()));
    }
}