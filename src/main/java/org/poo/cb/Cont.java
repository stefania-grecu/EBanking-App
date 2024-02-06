package org.poo.cb;

public class Cont {
    double suma;
    public Cont(int suma) {
        this.suma = suma;
    }

    public double getSuma() {
        return suma;
    }

    public void adaugaSuma(double bani) {
        suma = suma + bani;
    }
}

class USDCont extends Cont {
    public USDCont(float suma) {
        super((int) suma);
    }
}

class EURCont extends Cont {
    public EURCont(float suma) {
        super((int) suma);
    }
}

class GBPCont extends Cont {
    public GBPCont(float suma) {
        super((int) suma);
    }
}

class JPYCont extends Cont {
    public JPYCont(float suma) {
        super((int) suma);
    }
}

class CADCont extends Cont {
    public CADCont(float suma) {
        super((int) suma);
    }
}

