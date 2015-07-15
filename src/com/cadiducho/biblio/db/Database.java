
package com.cadiducho.biblio.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Database {
    
    protected Connection connection;
    
    protected Database() {
        connection = null;
    }
    
    public abstract Connection openConnection() throws SQLException, ClassNotFoundException;
    
    public boolean checkConnection() throws SQLException {
        return connection != null && !connection.isClosed();
    }
    
    public boolean closeConnection() throws SQLException {
        if (connection == null) {
            return false;
        }
        connection.close();
        return true;
    }
    
    public ResultSet querySQL(String str) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) {
            openConnection();
        }
        
        Statement statement = connection.createStatement();
        return statement.executeQuery(str);
    }
    
    public int updateSQL(String str) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) {
            openConnection();
        }
        
        Statement statement = connection.createStatement();
        return statement.executeUpdate(str);
    }
    
    public boolean crearTabla() {
        try {
            String sql = null;
            if (connection.toString().contains("com.mysql.jbc")) {
                sql = "CREATE TABLE IF NOT EXISTS `libros` ( "
                    + "`titulo` VARCHAR(255) NOT NULL , "
                    + "`editorial` VARCHAR(255) NOT NULL , "
                    + "`autor` VARCHAR(255) NOT NULL , "
                    + "`año` INT(4) NULL DEFAULT '1' COMMENT 'Año de la edición' , "
                    + "`id` INT(4) NOT NULL AUTO_INCREMENT COMMENT 'ID del libro', PRIMARY KEY (`id`) );"
                    + "ENGINE = InnoDB COMMENT = 'Tabla principal';";
            } else if (connection.toString().contains("sqlite")) {
                sql = "CREATE TABLE IF NOT EXISTS `libros` ( "
                    + "`titulo` VARCHAR(255) NOT NULL , "
                    + "`editorial` VARCHAR(255) NOT NULL , "
                    + "`autor` VARCHAR(255) NOT NULL , "
                    + "`año` INT(4) NULL DEFAULT '1' , "
                    + "`id` INT(4) NOT NULL, PRIMARY KEY (`id`) );";
            }
            updateSQL(sql);
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("No se ha podido crear la tabla: "+ex.getLocalizedMessage());
            return false;
        }
    }
}
