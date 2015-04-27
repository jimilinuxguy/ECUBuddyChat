/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import BuddyDB.DataConnectionClass;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jimisanchez
 */
@WebServlet(urlPatterns = {"/GroupRegister"})
public class GroupRegister extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String groupName;
        groupName = request.getParameter("groupname");
        
        Connection connection = null;
        DataConnectionClass dcc = new DataConnectionClass();
        connection = dcc.getConnection();
        List<String> validationWarnings = new ArrayList<String>();
        try {
            Statement stmt = (Statement) connection.createStatement();
            try {
                ResultSet rs = stmt.executeQuery("SELECT * FROM groups WHERE group_name = '" + groupName + "'");
                if ( rs.first() ) {
                    validationWarnings.add("The desired group name is already in use. Choose another.");
                } else {
                    String sql = "INSERT INTO groups (id,group_name,active)" + "VALUES (NULL, ?, ?)";
                    PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                    preparedStatement.setString(1, groupName);
                    preparedStatement.setBoolean(2,true);
                    
                    preparedStatement.executeUpdate(); 
                }
            } catch (Exception Ex) {
                System.out.println(Ex.getMessage());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
                                    String site = new String("groups.jsp");
                            response.setStatus(response.SC_MOVED_TEMPORARILY);
                            response.setHeader("Location", site);

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
        return "Short description";
    }// </editor-fold>

}
