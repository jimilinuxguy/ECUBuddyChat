/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import BuddyDB.DataConnectionClass;
import com.mysql.jdbc.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jimisanchez
 */
@WebServlet(urlPatterns = {"/UserLogin"})
public class UserLogin extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        String username = null;
        String password = null;
        Connection connection = null;
        
        List<String> validationWarnings = new ArrayList<String>();
        HttpSession session = request.getSession(true);
        
        try {
            
            username = request.getParameter("email");
            password = request.getParameter("password");
            
            if ( username.length() > 0 && password.length() > 0 ) {
                
                try {
                    DataConnectionClass dcc = new DataConnectionClass();
                    connection = dcc.getConnection();
                    System.out.println("Here");
                    System.out.println(connection);
                    
                    try {

                        Statement stmt = (Statement) connection.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT id,email FROM users WHERE email = '" + username + "' AND passphrase=MD5('"+password+"') ");
                        Boolean successLogin = rs.first();
                        System.out.println("There = " + successLogin);
                    
                        if (successLogin) {
                            session.setAttribute("user_id", rs.getLong("id"));
                            session.setAttribute("username",rs.getString("email"));
                            String site = new String("index.jsp");
                            response.setStatus(response.SC_MOVED_TEMPORARILY);
                            response.setHeader("Location", site);
                        } else {
                            validationWarnings.add("Invalid username or password");
                        }
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            } else {
                validationWarnings.add("No data received");
            }
        } finally {

            if (validationWarnings.size() > 0) {
                session.setAttribute("errors", validationWarnings);  
            }
            
            String site = new String("index.jsp");
            response.setStatus(response.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", site);

            out.close();
        }
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
