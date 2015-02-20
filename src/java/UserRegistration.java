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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    protected boolean processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        //Initialize username and password variables
        String username = null;
        String password = null;
        List<String> validationWarnings = new ArrayList<String>();

        try {
            //attempt to read from the post variables
            username = request.getParameter("email");
            password = request.getParameter("password");

            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UserRegistration.class.getName()).log(Level.SEVERE, null, ex);
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

            //Do we have any warnings?
            if (validationWarnings.size() > 0) {

                //Iterate over form validations and display
                for (String warning : validationWarnings) {
                    out.println(warning + "<br/>");
                }

                return false;
            }

            try {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    String connectionURL = "jdbc:mysql://127.0.0.1/ecubuddychat";
                    Connection connection = null;
                    connection = (Connection) DriverManager.getConnection(connectionURL, "root", "l33th4x0r");

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
                                String sql = "INSERT INTO users (email,passphrase)" + "VALUES (?, ?)";
                                PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                                preparedStatement.setString(1, username);
                                preparedStatement.setString(2, md5Password);
                                preparedStatement.executeUpdate(); 

                                //return false;
                            }

                        } catch (Exception ex) {
                            validationWarnings.add(ex.toString());
                        }

                    } catch (Exception ex) {
                        validationWarnings.add(ex.toString());
                    }

                    connection.close();

                } catch (Exception ex) {
                    validationWarnings.add("Unable to connect to database" + ex.toString());
                }
        } catch (Exception ex) {
            validationWarnings.add("No data received");
        }
        //Do we have any warnings?
        if (validationWarnings.size() > 0) {

            //Iterate over form validations and display
            for (String warning : validationWarnings) {
                out.println(warning + "<br/>");
            }

           // return false;
        }
        } finally {
            out.close();
        }
        
        return true;
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

}
