package com.dave.art.variable;

import com.dave.art.Util;

public class ExponentialDistribution implements VariableDistribution{
    private float exponent;

    public ExponentialDistribution() {
        this(2.0f);
    }

    public ExponentialDistribution(float exponent) {
        this.exponent = exponent;
    }

    @Override
    public int getValue(int minimum, int maximum) {
        int range = maximum - minimum;

        double maxRangeExponent = Math.pow(range, exponent);
        int randomInt = Util.RANDOM.nextInt((int)maxRangeExponent);

        double result = Math.pow(randomInt, 1.0f/exponent) + minimum;

        if (result < minimum){
            result = minimum;
        }

        if (result > maximum){
            result = maximum;
        }

        return (int)result;
    }

    @Override
    public String toString() {
        return "ExponentialDistribution{" +
                "exponent=" + exponent +
                '}';
    }
}
