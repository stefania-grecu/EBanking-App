package org.poo.cb;

public class StrategiePremium implements Strategie{
    @Override
    public double operatie(double num1, int num2) {
        return num1 * num2 * 0.95;
    }
}
