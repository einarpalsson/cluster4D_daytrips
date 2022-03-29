package daytrips;

import java.sql.Connection;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Init {
    /**
     * Initialize database according to schema.db
     * 
     * @throws IOException
     */
    public static void init() throws IOException {
        String SQL_PATH = "daytrips/schema.sql";
        String DB_PATH = "daytrips/daytrips.db";
        try {
            File db = new File(DB_PATH);
            if (db.createNewFile())
                System.out.println("Database created.");
            else {
                System.out.println("Database already exists.");
                System.exit(0);
            }
        } catch (Exception err) {
            System.err.println(err);
        }

        Class.forName("org.sqlite.JDBC");
        try {
            File sql = new File(SQL_PATH);
            if (!sql.exists()) {
                System.out.println("No schema found.");
                System.exit(0);
            }

            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            Statement statement = connection.createStatement();

            Scanner read = new Scanner(sql);
            read.useDelimiter(";");

            while (read.hasNext()) {
                String command = read.next();
                System.out.println(command);
                statement.execute(command);
            }
            read.close();

        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException err) {
                System.err.println(err);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        init();
    }
}