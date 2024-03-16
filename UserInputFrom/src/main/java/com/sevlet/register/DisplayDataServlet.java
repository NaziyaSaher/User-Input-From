package com.sevlet.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/display")
public class DisplayDataServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>User Data</title>");
        out.println("</head>");
        out.println("<body>");

        out.println("<h2>User Data</h2>");
        out.println("<table border=\"1\">");
        out.println("<tr>");
        out.println("<th>Name</th>");
        out.println("<th>Email</th>");
        out.println("<th>Age</th>");
        out.println("<th>Date of Birth</th>");
        out.println("</tr>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/userregister", "root", "Naziya@123");
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM USER_DATA");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                int age = rs.getInt("age");
                String dob = rs.getString("dob");

                out.println("<tr>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + email + "</td>");
                out.println("<td>" + age + "</td>");
                out.println("<td>" + dob + "</td>");
                out.println("</tr>");
            }

            pstmt.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<tr><td colspan=\"4\">Error: " + e.getMessage() + "</td></tr>");
        }

        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
