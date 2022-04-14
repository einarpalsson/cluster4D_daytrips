// package daytrips;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;
import java.sql.SQLException;
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

    // KALLA HÉR Á SQL CONTROLLER MEÐ KALLI Á QueryParser
    // DÆMI: return runQuery(queryParser(params));

    // runQuery hér tæki við streng sem keyrir select skipunina sem er returnað úr 
    // QueryParser fallinu. runQuery væri fall sem returnar alltaf ArrayList af 
    // Því sem kemur úr gagnagrunninum. Hér væri þá hægt að gera check sem ATH hvort
    // Arraylist sé tómur og skila null í því tilfelli

    // if (params.trim().equals("")) {
    //   return getDayTrip();
    // }
    System.out.println(queryParser(params));
    return null;
    /**
     Decode parameters... */
  }

  public static void main(String[] args) {
    Hashtable<String, Object> params = new Hashtable<>();
    params.put("difficulty", true);
    params.put("capacity", 5);

    getDayTrips(params);
  }
}
