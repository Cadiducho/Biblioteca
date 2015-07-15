package com.cadiducho.biblio.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MySQL extends Database {
    private final String user;
    private final String database;
    private final String pass;
    private final String port;
    private final String host;
    
    public MySQL(String u, String db, String pass, String port, String h) {
       this.user = u;
       this.database = db;
       this.pass = pass;
       this.port = port;
       this.host = h;
    }
    
    @Override
    public Connection openConnection() throws SQLException, ClassNotFoundException {
        if (checkConnection()) {
            return connection;
        }
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://"
		+ this.host + ":" + this.port + "/" + this.database,
		this.user, this.pass);
        return connection;
    }
    
    
}
