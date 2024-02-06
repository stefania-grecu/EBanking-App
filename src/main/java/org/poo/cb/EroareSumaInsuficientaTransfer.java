package org.poo.cb;

public class EroareSumaInsuficientaTransfer extends Throwable {
    public EroareSumaInsuficientaTransfer(String cont) {
        System.out.println("Insufficient amount in account " + cont + " for transfer");
    }
}
