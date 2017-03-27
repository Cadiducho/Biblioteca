package com.cadiducho.biblio;

import com.cadiducho.biblio.db.MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Biblioteca {

    private static boolean running = true;
    private static Scanner in = new Scanner(System.in);
    private static Libreria lib = new Libreria();

    private static MySQL mySQL;
    private static Connection connection;

    private Biblioteca() {
        try {
            mySQL = new MySQL("localhost", "3306", "libros", "root","password");
            mySQL.crearTabla();
            connection = mySQL.openConnection();
            cargarLibreria();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("No se ha podido conectar con la base de datos");
            running = false;
        }
    }

    public static void main(String[] args) {
        new Biblioteca().launch(args);
    }

    private void launch(String[] args) {
        while (running) {
            menu();
            int respuesta = in.nextInt();
            switch (respuesta) {
                case 1:
                    System.out.println(lib.getLibros());
                    break;
                case 2:
                    addLibro();
                    break;
                case 3:
                    removeLibro();
                    break;
                case 4:
                    save(); //Por seguridad
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
                + "\n1. Ver los libros que hay almacenados"
                + "\n2. Agregar un nuevo libro"
                + "\n3. Borrar un libro"
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
        try {
            año = Integer.parseInt(in.next());
        } catch (InputMismatchException | NumberFormatException e){ //No queremos errores
            año = Calendar.getInstance().get(Calendar.YEAR);
        }

        Libro l = new Libro(titulo, editorial, autor, año, false);
        lib.addLibro(l);
        save();
        System.out.println("Libro añadido: " + l.toString());
    }

    private void removeLibro(){
        String titulo;

        System.out.println("\nEscribe el titulo: ");
        titulo = in.next();

        for (Libro l : lib.getLibros()){
            if (l.getTitulo().equalsIgnoreCase(titulo)){
                try {
                    PreparedStatement ps = mySQL.getConnection().prepareStatement("DELETE FROM `libros` WHERE `titulo` = ?");
                    ps.setString(1, titulo);
                    ps.executeUpdate();
                    lib.removeLibro(l);
                    break;
                } catch (SQLException ex) {
                    System.out.println("No se ha podido borrar el libro: "  + ex.getLocalizedMessage());
                }
            }
        }
        System.out.println("Libro borrado: " + titulo);
    }

    private void cargarLibreria() {
        String sql = "SELECT * FROM `libros`";
        try {
            ResultSet rs = mySQL.getConnection().prepareStatement(sql).executeQuery();
            while (rs.next()) {
                Libro libro = new Libro(rs.getString("titulo"), rs.getString("editorial"), rs.getString("autor"), rs.getInt("año"), true);
                lib.addLibro(libro);
            }
        } catch (SQLException ex) {
            System.out.println("No se ha podido cargar la base de datos");
        }
    }

    private void save(){
        for (Libro libro : lib.getLibros()) {
            if (!libro.cached) {
                try {
                    String sql = "INSERT INTO `libros` ("
                            + "`titulo`, `editorial`, `autor`, `año`, `id`) "
                            + "VALUES ('" + libro.getTitulo() + "', '" + libro.getEditorial() + "',"
                            + " '" + libro.getAutor() + "', '" + libro.getAño() + "', NULL);";
                    PreparedStatement statement = mySQL.getConnection().prepareStatement(sql);
                    statement.executeUpdate();
                } catch (SQLException ex) {
                    System.out.println("No se ha podido guardar el libro: " + ex.getLocalizedMessage());
                }
            }
        }
    }
}
