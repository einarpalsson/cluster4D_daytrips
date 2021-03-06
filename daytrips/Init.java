package daytrips;

import java.sql.Connection;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Init {
    /**
     * Initialize database according to schema.db
     * 
     * @throws IOException
     */
    public static void init() throws IOException {
        String SQL_PATH = "daytrips" + File.separator + "schema.sql";;
        String DB_PATH = "daytrips" + File.separator + "daytrips.db";
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


        
        try {
            File sql = new File(SQL_PATH);
            if (!sql.exists()) {
                System.out.println("No schema found.");
                System.exit(0);
            }
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException err) {
                System.err.println(err);
            }
            String connectionUrl = "jdbc:sqlite:" + DB_PATH;
            Connection connection = DriverManager.getConnection(connectionUrl);
            Statement statement = connection.createStatement();

            Scanner read = new Scanner(sql, "utf-8");
            read.useDelimiter(";");

            while (read.hasNext()) {
                String command = read.next();
                System.out.println(command);
                statement.execute(command);
            }
            read.close();
            connection.close();
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        }
    }

    private static void insertDaytrips() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ArrayList<Operator> ops =  DayTripController.getOperators(new Hashtable<>());
        String[] names = {
            "J??klafer??",
            "Hestat??r",
            "Buggy fer??",
            "Kajak day",
            "Mountain hike",
            "Snorkling",
            "Golden circle",
            "Northern lights",
            "Sn??fellsnes round",
            "Waterfall excursion",
            "Whale watching",
            "Hot spring",
            "Volcano hike",
            "The Lava Tunnel",
            "Blue Lagoon",
            "Ice caving",
            "Paragliding",
            "Black beach",
            "Explore tour",
            "Something fun",
            "House tour",
            "Smoking tour"
        };

        String[] descriptions = {
            "J??klafer?? on top of amazing glaciers",
            "Riding some insane icelandic horses",
            "Driving the most fun buggy cars you have seen",
            "This is a sailing experience",
            "Mountain hiking through thick and thin",
            "Snorkling in the beautiful landscape",
            "Gullfoss and Geysir are extremely beautiful",
            "Aurora Borealis dreamy experience",
            "Sn??fellsnes round trip",
            "Exploring hidden waterfalls in Iceland",
            "See the most amazing whales in the world!",
            "Dip your toes into natural water",
            "Walk on lava, it's insane",
            "Cave exploring inside lava",
            "Very good skin-care routine",
            "Walk under frozen water, wow",
            "This goes very high",
            "A lot of sand in your shoes",
            "Explore a lot on this tour",
            "This is extremely fun",
            "Take a tour inside Bjarnis house, you will be surprised",
            "Smoke the most disgusting stuff"
        };

        String[] diff = {"Easy", "Medium", "Hard"};

        int j = 0;
        for (Operator o : ops) {
            int day = 0;
            int month = 0;

            int price = (int)Math.floor(Math.random()*(100000-1000+1)+1000);
            int ageLimit = (int)Math.floor(Math.random()*(18-12+1)+12);
            int difficulty = (int)Math.floor(Math.random()*3);
            int capacity = (int)Math.floor(Math.random()*(75-20+1)+20);

            for (int i = 0; i < 91; i++) {
                if (day >= 29) {
                    month++;
                    day = 1;
                }
                String q = "INSERT INTO DAYTRIP VALUES(" + 
                "'" + UUID.randomUUID().toString() + "', " +
                "'" + names[j] + "', " +
                price + ", " +
                "'" + descriptions[j] + "', " +
                "'" + o.getLocation() + "', " +
                o.getLocalCode() + ", " +
                "'" + LocalDate.of(2022, 4+month, 1+day).toString() + "', " +
                "'" + LocalDateTime.of(2022, 4+month, 1+day, 13, 0).toString() + "', " +
                "'" + LocalDateTime.of(2022, 4+month, 1+day, 18, 0) + "', " +
                ageLimit + ", " +
                "'" + diff[difficulty] + "', " +
                capacity + ", " +
                "'" + o.getOperatorId() + "');";

                try {
                    Query.insert(q);
                } catch (Exception e) {
                    //TODO: handle exception
                }
                day++;
            }
            j++;
        }
    }

    public static void main(String[] args) throws IOException {
        init();
        insertDaytrips();
    }
}
