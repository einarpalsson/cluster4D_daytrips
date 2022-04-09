package daytrips;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

  public ArrayList<DayTrip>  getDayTrip(String params) {
    if (params.trim().equals("")) {
      return getDayTrip();
    }
    return null;
    /**
     Decode parameters... */
  }
}
