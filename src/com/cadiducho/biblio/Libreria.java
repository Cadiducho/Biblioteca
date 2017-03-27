package com.cadiducho.biblio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;


public class Libreria implements Serializable {

    private final ArrayList<Libro> collection;

    public Libreria() {
        collection = new ArrayList<>();
    }

    public void addLibro(Libro l) {
        collection.add(l);
    }

    public void removeLibro(Libro l) {
        collection.remove(l);
    }

    public ArrayList<Libro> getLibros() {
        return collection;
    }

    public int getTotalLibros() {
        int i = 0;
        Iterator<Libro> it = collection.iterator();
        while (it.hasNext()) {
            i++;
        }
        return i;
    }

    @Override
    public String toString() {
        String s = "\n";

        Iterator<Libro> i = collection.iterator();
        while (i.hasNext()) {
            Libro l = i.next();
            s = s + l.toString();
        }
        return s;
    }
}
