package org.poo.cb;

public class EroareSumaInsuficienta extends Throwable {
    public EroareSumaInsuficienta(String cont) {
        System.out.println("Insufficient amount in account " + cont + " for exchange");
    }
}
