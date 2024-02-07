package org.poo.cb;

import java.util.ArrayList;
import java.util.List;

public class CoadaComenzi {
    private final List<Comanda> coada = new ArrayList<>();
    public void adaugaCoada(Comanda comanda) {
        coada.add(comanda);
    }

    public void scosCoada() throws EroareContExistent {
        for (Comanda c : coada) {
            c.executa();
        }
        coada.clear();
    }
}
