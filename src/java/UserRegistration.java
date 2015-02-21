/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import EmailFormatValidator.EmailFormatValidator;
import com.mysql.jdbc.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
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
@WebServlet(urlPatterns = {"/UserRegistration"})
public class UserRegistration extends HttpServlet {

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
        String username = null;
        String password = null;
        String firstName = null;
        String lastName = null;
        
        List<String> validationWarnings = new ArrayList<String>();

        try {
            //attempt to read from the post variables
            username = request.getParameter("email");
            password = request.getParameter("password");
            
            firstName = request.getParameter("firstname");
            lastName = request.getParameter("lastname");
            
            HttpSession session = request.getSession(true);

            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException ex) {
                validationWarnings.add("An error occured");
            }
            md.update(password.getBytes());
            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            String md5Password = sb.toString();
            
            if (username.length() > 0) {

                EmailFormatValidator emailFormatValidator = new EmailFormatValidator();

                Boolean isValidEmail = emailFormatValidator.validate(username);

                if (!isValidEmail) {
                    validationWarnings.add("Email address is invalid");
                }

            } else {
                validationWarnings.add("Email address is required");
            }
            // Check to make sure password received
            if (password.length() > 0) {

                //make sure at least 8 characters
                if (password.length() < 8) {
                    validationWarnings.add("Password minimum length not met");
                }

            } else {
                //Password not passed through
                validationWarnings.add("Password required.");
            }
            System.out.println("Size = " +validationWarnings.size());
            //Do we have any warnings?
            if (validationWarnings.size() > 0) {
                session.setAttribute("errors", validationWarnings);
                this.redirectToRegistration(response);
                return;
            }

            try {
                try {
                    
                    Connection connection = null;
                    DataConnectionClass dcc = new DataConnectionClass();
                    connection = dcc.getConnection();
                    
                    try {
                        
                        Statement stmt = (Statement) connection.createStatement();
                      
                        try {
                          
                            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE email = '" + username + "'");
                            
                            if (rs.first()) {
                                // This means the user already exists
                                validationWarnings.add("The desired username is already in use. Choose another, or reset your password if this account belongs to you");
                            } else {
                                //Username not in use
                                // Do registration
                                String sql = "INSERT INTO users (email,passphrase,firstname,lastname)" + "VALUES (?, ?, ?, ?)";
                                PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                           
                                preparedStatement.setString(1, username);
                                preparedStatement.setString(2, md5Password);
                                preparedStatement.setString(3,firstName);
                                preparedStatement.setString(4,lastName);
                                
                                preparedStatement.executeUpdate(); 
                                
                                session.setAttribute("message","Registration successful");

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
