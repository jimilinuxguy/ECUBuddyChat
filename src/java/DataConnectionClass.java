
import com.mysql.jdbc.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jimisanchez
 */
public class DataConnectionClass {
    
    public Connection connection = null;

    public DataConnectionClass() {
        //Constructor stuff? nah
        try {
            System.out.println("HERE");
            Class.forName("com.mysql.jdbc.Driver");
            String mysql_host = Configuration.getMySQLHost();
            String mysql_username = Configuration.getMySQLUser();
            String mysql_password = Configuration.getMySQLPassword();
            String mysql_db = Configuration.getMySQLDatabase();
            String connectionURL = "jdbc:mysql://"+mysql_host+"/"+mysql_db;
            this.connection = (Connection) DriverManager.getConnection(connectionURL, mysql_username, mysql_password);
        } catch (Exception ex) {
            System.out.println("Here = " + ex);
        }    
    }    

    public Connection getConnection() {
        return this.connection;
    }
}
