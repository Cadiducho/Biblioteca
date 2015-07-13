package com.cadiducho.biblio;

import java.io.Serializable;

public class Libro implements Serializable {
    
    private final String titulo, editorial, autor;
    private final int año;
    
    public Libro() {
        this.titulo = null;
        this.editorial = null;
        this.autor = null;
        this.año = 0;
    }
    public Libro(String t, String e, String a, int año) {
        this.titulo = t;
        this.editorial = e;
        this.autor = a;
        this.año = año;
    }
    
    @Override
    public String toString() {
        return "Libro {Titulo:"+titulo+", Editorial:"+editorial+", Autor:"+autor+", Año:"+año+"}";
    }
}
