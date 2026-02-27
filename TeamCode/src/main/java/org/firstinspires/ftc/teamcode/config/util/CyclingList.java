package org.firstinspires.ftc.teamcode.config.util;

import java.util.ArrayList;
import java.util.HashMap;

public class CyclingList {
    ArrayList<double[]> values = new ArrayList<>();
    int maxSize;

    public CyclingList(int maxSize) {
        this.maxSize = maxSize;
    }

    public void add(double valueToAdd, double time) {
        if (values.size() == maxSize) {
            values.remove(0);

        }
        double[] combinedValue = {valueToAdd, time};
        values.add(combinedValue);
    }

    public double average() {
        if (values.isEmpty()) {
            return 0;
        }
        else {
            double sum = 0;
            for (double[] n : values) {
                sum += n[0];
            }

            double average = sum / values.size();

            return average;
        }
    }
    public double mode() {
        if (values == null || values.isEmpty()) return Double.NaN;

        HashMap<Double, Integer> freq = new HashMap<>();
        double mode = values.get(0)[0];
        int maxCount = 0;

        for (double[] entry : values) {
            double value = entry[0];   // use only the first element

            int count = freq.getOrDefault(value, 0) + 1;
            freq.put(value, count);

            if (count > maxCount) {
                maxCount = count;
                mode = value;
            }
        }

        return mode;
    }

    public double averageROC() {
        if (values.isEmpty()) {
            return 0;
        }
        else {
            double firstValue = values.get(0)[0];
            double lastValue = values.get(values.size() - 1)[0];

            double firstTime = values.get(0)[1];
            double lastTime = values.get(values.size() - 1)[1];

            double averageRateOfChange = (lastValue - firstValue) / (lastTime - firstTime);

            return averageRateOfChange;
        }
    }

    public int getMaxSize() {
        return maxSize;
    }
    public int getSize() {
        return values.size();
    }

    public ArrayList<double[]> getValues() {
        return values;
    }

    public double totalTime() {
        if (values.isEmpty()) {
            return 0;
        }
        else {
            double firstTime = values.get(0)[1];
            double lastTime = values.get(values.size() - 1)[1];

            double totalTime = (lastTime - firstTime);

            return totalTime;
        }
    }
    public void reset() {
        values.clear();
    }


}
