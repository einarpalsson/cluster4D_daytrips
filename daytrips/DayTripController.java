package daytrips;

class DayTripController {

  public ArrayList<DayTrip> getDayTrip() {
    String query = "SELECT * FROM DAYTRIP";
    ArrayList<DayTrip> daytrips = new ArrayList<DayTrip>();

    try {
      CachedRowSet res = Query.query(query);
      while (crs.next()) {
        daytrips.add(
          new DayTrip(
            crs.getString("Name"),
            /**
            BLABLABLA... */

          )
        );
      }
    } catch (SQLException err) {
      System.err.println(err);
    }
    return daytrips;
  }

  public DayTrip[] getDayTrip(String params) {
    if (params.trim().equals("")) {
      return getDayTrip();
    }
    /**
    Decode parameters... */
  }
}
