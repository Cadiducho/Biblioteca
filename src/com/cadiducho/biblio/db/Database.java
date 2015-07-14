
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
}
