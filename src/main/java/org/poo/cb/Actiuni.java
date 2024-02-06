package org.poo.cb;

import java.util.ArrayList;
import java.util.Date;

public class Actiuni {
    String numeCompanie;
    int nrActiuni;
    ArrayList<Double> valori;

    public Actiuni(String numeCompanie, int nrActiuni) {
        this.numeCompanie = numeCompanie;
        this.valori = new ArrayList<>();
        this.nrActiuni = nrActiuni;
    }

    public String getNumeCompanie() {
        return numeCompanie;
    }

    public ArrayList<Double> getValori() {
        return valori;
    }

    public int getNrActiuni() {
        return nrActiuni;
    }
}
