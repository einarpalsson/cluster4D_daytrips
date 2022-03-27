import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class DTController {

  private static DayTripMockUp MockTest = new DayTripMockUp();

  public ArrayList<DT> getDaytrips(Hashtable<String, String> params) {
    Set<String> keys = params.keySet();

    String q = "SELECT * FROM DAYTRIPS WHERE ";

    int count = 0;

    for (String key : keys) {
      if (!params.get(key).isEmpty()) {
        count++;
        q += key + " = " + params.get(key) + " AND ";
      }
    }

    String query = "";
    if (count > 0) {
      query = q.substring(0, q.length() - 5);
    } else {
      query = q.substring(0, q.length() - 7);
    }
    return MockTest.getDaytrips(query);
  }
}
