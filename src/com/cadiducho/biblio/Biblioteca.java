package com.cadiducho.biblio;

import com.cadiducho.biblio.db.MySQL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Biblioteca {

    static boolean running = true;
    static Scanner in = new Scanner(System.in);
    static Libreria lib = new Libreria();
    private MySQL mysql = null;
    private Connection con = null;
    
    public static void main(String[] args) {
        new Biblioteca().launch(args);
    }
    
    private Biblioteca() {
        /*con = sqlite.openConnection();
        if (!sqlite.crearTabla()) running = false;
        System.out.println("Se ha conectado a SQLite");*/
    }
    
    private void launch(String[] args) {
        while (running) {
            menu();
            int respuesta = in.nextInt();
            switch (respuesta) {
                case 0:
                    //cargarLibreria();
                    break;
                case 1:
                    System.out.println(lib.getLibros());
                    break;
                case 2:
                    addLibro();
                    break;
                case 3:
                    guardarSalir();
                    break;
                case 4:
                    running = false;
                    break;
                default: 
                    System.out.println("Opción no válida.");
            }
        }
        System.exit(0);
    }
    
    private void menu() {
        System.out.println("Elige una opción numérica:"
                + "\n0. Cargar libros desde mysql"
                + "\n1. Ver los libros que hay almacenados"
                + "\n2. Agregar un nuevo libro"
                + "\n3. Guardar y salir"
                + "\n4. Salir");
    }
    
    private void addLibro() {
        String titulo, editorial, autor;
        int año;

	System.out.println("\nEscribe el titulo: ");
	titulo = in.next();
	System.out.println("\nEscribe la editorial: ");
	editorial = in.next();
	System.out.println("\nEscribe el autor: ");
	autor = in.next();
	System.out.println("\nEscribe el año: ");
	año = in.nextInt();

	Libro l = new Libro(titulo, editorial, autor, año, false);
	lib.addLibro(l);
    }
    /*
    private void cargarLibreria() {
        String sql = "SELECT * FROM `libros`";
        try {
            ResultSet rs = connection.querySQL(sql);
            while (rs.next()) {
                Libro libro = new Libro(rs.getString("titulo"), rs.getString("editorial"), rs.getString("autor"), rs.getInt("año"), true);
                lib.addLibro(libro);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("No se ha podido cargar la base de datos");
        }    
    }*/
    
    private void guardarSalir() {
        for (Libro libro : lib.getLibros()) {
            if (!libro.cached) {
                try {
                    String sql = "INSERT INTO `biblio`.`libros` ("
                        + "`titulo`, `editorial`, `autor`, `año`, `id`) "
                        + "VALUES ('"+libro.getTitulo()+"', '"+libro.getEditorial()+"',"
                        + " '"+libro.getAutor()+"', '"+libro.getAño()+"', NULL);";
                    mysql.updateSQL(sql);
                } catch (SQLException | ClassNotFoundException ex) {
                    System.out.println("No se ha podido guardar el libro: "+ex.getLocalizedMessage());
                }
            }
        }
        running = false;
    }
    
}
