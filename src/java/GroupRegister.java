
import BuddyDB.DataConnectionClass;
import EmailFormatValidator.EmailFormatValidator;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lauers14
 */
public class GroupRegister  extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @return boolean
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        //Initialize username and password variables
        String groupname = null;
        int id = 0;
        int active = 1;
         
        List<String> validationWarnings = new ArrayList<String>();

        try {
            //attempt to read from the post variables
            groupname = request.getParameter("group_name");
            
            HttpSession session = request.getSession(true);

            
          try {
                try {
                    
                    Connection connection = null;
                    DataConnectionClass dcc = new DataConnectionClass();
                    connection = dcc.getConnection();
                    
                    try {
                        
                        Statement stmt = (Statement) connection.createStatement();
                      
                        try {
                          
                            ResultSet rs = stmt.executeQuery("SELECT * FROM groups WHERE group_name = '" + groupname + "'");
                            
                            if (rs.first()) {
                                // This means the user already exists
                                validationWarnings.add("The desired group name is already in use. Choose another.");
                            } else {
                                //Groupname not in use
                                // Do create group
                                id =id +1;
                                String sql = "INSERT INTO groups (id,group_name,active)" + "VALUES (?, ?, ?)";
                                PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                           
                                preparedStatement.setInt(1, id);
                                preparedStatement.setString(2, groupname);
                                preparedStatement.setInt(3,active);
                                
                                preparedStatement.executeUpdate(); 
                                
                                session.setAttribute("message","Group Creaction Successful");

                            }

                        } catch (Exception ex) {
                            validationWarnings.add("An error occured");
                        }

                    } catch (Exception ex) {
                        validationWarnings.add("An error occured");
                    }

                    connection.close();
                } catch (SQLException ex) {
                    validationWarnings.add("An error occured");
                }
        } catch (Exception ex) {
            validationWarnings.add("No data received");
        }
        //Do we have any warnings?
        if (validationWarnings.size() > 0) {
                session.setAttribute("errors", validationWarnings);  
                this.redirectToRegistration(response);
        }
        } finally {
            //Todo
        }
        this.redirectToLogin(response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "ECU Buddy Chat Registration Service";
    }// </editor-fold>

    public void redirectToRegistration(HttpServletResponse response) {
        String site = new String("register.jsp");
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", site);
        return;
    }
    
    public void redirectToLogin(HttpServletResponse response) {
        String site = new String("index.jsp");
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", site);
        return;
    }
    
    
}
