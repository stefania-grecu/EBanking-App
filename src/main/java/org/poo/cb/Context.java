package org.poo.cb;

public class Context {
    private Strategie strategie;

    public Context(Strategie strategie) {
        this.strategie = strategie;
    }
    public double executeStrategie(double num1, int num2) {
        return strategie.operatie(num1, num2);
    }
}
