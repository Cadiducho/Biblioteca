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
    private final MySQL mysql;
    private final Connection con;
    
    public static void main(String[] args) {
        new Biblioteca().launch(args);
    }
    
    private Biblioteca() {
        mysql = new MySQL("cadi", "biblio", "pass", "3306", "localhost");
        con = null;
        try {
            mysql.openConnection();
            System.out.println("Se ha conectado a MySQL");
            mysql.crearTabla();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("No se ha podido conectar a mysql: "+ex.getLocalizedMessage());
        }
    }
    
    private void launch(String[] args) {
        do {
            menu();
            int respuesta = in.nextInt();
            switch (respuesta) {
                case 0:
                    cargarLibreria();
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
        } while (running);
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
    
    private void cargarLibreria() {
        String sql = "SELECT * FROM `libros`";
        try {
            ResultSet rs = mysql.querySQL(sql);
            while (rs.next()) {
                Libro libro = new Libro(rs.getString("titulo"), rs.getString("editorial"), rs.getString("autor"), rs.getInt("año"), true);
                lib.addLibro(libro);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("No se ha podido cargar la base de datos");
        }
        
        /*FileInputStream fis;
	ObjectInputStream inp;
	File file = new File(str + ".lib");
        if (file.exists()) {
            try {
                fis = new FileInputStream(file);
		inp = new ObjectInputStream(fis);
		lib = (Libreria) inp.readObject();
		fis.close();
		inp.close();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error cargando la libreria: "+e.getLocalizedMessage());
            }
        } else {
            System.out.println("Ese archivo no existe!");
	}*/
    }
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
        /*System.out.println("Escribe el nombre del archivo: ");
	String fileName = in.next() + ".lib";
	running = false;
	FileOutputStream fos;
	ObjectOutputStream out;
	try {
            fos = new FileOutputStream(fileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(lib);
            fos.close();
            out.close();
	} catch (IOException e) {
            System.out.println("Error guardando la libreria: "+e.getLocalizedMessage());
        }*/
    }
    
}
