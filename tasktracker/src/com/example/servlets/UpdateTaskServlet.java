package com.example.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UpdateTaskServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tasktracker"; 
    private static final String DB_USER = "root";
    private static final String DB_PASS = "admin"; 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean completed = Boolean.parseBoolean(request.getParameter("completed"));
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement stmt = conn.prepareStatement("UPDATE tasks SET completed = ? WHERE id = ?");
            stmt.setBoolean(1, completed);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            conn.close();
            response.setStatus(HttpServletResponse.SC_OK); 
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}