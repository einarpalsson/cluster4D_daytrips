package daytrips;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.sql.SQLException;

import javax.sql.ConnectionEvent;
import javax.sql.rowset.CachedRowSet;

class DayTripController {
  private final static String[] DayTripParams = {"dayTripId", "name", "price", "description", "location", "date", "timeStart", "timeEnd", "ageLimit", "difficulty", "capacity", "operatorId"};
  private final static String[] BookingParams = {"bookingId", "clientSSN", "clientEmail", "clientPhoneNumber", "clientCount", "date", "isPaid", "dayTripId"};
  private final static String[] OperatorParams = {"operatorId", "name", "phoneNo", "location", "localCode"};
  private final static String[] ReviewParams = {"rating", "date", "phoneNo", "clientSSN", "dayTripId"};

  public static boolean isDateArr(Object value) {
    return value.getClass().isArray();
  }

  public static String queryParser(
    Hashtable<String, Object> params,
    String method,
    String initalQuery,
    List<String> sqlParams
  ) {
    Set<String> keys = new HashSet<String>(params.keySet());

    int i = 0;
    switch(method) {
      case "GET":
        if (params.size() <= 0) {
          return initalQuery + ";";
        }
        
        initalQuery += " WHERE ";

        for (String key : keys) {
          Object value = params.get(key);
          if (sqlParams.contains(key)) {
            i++;
            if (key.contains("date") && isDateArr(value)) {
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
        String values = "";

        for (String key : sqlParams) {
          i++;
          initalQuery += key;
          values += "?"; 

          if (i < params.keySet().size()) {
            initalQuery += ", ";
            values += ", ";
          } else {
            initalQuery += ") VALUES(" + values + ");";
          }
        }

        return initalQuery;
      case "PATCH":
        System.out.println("PATCH yay");
        break;
    }
    return "ves";
  }

  public static String createDayTrip(Hashtable<String, Object> params) {
    String daytripUUID = UUID.randomUUID().toString();
    params.put("daytripId", daytripUUID);
    ArrayList<String> values = new ArrayList<>();

    String query = queryParser(params, "POST", "INSERT INTO DAYTRIP(", Arrays.asList(DayTripParams));
    System.out.println(query);

    for (String a: DayTripParams) {
      values.add(params.get(a).toString());
    }
      
    try {
      Query.insert(query, values);
    } catch (Exception e) {
      System.out.println(e);
    }

    return daytripUUID;
  }

  public static ArrayList<DayTrip> getDayTrips(Hashtable<String, Object> params) {
    String q = queryParser(params, "GET", "SELECT * FROM DAYTRIP", Arrays.asList(DayTripParams));
    System.out.println("QUERY ----> " + q);
    CachedRowSet res = Query.query(q);
    ArrayList<DayTrip> daytrips = new ArrayList<DayTrip>();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    try {
      while (res.next()) {
        daytrips.add(new DayTrip(
          res.getString("dayTripId"),
          res.getString("name"),
          res.getDouble("price"),
          res.getString("description"),
          res.getString("location"),
          res.getInt("localCode"),
          LocalDate.parse(res.getString("date")),
          LocalDateTime.parse(res.getString("timeStart"), format),
          LocalDateTime.parse(res.getString("timeEnd"), format),
          res.getInt("ageLimit"),
          res.getString("difficulty"),
          res.getInt("capacity"),
          res.getString("oId")
        ));
      }
    } catch (Exception e) {
      // Ignore
    }

    return daytrips;
  }

  /** Method makes a new booking object and inserts it into database.
   * 
   * @param dayTrip DayTrip object that is being booked.
   * @param clientSSN SSN of the client booking the trip.
   * @param isPaid  whether client has paid or not.
   * @return  booking id for lookup in database.
   */
  public static String bookDayTrip(Hashtable<String, Object> params) {
    String bookingUUID = UUID.randomUUID().toString();
    params.put("bookingId", bookingUUID);
    params.put("date", LocalDate.now().toString());
    ArrayList<String> values = new ArrayList<>();

    String query = queryParser(params, "POST", "INSERT INTO BOOKING(", Arrays.asList(BookingParams));
    System.out.println(query);

    for (String a: BookingParams) {
      values.add(params.get(a).toString());
    }
      
    try {
      Query.insert(query, values);
    } catch (Exception e) {
      System.out.println(e);
    }

    return bookingUUID;
  }

  public static ArrayList<Booking> getBookings(Hashtable<String, Object> params) {
    String q = queryParser(params, "GET", "SELECT * FROM BOOKING", Arrays.asList(BookingParams));
    System.out.println(q);
    CachedRowSet res = Query.query(q);
    ArrayList<Booking> bookings = new ArrayList<Booking>();

    try {
      while (res.next()) {
        Booking b = new Booking(
          res.getString("bookingId"),
          res.getString("clientSSN"),
          res.getString("clientEmail"),
          res.getString("clientPhoneNumber"),
          res.getInt("clientCount"),
          LocalDate.parse(res.getString("date")),
          Boolean.valueOf(res.getString("isPaid")),
          res.getString("dayTripId")
        );
        bookings.add(b);
      }
    } catch (Exception e) {
      // Ignore
    }

    return bookings;
  }

  public static ArrayList<Operator> getOperators(Hashtable<String, Object> params) {
    String q = queryParser(params, "GET", "SELECT * FROM OPERATOR", Arrays.asList(OperatorParams));
    System.out.println("QUERY ----> " + q);
    CachedRowSet res = Query.query(q);
    ArrayList<Operator> operators = new ArrayList<Operator>();

    try {
      while (res.next()) {
        operators.add(new Operator(
          res.getString("operatorId"),
          res.getString("name"),
          res.getString("location"),
          res.getString("phoneNo"),
          res.getInt("localCode")
        ));
      }
    } catch (Exception e) {
      // Ignore
    }

    return operators;
  }

  public static ArrayList<Review> getReviews(Hashtable<String, Object> params) {
    String q = queryParser(params, "GET", "SELECT * FROM REVIEW", Arrays.asList(ReviewParams));
    System.out.println("QUERY ----> " + q);
    CachedRowSet res = Query.query(q);
    ArrayList<Operator> reviews = new ArrayList<Operator>();

    try {
      while (res.next()) {
        reviews.add(new Review(
          res.getInt("rating"),
          res.getString("review"),
          LocalDate.parse(res.getString("date")),
          res.getString("clientSSN"),
          res.getString("dayTripId"),
        ));
      }
    } catch (Exception e) {
      // Ignore
    }

    return reviews;
  }

  public static void main(String[] args) {
    /* ---------------------------------------- */
    /* ----------- getDayTrips TEST ----------- */
    Hashtable<String, Object> getDayTripsParams = new Hashtable<>();
    LocalDate[] dates = {LocalDate.of(2022, 5, 1), LocalDate.of(2022, 6, 3)};
    // getDayTripsParams.put("Difficulty", 2);
    getDayTripsParams.put("date", dates);
    // getDayTripsParams.put("Date", d1);
    getDayTrips(getDayTripsParams);

    // /* ---------------------------------------- */
    // /* ----------- bookDayTrip TEST ----------- */
    Hashtable<String, Object> bookDayTripParams = new Hashtable<>();
    bookDayTripParams.put("clientSSN", "300321-2240");
    bookDayTripParams.put("clientEmail", "frosti@iceman.is");
    bookDayTripParams.put("clientPhoneNumber", "000-0000");
    bookDayTripParams.put("clientCount", 3);
    bookDayTripParams.put("isPaid", true);
    bookDayTripParams.put("dayTripId", "f2167055-02d8-4707-a355-80c3a69e051f");
    bookDayTrip(bookDayTripParams);

    /* ---------------------------------------- */
    /* ----------- getBookings TEST ----------- */
    ArrayList<Booking> bookings = getBookings(new Hashtable<>());
    for (Booking b : bookings) {
      String bookingId = b.getBookingId();
      String clientSSN = b.getClientSSN();
      String clientEmail = b.getClientEmail();
      String clientPhoneNumber = b.getClientPhoneNumber();
      int clientCount = b.getClientCount();
      String date = b.getDate().toString();
      String isPaid = String.valueOf(b.isPaid());
      String dayTripId = b.getDate().toString();
      System.out.println(
        bookingId + " ||| " + 
        clientSSN + " ||| " + 
        clientEmail + " ||| " + 
        clientPhoneNumber + " ||| " +
        clientCount + " ||| " +
        date + " ||| " +
        isPaid + " ||| " +
        dayTripId
      );
    }

    ArrayList<Operator> ops = getOperators(new Hashtable<>());
    System.out.println(ops.size());
  }
}
