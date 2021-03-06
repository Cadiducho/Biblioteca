package com.cadiducho.biblio.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class MySQL {

    protected Connection connection;

    private final String user, database, password, port, hostname;

    public MySQL(String hostname, String port, String database, String username, String password) {
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.user = username;
        this.password = password;
    }

    public boolean checkConnection() throws SQLException {
        return connection != null && !connection.isClosed();
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean closeConnection() throws SQLException {
        if (connection == null) {
            return false;
        }
        connection.close();
        return true;
    }

    public Connection openConnection() throws SQLException, ClassNotFoundException {
        if (checkConnection()) {
            return connection;
        }

        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://"
                + this.hostname + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.user, this.password);
        return connection;
    }


    public boolean crearTabla() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS `libros` ( "
                    + "`titulo` VARCHAR(255) NOT NULL , "
                    + "`editorial` VARCHAR(255) NOT NULL , "
                    + "`autor` VARCHAR(255) NOT NULL , "
                    + "`año` INT(4) NULL DEFAULT '1' COMMENT 'Año de la edición' , "
                    + "`id` INT(4) NOT NULL AUTO_INCREMENT COMMENT 'ID del libro', PRIMARY KEY (`id`) );";
            PreparedStatement statement = openConnection().prepareStatement(sql);
            statement.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("No se ha podido crear la tabla: " + ex.getLocalizedMessage());
            return false;
        }
    }
}
