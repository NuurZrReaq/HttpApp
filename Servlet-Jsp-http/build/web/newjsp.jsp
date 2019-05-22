
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import ="java.sql.*"%>
<%@page import ="java.io.*"%>
<%@page import ="java.io.PrintWriter"%>
<%@page import ="java.util.*"%>
       <%//PrintWriter out = response.getWriter(); 
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
                        query = "Select * from users where AccountNumber='" + acc + "' and Password='" + Pass+"'";
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
                        query = "Select Balance from users where AccountNumber='" + acc+"'";
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
        }%>
