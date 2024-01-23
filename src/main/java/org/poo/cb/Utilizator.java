package org.poo.cb;

import java.util.ArrayList;
import java.util.List;

public class Utilizator {
    String email, nume, prenume, adresa;
    ArrayList<Utilizator> prieteni;
    ArrayList<Cont> cont;

    List<Actiuni> actiuni;

    public Utilizator(String email, String nume, String prenume, String adresa) {
        this.email = email;
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = adresa;
        this.prieteni = new ArrayList<>();
        this.cont = new ArrayList<>();
        this.actiuni = new ArrayList<>();
    }

    public void adaugareCont(String tipValuta) throws EroareContExistent {
        ContFactory c = ContFactory.Instanta();
        Cont x = c.creazaCont(tipValuta, 0);

        if (x == null)
            throw new EroareContExistent(tipValuta);

        cont.add(x);
    }

    public void adaugareBani(String tipValuta, double suma) {
        for (Cont c : cont)
            if (c.getClass().getSimpleName().equals(tipValuta + "Cont"))
                c.adaugaSuma(suma);
    }

    public void schimbValutar(String tipValutaSursa, String tipValuatDestinatie, double suma) {

    }

}
