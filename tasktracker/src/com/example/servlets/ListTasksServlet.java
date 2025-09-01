package com.example.servlets;

import java.io.*;
import java.sql.*;
import jakarta.servlet.http.*;

public class ListTasksServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tasktracker"; 
    private static final String DB_USER = "root";
    private static final String DB_PASS = "admin"; 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Task List</title>");
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/styles.css\">");
        out.println("</head>");
        out.println("<body>");
        // Header HTML
        out.println("<div class=\"header\">");
        out.println("<h1 class=\"header-title\">Task Tracker</h1>");
        out.println("<p class=\"header-desc\">A simple and elegant way to manage your tasks.</p>");
        out.println("</div>");

        out.println("<div class=\"container\">"); 
        out.println("<h1>Task List</h1>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tasks");

            // Correct Table HTML
            out.println("<table class=\"task-table\">");
            out.println("<tr><th>ID</th><th>Title</th><th>Description</th><th>Completed</th><th>Actions</th></tr>"); 
            
            // This is the new counter variable
            int displayId = 1;

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                boolean completed = rs.getBoolean("completed"); 
                String rowClass = completed ? "completed-task" : "";

                out.println("<tr class=\"" + rowClass + "\" id=\"task-" + id + "\">");
                out.println("<td>" + displayId + "</td>"); // Use the counter for display
                out.println("<td>" + title + "</td>");
                out.println("<td>" + description + "</td>");
                out.println("<td><input type=\"checkbox\" class=\"task-checkbox\" data-id=\"" + id + "\" " + (completed ? "checked" : "") + "></td>");
                out.println("<td class=\"actions\">");
                out.println("<a href=\"edit?id=" + id + "\" class=\"edit-icon\">&#x270E;</a>"); // Pen icon
                out.println("<a href=\"delete?id=" + id + "\" class=\"delete-icon\">&#x2715;</a>"); // X icon
                out.println("</td>");
                out.println("</tr>");
                
                // Increment the counter for the next row
                displayId++;
            }
            out.println("</table>");
            out.println("<p><a href=\"/tasktracker/\" class=\"button\">Add a New Task</a></p>");
            out.println("</div>"); 
            
            // Script Tag
            out.println("<script src=\"js/script.js\"></script>");

            out.println("</body>");
            out.println("</html>");

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}