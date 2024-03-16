## To set up and run the web application, follow these steps:

### 1.Setting Up the Environment:
Install MySQL server.
Set up a MySQL database and create a table to store user data.
Install apache-tomcat-8.5.99.

### 2.Project Structure:
Create a Dynamic web project, including files HTML, Java class and necessary files.

### 3.Database Configuration:
Configure your Java application to connect to the MySQL database. This typically involves setting up a JDBC connection.
Provide the necessary database credentials (username, password, database URL) in your Java Servlet code or in a separate configuration file.

### 4.Creating the User Input Form:
Develop an HTML form with fields for Name, Email, Age, and Date of Birth.

### 5.Java Servlet for Data Storage:
Create a Java Servlet to handle form submissions.
If the data is valid, store it in the MySQL database using JDBC.

### 6.Deploying the Application:
Compile your Java Servlets.
Start your Servlet container.
Access your application through a web browser using the appropriate URL.

### 7.Testing:
Test the application.


## HTML, CSS and JAVASCRIPT code 


### index.html => login-page code

    <title>User Form</title>
   ### CSS code: 
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 500px;
            margin: 100px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }
        

        input[type="text"],
        input[type="email"],
        input[type="number"],
        input[type="date"] {
            width: calc(100% - 12px);
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        button[type="submit"] {
            background-color: #4caf50;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }
        #displayButton{
        background-color: #45a049;
         color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }
        

        button[type="submit"]:hover {
            background-color: #45a049;
        }
         button[type="displayButton"]:hover {
            background-color: #45a049;
        }

        .error-message {
            color: red;
            margin-top: 5px;
        }
    </style>

### HTML code: 
</head>
<body>
    <div class="container">
        <form id="userForm" action="submit" method="post">
            
           <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" required>
            </div>
            
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            
            <div class="form-group">
                <label for="age">Age:</label>
                <input type="number" id="age" name="age" required>
                <span class="error-message" id="ageError"></span>
            </div>
            
            <div class="form-group">
                <label for="dob">Date of Birth:</label>
                <input type="date" id="dob" name="dob" required>
            </div>
            
            <button type="submit">Submit</button>
      <button id="displayButton">Display User Data</button>
        </form>
        
    </div>

### JavaScript => Validation Code:
    <script>
        document.getElementById("userForm").addEventListener("submit", function(event) {
            const age = document.getElementById("age").value;
            if (isNaN(age) || age < 0) {
                document.getElementById("ageError").textContent = "Age must be a positive integer.";
                event.preventDefault();
            } else {
                document.getElementById("ageError").textContent = "";
            }
        });

        document.getElementById("displayButton").addEventListener("click", function() {
            window.location.href = "display";
        });
    </script>
</body>
</html>


### display.html

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Data</title>

  <style>
        body {
            display: flex;
            align-items: start;
            justify-content: center;
            height: 97.5vh;
        }

        .table-info h2 {
            color: blueviolet;
            text-align: center;
        }

        .table-info table, tr, td, th {
            border: 1px solid blueviolet;
            border-collapse: collapse;
            padding: 20px;
        }
        
    </style>

</head>
<body>
    <h2>Users</h2><br>
    <table border="1">
        <thead>
            <tr>
                <th > ID </th>
                <th > Name </th>
                <th >Email</th>
                <th >Age</th>
                <th >Date</th>
            </tr>
        </thead>
        <tbody>
            <tr th:each="user : ${userDetails}">
                <td th:text="${user.id}"></td>
                <td th:text="${user.name}"></td>
                <td th:text="${user.email}"></td>
                <td th:text="${user.age}"></td>
                <td th:text="${user.dob}"></td>
            </tr>
        </tbody>
    </table>
</body>
</html>

# Java(JDBC & JEE) code:
## This code is for connecting database to server.

### RegisterSeevlet.java
package com.sevlet.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/submit")
public class RegisterServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("/index.html").forward(req, res);
        res.sendRedirect("display");

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        int age = Integer.parseInt(req.getParameter("age"));
        String dob = req.getParameter("dob");

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/userregister", "root", "Naziya@123");
            String query = "INSERT INTO USER_DATA (name, email, age, dob) VALUES (?, ?, ?, ?)";
            pstmt = con.prepareStatement(query);

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setInt(3, age);
            pstmt.setString(4, dob);

            int count = pstmt.executeUpdate();
            if (count > 0) {
                out.println("<h3>Record Stored into Database</h3>");
            } else {
                out.println("<h3>Failed to store record into Database</h3>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        out.close();
    }
}

### DisplayDataServlet.java

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


