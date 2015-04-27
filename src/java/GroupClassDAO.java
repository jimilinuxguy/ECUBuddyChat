
import BuddyDB.DataConnectionClass;
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
public class GroupClassDAO {
    private String groupName; 
     
    public GroupClassDAO(String groupName) {
                 if ( groupName != null) {
            this.groupName = groupName;
        }
    }
      public List<GroupClass> list() throws SQLException {
             List<GroupClass> groups = new ArrayList<GroupClass>();
             GroupClass group = new GroupClass();

             try {
            Connection connection = null;
            try {

                String groupQuery = "SELECT id,group_name,active from groups WHERE group_name ='" + groupName+"'";
                System.out.println(groupQuery);
                DataConnectionClass dcc = new DataConnectionClass();
                connection = dcc.getConnection();
                
                PreparedStatement statement = connection.prepareStatement(groupQuery);
                ResultSet resultSet = statement.executeQuery();
                
                if ( resultSet.first() ) {
                    group.setGroupId(resultSet.getInt("id"));
                    group.setActive(resultSet.getInt("active"));
                    group.setGroupName(resultSet.getString("group_name"));
                }
  

                 groups.add(group);

            } catch (SQLException ex) {
                //TODO
            }
        } catch (Exception ex) {
            //TODO
        }

        return groups;
    }
}
