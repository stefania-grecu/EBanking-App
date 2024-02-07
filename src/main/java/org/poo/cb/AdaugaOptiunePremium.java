package org.poo.cb;

public class AdaugaOptiunePremium implements Comanda{
    private Utilizator utilizator;

    public AdaugaOptiunePremium(Utilizator utilizator) {
        this.utilizator = utilizator;
    }

    public void executa() {
        try {
            utilizator.premium();
        } catch (EroareSumaInsuficientaPremium e) {
            System.out.println("Insufficient amount in account for buying premium option");
        }
    }
}
