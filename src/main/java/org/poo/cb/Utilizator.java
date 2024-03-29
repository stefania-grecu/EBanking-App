package org.poo.cb;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class Utilizator {
    String email, nume, prenume, adresa;
    ArrayList<Utilizator> prieteni;
    ArrayList<Cont> cont;
    ActiuniContainer actiuni;
    boolean x;

    public Utilizator(String email, String nume, String prenume, String adresa, ArrayList<Actiuni> actiuni) {
        this.email = email;
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = adresa;
        this.prieteni = new ArrayList<>();
        this.cont = new ArrayList<>();
        this.actiuni = new ActiuniContainer(actiuni);
        this.x = false;
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

    public void schimbValutar(String tipValutaSursa, String tipValuatDestinatie, double suma, String[][] valoare) throws EroareSumaInsuficienta {
        int s = 0, d = 0;
        //aici m am gandit ca ar fi un fel de matrice
        //pe prima coloana sa fie tipul valutei
        for (int v = 0; v < valoare[0].length; v++) {
            if(tipValutaSursa.equalsIgnoreCase(valoare[0][v]))
                s = v;
            if(tipValuatDestinatie.equalsIgnoreCase(valoare[0][v]))
                d = v;
        }

        Cont sursa = null, destinatie = null;

        sursa = cautareCont(cont, tipValutaSursa, sursa);
        destinatie = cautareCont(cont, tipValuatDestinatie, destinatie);

        double val = Double.parseDouble(valoare[d][s]);
        double sumaNoua = suma * val;

        if (sumaNoua > sursa.getSuma())
            throw new EroareSumaInsuficienta(tipValutaSursa);

        if (sumaNoua > (sursa.suma / 2) && !x)
            sursa.suma = sursa.suma - sumaNoua/100;

        sursa.suma = sursa.suma - sumaNoua;
        destinatie.suma = destinatie.suma + suma;
    }

    public void transferBani(Utilizator prieten, String tipValuta, double suma) throws EroareSumaInsuficientaTransfer {
        Cont c = null, cp = null;

        c = cautareCont(cont, tipValuta, c);
        cp = cautareCont(prieten.cont, tipValuta, cp);

        if (suma > c.getSuma())
            throw new EroareSumaInsuficientaTransfer(tipValuta);

        c.suma = c.suma - suma;
        cp.suma = cp.suma + suma;
    }

    public void cumparareActiuni(double suma, int nrActiuni, String numeCompanie) throws EroareSumaInsuficientaActiuni {
        Cont c = null;
        int k = 0;

        c = cautareCont(cont, "USD", c);

        if (suma * nrActiuni > c.getSuma())
            throw new EroareSumaInsuficientaActiuni();

        Iterator<Actiuni> iterator = actiuni.getIterator();

        while (iterator.hasNext()) {
            Actiuni a = iterator.next();

            if (a.getNumeCompanie().equals(numeCompanie)) {
                a.adaugaNrActiuni(nrActiuni);
                k = 1;
            }
        }

        if (k == 0) {
            Actiuni a = new Actiuni(numeCompanie, nrActiuni);
            actiuni.add(a);
        }

        Context context;
        double s;

        if (!x)
            context = new Context(new StrategieNormala());
        else
            context = new Context(new StrategiePremium());

        s = context.executeStrategie(suma, nrActiuni);
        c.suma = c.suma - s;
    }

    public void premium() throws EroareSumaInsuficientaPremium {
        if (!x) {
            Cont c = null;

            c = cautareCont(cont, "USD", c);

            if (100 > c.getSuma())
                throw new EroareSumaInsuficientaPremium();

            c.suma = c.suma - 100;
            x = true;
        }
    }

    private Cont cautareCont(ArrayList<Cont> cont, String tipValuta, Cont c) {
        for (Cont x : cont) {
            String tipCont = x.getClass().getSimpleName().substring(0, 3);
            if (tipCont.equalsIgnoreCase(tipValuta))
                c = x;
        }
        return c;
    }

}
