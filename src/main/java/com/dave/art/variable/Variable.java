package com.dave.art.variable;

import com.dave.art.Util;

import java.util.Random;

public class Variable {
    private int minimum;
    private int maximum;
    private VariableDistribution variableDistribution;

    public Variable(int minimum, int maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public Variable(int minimum, int maximum, VariableDistribution variableDistribution) {
        this.minimum = minimum;
        this.maximum = maximum;
        this.variableDistribution = variableDistribution;
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public VariableDistribution getVariableDistribution() {
        return variableDistribution;
    }

    public void setVariableDistribution(VariableDistribution variableDistribution) {
        this.variableDistribution = variableDistribution;
    }

    public int getValue(){
        if (variableDistribution == null){
            if (minimum== maximum){
                return minimum;
            }
            return getRandom().nextInt(maximum-minimum) + minimum;
        }
        else{
            return variableDistribution.getValue(minimum, maximum);
        }
    }

    private static Random getRandom(){
        return Util.RANDOM;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "minimum=" + minimum +
                ", maximum=" + maximum +
                ", variableDistribution=" + variableDistribution +
                '}';
    }
}
