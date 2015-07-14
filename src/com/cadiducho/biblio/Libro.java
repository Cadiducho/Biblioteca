package com.cadiducho.biblio;

import java.io.Serializable;

public class Libro implements Serializable {
    
    private final String titulo, editorial, autor;
    private final int año;
    public final boolean cached;
    
    public Libro() {
        this.titulo = null;
        this.editorial = null;
        this.autor = null;
        this.año = 0;
        this.cached = false;
    }
    public Libro(String t, String e, String a, int año, boolean cached) {
        this.titulo = t;
        this.editorial = e;
        this.autor = a;
        this.año = año;
        this.cached = cached;
    }
    
    public String getTitulo() {
        return this.titulo;
    }
    
    public String getEditorial() {
        return this.editorial;
    }
    
    public String getAutor() {
        return this.autor;
    }
    
    public int getAño() {
        return this.año;
    }
    
    @Override
    public String toString() {
        return "Libro {Titulo:"+titulo+", Editorial:"+editorial+", Autor:"+autor+", Año:"+año+"}";
    }
}
