/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import BuddyDB.DataConnectionClass;
import com.mysql.jdbc.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
 * @author jj regan
 */
@WebServlet(urlPatterns = {"/ForgotPassword"})
public class UserForgotPassword extends HttpServlet {

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
        Connection connection = null;
        
        List<String> validationWarnings = new ArrayList<String>();
        HttpSession session = request.getSession(true);
        
        try {
            
            username = request.getParameter("email");
            
            if ( username.length() > 0 ) {
                
                try 
                {
                    DataConnectionClass dcc = new DataConnectionClass();
                    connection = dcc.getConnection();
                    System.out.println("Here");
                    System.out.println(connection);
                    
                    try 
                    {
                        String password = null;
                        //change passphrase
                        String sql = "UPDATE users SET passphrase = ? WHERE email = '"+ username +"'";
                        PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                        password = request.getParameter("password");
                        MessageDigest md = null;
                        try 
                        {
                            md = MessageDigest.getInstance("MD5");
                        } 
                        catch (NoSuchAlgorithmException ex) 
                        {
                            validationWarnings.add("An error occured");
                        }
                        md.update(password.getBytes());
                        byte byteData[] = md.digest();

                        //convert the byte to hex format method 1
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < byteData.length; i++) 
                        {
                            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
                        }
            
                        String md5Password = sb.toString();
                        
                        preparedStatement.setString(1, md5Password);

                        preparedStatement.executeUpdate(); 

                        session.setAttribute("message","Your new password is "+ password);
                        session.setAttribute("passwordChange", "Your password has been successfully changed!\n"+
                                "Login with your new password.");
                    }
                    catch (Exception ex) 
                    {
                        System.out.println(ex);
                    }
                } 
                catch (Exception ex) 
                {
                    System.out.println(ex);
                }
            }
            else 
            {
                validationWarnings.add("No data received");
            }
        }
        finally 
        {
            if (validationWarnings.size() > 0) 
            {
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
