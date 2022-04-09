package daytrips;
import java.util.ArrayList;
import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;

class DayTripController {

  public ArrayList<DayTrip> getDayTrip() {
    String query = "SELECT * FROM DAYTRIP";
    ArrayList<DayTrip> daytrips = new ArrayList<DayTrip>();

    try {
      CachedRowSet res = Query.query(query);
      while (res.next()) {
        /*daytrips.add(
          new DayTrip(
            res.getString("Name")
          )
        );
        */
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
