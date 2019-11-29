package com.dave.art.variable;

import com.dave.art.Util;

public class InverseExponentialDistribution implements VariableDistribution{
    private ExponentialDistribution exponentialDistribution;

    public InverseExponentialDistribution() {
        this(2.0f);
    }

    public InverseExponentialDistribution(float exponent) {
        exponentialDistribution = new ExponentialDistribution(exponent);
    }

    @Override
    public int getValue(int minimum, int maximum) {
        return maximum - exponentialDistribution.getValue(minimum, maximum);
    }

    @Override
    public String toString() {
        return "InverseExponentialDistribution{" +
                "exponentialDistribution=" + exponentialDistribution +
                '}';
    }
}
