package databasePart1;

import java.sql.*;

public class DatabaseView {
    public static void main(String[] args) {
        String url = "jdbc:h2:~/FoundationDatabase";
        String user = "sa";
        String password = ""; // Leave blank

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            // Show all tables
            ResultSet rsTables = stmt.executeQuery("SHOW TABLES");
            System.out.println("Tables in the database:");
            while (rsTables.next()) {
                System.out.println("- " + rsTables.getString(1));
            }

            // Retrieve all users from cse360users table
            String query = "SELECT userName, role FROM cse360users";
            ResultSet rsUsers = stmt.executeQuery(query);

            System.out.println("\nUsers in the database:");
            while (rsUsers.next()) {
                String username = rsUsers.getString("userName");
                String role = rsUsers.getString("role");
                System.out.println("User: " + username + " | Role: " + role);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
