package com.example.servlets;

import java.io.*;
import java.sql.*;
import jakarta.servlet.http.*;

public class AddTaskServlet extends HttpServlet {
    // In both AddTaskServlet.java and ListTasksServlet.java
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tasktracker"; // your db name
    private static final String DB_USER = "root";
    private static final String DB_PASS = "admin"; 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            String sql = "INSERT INTO tasks (title, description) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.executeUpdate();

            stmt.close();
            conn.close();

            response.sendRedirect("list"); // redirect to task list
        } catch (Exception e) {
            e.printStackTrace(response.getWriter());
        }
    }
}
