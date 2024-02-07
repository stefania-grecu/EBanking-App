package org.poo.cb;

import java.util.ArrayList;

public class ActiuniContainer implements Container<Actiuni> {

    private ArrayList<Actiuni> actiuni;
    public ActiuniContainer(ArrayList<Actiuni> actiuni) {
        this.actiuni = actiuni;
    }
    @Override
    public Iterator<Actiuni> getIterator() {
        return new ActiuniIterator(actiuni);
    }

    public void add(Actiuni a) {
        actiuni.add(a);
    }
}
