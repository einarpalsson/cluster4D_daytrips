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

  public static String queryParser(Hashtable<String, Object> params) {
    String q = "SELECT * FROM Daytrips";

    if (params.size() <= 0) {
      return q + ";";
    }
    
    q += " WHERE ";
    Set<String> keys = params.keySet();

    while (keys.iterator().hasNext()) {
      q += keys.iterator().next() + " = " +  params.get(keys.iterator().next()) + " AND ";

      keys.remove(keys.iterator().next());
    }

    String query = "";

    if (keys.size() == 0) {
      query = q.substring(0, q.length()-5) + ";";
    }

    return query;
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
    System.out.println(queryParser(params));
    return null;
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
    params.put("difficulty", 3);
    params.put("capacity", 5);

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
