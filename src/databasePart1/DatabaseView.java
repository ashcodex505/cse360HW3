package databasePart1;
import java.sql.*;

public class DatabaseView {
    public static void main(String[] args) {
        String url = "jdbc:h2:~/FoundationDatabase";
        String user = "sa";
        String password = ""; // Leave blank

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            // 1. Show all tables in the database
            ResultSet rsTables = stmt.executeQuery("SHOW TABLES");
            System.out.println("Tables in the database:");
            while (rsTables.next()) {
                String tableName = rsTables.getString(1);
                System.out.println("- " + tableName);
            }

            // 2. Specify the tables you want to display data from.
            //    Update these names if they differ in your database.
            String[] tables = {"cse360users", "InvitationCodes", "PasswordResetForm"};

            // 3. Loop through each table and print out its data.
            for (String tableName : tables) {
                System.out.println("\nData from table: " + tableName);
                
                // Execute a SELECT * query to get all rows and columns.
                ResultSet rsData = stmt.executeQuery("SELECT * FROM " + tableName);
                ResultSetMetaData rsmd = rsData.getMetaData();
                int columnCount = rsmd.getColumnCount();

                // Print the column headers
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rsmd.getColumnName(i) + "\t");
                }
                System.out.println();
                
                // Print each row's data
                while (rsData.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(rsData.getString(i) + "\t");
                    }
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
