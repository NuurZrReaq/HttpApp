/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 *
 * @author Noor
 */
@WebServlet(urlPatterns = {"/NewServlet"})
public class NewServlet extends HttpServlet {

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
        int t;
        String acc; 
        String Pass,newPass,Operation,Amount,temp;String query;
        try {
            Class.forName("com.mysql.jdbc.Driver"); 
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/firstdb", "root", "134687952a");
            if(con == null){
                out.println("db not connected");
            }
            else {
                Statement stmnt = con.createStatement();
                ResultSet rs =null; 
                Enumeration<String> paramNames = request.getParameterNames();
                Operation = request.getParameter("Operation");
                temp = paramNames.nextElement();
               // out.println(temp);
                switch (Operation) {
                    case "Login":
                        //pass
                        acc = request.getParameter("accNum");
                        Pass = request.getParameter("pass");
                        //Pass = request.getParameter("Password");
                        query = "Select * from users where AccountNumber=" + acc + " and Password=" + Pass;
                        rs = stmnt.executeQuery(query);
                        if (rs.next()) {
                            out.println("Success");
                        } else {
                            out.println("Failed");
                        }
                        break;

                    case "ChangePass":
                        newPass = request.getParameter("newPass");
                        acc = request.getParameter("accNum");
                        query = "Update users set Password= " + newPass + " where AccountNumber= " + acc;
                        t = stmnt.executeUpdate(query);
                        if (t > 0) {
                            out.println("Password Changed");
                        } else {
                            out.println("Error Occured");
                        }

                        break;
                    case "withdraw":
                        Amount = request.getParameter("Amount");
                        acc = request.getParameter("accNum");
                        query = "Select Balance from users where AccountNumber=" + acc;
                        rs = stmnt.executeQuery(query);
                        if (rs.next()) {
                            int bal = Integer.parseInt(rs.getString("Balance"));
                            if (bal > Integer.parseInt(Amount)) {
                                bal -= Integer.parseInt(Amount);
                                query = "Update users set Balance=" + bal + " where AccountNumber=" + acc;
                                t = stmnt.executeUpdate(query);
                                if (t > 0) {
                                    out.println(Amount + " Subbed ");
                                } else {
                                    out.println("Shiiiiiit");
                                }
                            } else {//ifbal<amount
                                out.println("Amount << Balance");
                            }

                        }
                        break;

                    case "deposit":
                        acc = request.getParameter("accNum");
                        Amount = request.getParameter("Amount");
                        query = "Select Balance from users where AccountNumber=" + acc;
                        rs = stmnt.executeQuery(query);
                        if (rs.next()) {
                            int bal = Integer.parseInt(rs.getString("Balance"));
                            bal += Integer.parseInt(Amount);
                            query = "Update users set Balance=" + bal + " where AccountNumber=" + acc;
                            t = stmnt.executeUpdate(query);
                            if (t > 0) {
                                out.println(Amount + " Added ");
                            } else {
                                out.println("Shiiiiiit");
                            }
                        }
                        break;
                    case "getBalance":
                        acc = request.getParameter("accNum");
                        query = "Select Balance from users where AccountNumber=" + acc;
                        rs = stmnt.executeQuery(query);
                        if (rs.next()) {
                            out.println(rs.getString("Balance"));
                        } else {
                            out.println("Error Occured");
                        }
                        break;

                }
            }        
          
        }
        catch (Exception e) { 
            out.print(e.toString());
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
