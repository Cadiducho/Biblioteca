package com.cadiducho.biblio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Libreria implements Serializable {
    
    private final List<Libro> collection;
    
    public Libreria() {
        collection = new ArrayList<>();
    }
    
    public void addLibro(Libro l) {
        collection.add(l);
    }
    
    @Override
    public String toString() {
        String s = "\n";
        
        Iterator<Libro> i = collection.iterator();
        while(i.hasNext()){
            Libro l = (Libro) i.next();
            s = s + l.toString();
        }
	return s;
    }
}
