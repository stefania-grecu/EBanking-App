package org.poo.cb;

public class ContFactory {
    public static ContFactory instantaUnica;

    private ContFactory() {}

    public static ContFactory Instanta() {
        if (instantaUnica == null)
            instantaUnica = new ContFactory();
        return instantaUnica;
    }

    public Cont creazaCont(String type, float suma) {
        switch (type) {
            case "USD":
                return new USDCont(suma);
            case "EUR":
                return new EURCont(suma);
            case "GBP":
                return new GBPCont(suma);
            case "JPY":
                return new JPYCont(suma);
            case "CAD":
                return new CADCont(suma);
            default:
                return null;
        }
    }
}
