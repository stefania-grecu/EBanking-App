package org.poo.cb;

public class AdaugaSchimbValutar implements Comanda{
    private String tipValutaSursa, tpValutaDestinatie;
    private Utilizator utilizator;
    private  double suma;
    private String[][] valoare;

    public AdaugaSchimbValutar(Utilizator utilizator, String tipValutaSursa, String tipValuatDestinatie, double suma, String[][] valoare) {
        this.utilizator = utilizator;
        this.suma = suma;
        this.tipValutaSursa = tipValutaSursa;
        this.tpValutaDestinatie = tipValuatDestinatie;
        this.valoare = valoare;
    }

    public void executa() {
        try {
            utilizator.schimbValutar(tipValutaSursa, tpValutaDestinatie, suma, valoare);
        } catch (EroareSumaInsuficienta e) {
            System.out.println("Insufficient amount in account " + tipValutaSursa + " for exchange");
        }
    }
}
