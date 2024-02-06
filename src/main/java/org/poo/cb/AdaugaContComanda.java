package org.poo.cb;

public class AdaugaContComanda implements Comanda {
    private String tipValuta;
    private Utilizator utilizator;

    public AdaugaContComanda(Utilizator utilizator, String tipValuta) {
        this.utilizator = utilizator;
        this.tipValuta = tipValuta;
    }

    public void executa() {
        try {
            utilizator.adaugareCont(tipValuta);
        } catch (EroareContExistent e) {
            System.out.println("User with " + tipValuta + " is already a friend");
        }

    }
}
