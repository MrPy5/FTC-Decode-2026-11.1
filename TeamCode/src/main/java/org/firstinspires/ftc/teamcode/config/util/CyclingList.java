package org.firstinspires.ftc.teamcode.config.util;

import com.acmerobotics.dashboard.config.Config;

import java.util.ArrayList;
import java.util.HashMap;
@Config
public class CyclingList {
    ArrayList<double[]> values = new ArrayList<>();
    int maxSize;

    // Kalman filter state
    private double kalmanEstimate;
    public static double kalmanErrorCovariance;
    public static double processNoise;      // Q: how much the true value can drift per step
    public static double measurementNoise;  // R: how noisy your sensor readings are
    private boolean kalmanInitialized = false;

    public CyclingList(int maxSize) {
        this.maxSize = maxSize;
        // Sensible defaults — tune these for your use case
        this.processNoise = 0.01;
        this.measurementNoise = 10;
        this.kalmanErrorCovariance = 1.0;
    }

    /**
     * Optional constructor with explicit Kalman tuning parameters.
     *
     * @param maxSize           max history size
     * @param processNoise      Q — increase if the true value changes quickly
     * @param measurementNoise  R — increase if your sensor is noisy
     */
    public CyclingList(int maxSize, double processNoise, double measurementNoise) {
        this.maxSize = maxSize;
        this.processNoise = processNoise;
        this.measurementNoise = measurementNoise;
        this.kalmanErrorCovariance = 1.0;
    }

    public void add(double valueToAdd, double time) {
        if (values.size() == maxSize) {
            values.remove(0);
        }
        double[] combinedValue = {valueToAdd, time};
        values.add(combinedValue);

        // Update Kalman filter with each new measurement
        updateKalman(valueToAdd);
    }

    // -------------------------------------------------------------------------
    // Kalman Filter
    // -------------------------------------------------------------------------

    private void updateKalman(double measurement) {
        if (!kalmanInitialized) {
            // Seed the filter with the first real measurement
            kalmanEstimate = measurement;
            kalmanInitialized = true;
            return;
        }


        double predictedCovariance = kalmanErrorCovariance + processNoise;

        double kalmanGain = predictedCovariance / (predictedCovariance + measurementNoise);
        kalmanEstimate = kalmanEstimate + kalmanGain * (measurement - kalmanEstimate);
        kalmanErrorCovariance = (1 - kalmanGain) * predictedCovariance;
    }

    public double getKalmanEstimate() {
        return kalmanInitialized ? kalmanEstimate : 0;
    }

    public void resetKalman() {
        kalmanInitialized = false;
        kalmanErrorCovariance = 1.0;
    }


    public double average() {
        if (values.isEmpty()) return 0;
        double sum = 0;
        for (double[] n : values) sum += n[0];
        return sum / values.size();
    }

    public double mode() {
        if (values == null || values.isEmpty()) return Double.NaN;
        HashMap<Double, Integer> freq = new HashMap<>();
        double mode = values.get(0)[0];
        int maxCount = 0;
        for (double[] entry : values) {
            double value = entry[0];
            int count = freq.getOrDefault(value, 0) + 1;
            freq.put(value, count);
            if (count > maxCount) { maxCount = count; mode = value; }
        }
        return mode;
    }

    public double averageROC() {
        if (values.isEmpty()) return 0;
        double firstValue = values.get(0)[0], lastValue  = values.get(values.size() - 1)[0];
        double firstTime  = values.get(0)[1], lastTime   = values.get(values.size() - 1)[1];
        return (lastValue - firstValue) / (lastTime - firstTime);
    }

    public double totalTime() {
        if (values.isEmpty()) return 0;
        return values.get(values.size() - 1)[1] - values.get(0)[1];
    }

    public void reset() {
        values.clear();
        resetKalman();
    }

    public int getMaxSize()               { return maxSize; }
    public int getSize()                  { return values.size(); }
    public ArrayList<double[]> getValues(){ return values; }
}