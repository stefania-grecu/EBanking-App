package org.poo.cb;

public class EroareSumaInsuficientaPremium extends Throwable{
    public EroareSumaInsuficientaPremium() {
        System.out.println("Insufficient amount in account for buying premium option");
    }

}
