package org.poo.cb;

import java.util.ArrayList;

public class ActiuniIterator implements Iterator<Actiuni> {
    private ArrayList<Actiuni> actiuni;
    private int index = 0;

    public ActiuniIterator(ArrayList<Actiuni> actiuni) {
        this.actiuni = actiuni;
    }

    @Override
    public boolean hasNext() {
        return index < actiuni.size();
    }

    @Override
    public Actiuni next() {
        if (this.hasNext()) {
            return actiuni.get(index++);
        }
        return null;
    }
}
