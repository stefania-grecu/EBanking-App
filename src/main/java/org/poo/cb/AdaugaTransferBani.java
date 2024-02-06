package org.poo.cb;

public class AdaugaTransferBani implements Comanda{
    private Utilizator utilizator, prieten;
    private double suma;
    private String tipValuta;

    public AdaugaTransferBani(Utilizator utilizator,Utilizator prieten, String tipValuta, double suma) {
        this.utilizator = utilizator;
        this.tipValuta = tipValuta;
        this.suma = suma;
        this.prieten = prieten;
    }

    public void executa() {
        try {
            utilizator.transferBani(prieten, tipValuta, suma);
        } catch (EroareSumaInsuficientaTransfer e) {
            System.out.println("Insufficient amount in account " + tipValuta + " for transfer");
        }
    }
}
