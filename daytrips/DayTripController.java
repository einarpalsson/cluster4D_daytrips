package daytrips;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Set;
import java.util.UUID;
import java.sql.SQLException;

import javax.sql.ConnectionEvent;
import javax.sql.rowset.CachedRowSet;

class DayTripController {
  private final static Set<String> DayTripParams = Set.of("Name", "Date", "TimeStart", "TimeEnd", "Difficulty", "Description", "AgeLimit", "Price", "Operator", "Location", "Capacity");

  static public ArrayList<DayTrip> getDayTrip() {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String query = "SELECT * FROM DAYTRIP";
    ArrayList<DayTrip> daytrips = new ArrayList<DayTrip>();

    try {
      CachedRowSet res = Query.query(query);
      while (res.next()) {
        daytrips.add(
                new DayTrip(
                        res.getString("Name"),
                        LocalDate.parse(res.getString("Date")),
                        LocalDateTime.parse(res.getString("TimeStart"), format),
                        LocalDateTime.parse(res.getString("TimeEnd"), format),
                        res.getInt("Difficulty"),
                        res.getString("Description"),
                        res.getInt("AgeLimit"),
                        res.getDouble("Price"),
                        res.getString("Operator"),
                        res.getString("Location"),
                        res.getInt("Capacity")
                )
        );
      }
    } catch (SQLException err) {
      System.err.println(err);
    }
    return daytrips;
  }

  public static boolean isDateArr(Object value) {
    return value.getClass().isArray();
  }

  public static String queryParser(Hashtable<String, Object> params, String method, String initalQuery) {
    switch(method) {
      case "GET":
        if (params.size() <= 0) {
          return initalQuery + ";";
        }
        
        initalQuery += " WHERE ";
        Set<String> keys = params.keySet();

        int i = 0;
        for (String key : keys) {
          Object value = params.get(key);
          if (DayTripParams.contains(key)) {
            i++;
            if (key.contains("Date") && isDateArr(value)) {
              LocalDate[] d = (LocalDate[]) value;
              initalQuery += key + " >= '" + d[0] + "' AND " + key + " <= '" + d[1] + "'";
              initalQuery += i < keys.size() ? " AND " : ";";
              continue;
            }

            initalQuery += key + " = '" + value + "'";

            initalQuery += i < keys.size() ? " AND " : ";";
          }
        }

        return initalQuery;
      case "POST":
        System.out.println("POST yay");
        break;
      case "PATCH":
        System.out.println("PATCH yay");
        break;
    }
    return "yay";
  }

  public static ArrayList<DayTrip> getDayTrips(Hashtable<String, Object> params) {

    // KALLA HER A SQL CONTROLLER MED KALLI A QueryParser
    // DÆMI: return runQuery(queryParser(params));

    // runQuery hér taeki vid streng sem keyrir select skipunina sem er returnad ur 
    // QueryParser fallinu. runQuery vaeri fall sem returnar alltaf ArrayList af 
    // thvi sem kemur ur gagnagrunninum. Her vaeri þa haegt að gera check sem ATH hvort
    // Arraylist se tomur og skila null i thvi tilfelli

    // if (params.trim().equals("")) {
    //   return getDayTrip();
    // }

    String q = queryParser(params, "GET", "SELECT * FROM DAYTRIP");
    System.out.println(q);
    CachedRowSet res = Query.query(q);
    ArrayList<DayTrip> daytrips = new ArrayList<DayTrip>();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    try {
      while (res.next()) {
        daytrips.add(new DayTrip(
          res.getString("Name"),
          LocalDate.parse(res.getString("Date")),
          LocalDateTime.parse(res.getString("TimeStart"), format),
          LocalDateTime.parse(res.getString("TimeEnd"), format),
          res.getInt("Difficulty"),
          res.getString("Description"),
          res.getInt("AgeLimit"),
          res.getDouble("Price"),
          res.getString("Operator"),
          res.getString("Location"),
          res.getInt("Capacity")
        ));
      }
    } catch (Exception e) {
      // Ignore
    }

    for (DayTrip dt: daytrips) {
      System.out.println(dt.getTripName() + " | " + dt.getDate().toString());
    }

    return daytrips;
    /**
     Decode parameters... */
  }

  /** Method makes a new booking object and inserts it into database.
   * 
   * @param dayTrip DayTrip object that is being booked.
   * @param clientSSN SSN of the client booking the trip.
   * @param isPaid  whether client has paid or not.
   * @return  booking id for lookup in database.
   */
  public static String bookDayTrip(
    DayTrip dayTrip, 
    String clientSSN, 
    String clientEmail,
    String clientPhoneNumber,
    int clientCount, 
    Boolean isPaid) {
      UUID bookingID = UUID.randomUUID();
      System.out.println(bookingID);


      Booking b = new Booking(
        bookingID, 
        clientSSN, 
        clientEmail, 
        clientPhoneNumber, 
        clientCount,
        dayTrip, 
        dayTrip.getDate(), 
        isPaid
        );
      
        String query = "insert into booking(ID, Client, DayTrip, Date, IsPaid, ClientEmail, ClientPhoneNumber, ClientCount) "
          + "values(?, ?, ?, ?, ?, ?, ?, ?);";

        ArrayList<String> values = new ArrayList<>();
        
        values.add(b.getBookingId().toString());
        values.add(b.getClient());
        values.add(b.getDayTrip().getTripName());
        values.add(b.getLocalDate().toString());
        values.add(String.valueOf(b.isPaid()));
        values.add(b.getClientEmail());
        values.add(b.getClientPhoneNumber());
        values.add(Integer.toString(b.getClientCount()));
        for(String s: values) System.out.println(s);

        try {
          Query.insert(query, values);
        } catch (Exception e) {
          System.out.println(e);
        }

          
      return b.getBookingId().toString();
    }

  public static void main(String[] args) {
    Hashtable<String, Object> params = new Hashtable<>();

    LocalDate d1 = LocalDate.of(2022, 5, 1);
    LocalDate d2 = LocalDate.of(2022, 6, 3);
    LocalDate[] dates = {d1, d2};

    // params.put("Difficulty", 2);
    params.put("Date", dates);
    // params.put("Date", d1);

    getDayTrips(params);

    LocalDate ld = LocalDate.now();
    LocalDateTime ldt1 = LocalDateTime.of(2022, 6, 12, 20, 00);
    LocalDateTime ldt2 = LocalDateTime.of(2022, 6, 12, 22, 00);
    DayTrip d = new DayTrip(
      "Frostahlaup",
       ld,
        ldt1,
         ldt2,
          2,
           "Bara gaman",
            12,
             3000,
              "Ævintýraferðir",
               "Vík í Mýrdal",
                5);
    
    bookDayTrip(d, "300321-2240", "frosti@iceman.is", "000-0000", 3, true);
  }
}
