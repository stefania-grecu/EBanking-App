package org.poo.cb;

public class AdaugaCumparaActiuni implements Comanda{
    private Utilizator utilizator;
    private double suma;
    private int nrActiuni;
    private String numeCompanie;

    public AdaugaCumparaActiuni(Utilizator utilizator, double suma, int nrActiuni, String numeCompanie) {
        this.utilizator = utilizator;
        this.nrActiuni = nrActiuni;
        this.suma = suma;
        this.numeCompanie = numeCompanie;
    }

    public void executa() {
        try {
            utilizator.cumparareActiuni(suma, nrActiuni, numeCompanie);
        } catch (EroareSumaInsuficientaActiuni e) {
            System.out.println("Insufficient amount in account for buying stocks");
        }
    }
}
