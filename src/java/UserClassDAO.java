
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jimisanchez
 */
public class UserClassDAO {
    
    private String firstName;
    private String lastName;
    
    public UserClassDAO(String firstName, String lastName) {
       
        if ( firstName != null) {
            this.firstName = firstName;
        }
        
        if ( lastName != null) {
            this.lastName = lastName;
        }
        
    }
    
    public List<UserClass> list() throws SQLException {
        
        List<UserClass> users = new ArrayList<UserClass>();

        String mysql_host = Configuration.getMySQLHost();
        String mysql_username = Configuration.getMySQLUser();
        String mysql_password = Configuration.getMySQLPassword();
        String mysql_db = Configuration.getMySQLDatabase();        
        
        try {

            Class.forName("com.mysql.jdbc.Driver");
            String connectionURL = "jdbc:mysql://"+mysql_host+"/"+mysql_db;
            Connection connection = null;
            connection = (Connection) DriverManager.getConnection(connectionURL, mysql_username, mysql_password);
            String userQuery = "SELECT id,email,firstname,lastname from users ";
            if (this.lastName != null && this.lastName !="" && this.firstName != null && this.firstName !="") {
                userQuery += " WHERE firstname like '%"+this.firstName+"%' and lastname='%"+this.lastName+"%' ";
            } else if ( this.lastName != null && this.lastName != "") {
               userQuery += " WHERE lastname like '%"+this.lastName+"%' "; 
            } else if ( this.firstName != null && this.firstName != "") {
                userQuery += " WHERE firstname like '%"+this.firstName+"%' ";
            }
            System.out.println("Query = " + userQuery);
            PreparedStatement statement = connection.prepareStatement(userQuery);
            ResultSet resultSet = statement.executeQuery();
            
             while (resultSet.next()) {
                 
                 UserClass user = new UserClass();
                 
                 user.setUserId( resultSet.getInt("id"));
                 user.setFirstName( resultSet.getString("firstname"));
                 user.setLastName( resultSet.getString("lastname"));
                 user.setUserName( resultSet.getString("email"));

                 users.add(user);

             }
            
        } catch (Exception ex) {
            
        }
        
        return users;
    }
}
