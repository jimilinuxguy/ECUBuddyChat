
import com.mysql.jdbc.Connection;
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
    private String email;
    
    public UserClassDAO(String firstName, String lastName,String email) {
       
        if ( firstName != null) {
            this.firstName = firstName;
        }
        
        if ( lastName != null) {
            this.lastName = lastName;
        }
        
        if ( email != null) {
            this.email = email;
        }
        
    }
    
    public List<UserClass> list() throws SQLException {
        
        List<UserClass> users = new ArrayList<UserClass>();

        Connection connection = null;
        
        try {
            DataConnectionClass dcc = new DataConnectionClass();
            connection = dcc.getConnection();
            
            String userQuery = "SELECT id,email,firstname,lastname from users ";
            
            if (  !email.isEmpty() ) {
                userQuery += " WHERE email = '"+email+"'";
            }
            else if (this.lastName != null && this.lastName !="" && this.firstName != null && this.firstName !="") {
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
