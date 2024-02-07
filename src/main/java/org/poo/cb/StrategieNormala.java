package org.poo.cb;

public class StrategieNormala implements Strategie{
    @Override
    public double operatie(double num1, int num2) {
        return num1 * num2;
    }
}
