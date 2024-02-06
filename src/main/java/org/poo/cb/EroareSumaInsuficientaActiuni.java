package org.poo.cb;

public class EroareSumaInsuficientaActiuni extends Throwable {
    public EroareSumaInsuficientaActiuni() {
        System.out.println("Insufficient amount in account for buying stock");
    }
}
