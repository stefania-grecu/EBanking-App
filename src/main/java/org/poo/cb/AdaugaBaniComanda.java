package org.poo.cb;

public class AdaugaBaniComanda implements Comanda{
    private String tipValuta;
    private Utilizator utilizator;
    private  double suma;

    public AdaugaBaniComanda(Utilizator utilizator, String tipValuta, float suma) {
        this.utilizator = utilizator;
        this.suma = suma;
        this.tipValuta = tipValuta;
    }

    public void executa() {
        utilizator.adaugareBani(tipValuta, suma);
    }
}
