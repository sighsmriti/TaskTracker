package com.example.servlets;

import java.io.*;
import java.sql.*;
import jakarta.servlet.http.*;

public class EditTaskServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tasktracker"; 
    private static final String DB_USER = "root";
    private static final String DB_PASS = "admin"; 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        int id = Integer.parseInt(request.getParameter("id"));
        String title = "";
        String description = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement stmt = conn.prepareStatement("SELECT title, description FROM tasks WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                title = rs.getString("title");
                description = rs.getString("description");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace(out);
        }

        out.println("<html><head><title>Edit Task</title><link rel=\"stylesheet\" type=\"text/css\" href=\"css/styles.css\"></head><body>");
        out.println("<div class=\"header\"><h1 class=\"header-title\">Task Tracker</h1><p class=\"header-desc\">A simple and elegant way to manage your tasks.</p></div>");
        out.println("<div class=\"container\">");
        out.println("<h1>Edit Task</h1>");
        out.println("<form action=\"edit\" method=\"post\">");
        out.println("<input type=\"hidden\" name=\"id\" value=\"" + id + "\">");
        out.println("<label for=\"title\">Title:</label>");
        out.println("<input type=\"text\" id=\"title\" name=\"title\" value=\"" + title + "\" required>");
        out.println("<label for=\"description\">Description:</label>");
        out.println("<textarea id=\"description\" name=\"description\" rows=\"4\" required>" + description + "</textarea>");
        out.println("<input type=\"submit\" value=\"Update Task\">");
        out.println("</form>");
        out.println("</div></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement stmt = conn.prepareStatement("UPDATE tasks SET title = ?, description = ? WHERE id = ?");
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("list"); 
    }
}