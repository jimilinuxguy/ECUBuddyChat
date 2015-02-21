
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    
    public GroupClass GroupClassDAO(Integer groupId) {
        GroupClass group = new GroupClass();
        try {
            Connection connection = null;
            try {

                String groupQuery = "SELECT id,group_name,active from groups where id=" + groupId;
                DataConnectionClass dcc = new DataConnectionClass();
                connection = dcc.getConnection();
                
                PreparedStatement statement = connection.prepareStatement(groupQuery);
                ResultSet resultSet = statement.executeQuery();
                
                if ( resultSet.first() ) {
                    group.setGroupId(resultSet.getInt("id"));
                    group.setActive(resultSet.getBoolean("active"));
                    group.setGroupName(resultSet.getString("group_name"));
                }

            } catch (SQLException ex) {
                //TODO
            }
        } catch (Exception ex) {
            //TODO
        }

        return group;
    }
}
