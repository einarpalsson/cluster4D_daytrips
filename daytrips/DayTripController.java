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
  private final static String[] DayTripParams = {"dayTripId", "name", "price", "description", "location", "localCode", "date", "timeStart", "timeEnd", "ageLimit", "difficulty", "capacity", "oId"};
  private final static String[] BookingParams = {"bookingId", "clientSSN", "clientEmail", "clientPhoneNumber", "clientCount", "date", "isPaid", "dtId"};
  private final static String[] OperatorParams = {"operatorId", "name", "phoneNo", "location", "localCode"};
  private final static String[] ReviewParams = {"rating", "review", "date", "clientSSN", "dtId"};

  /**
   * Checks if the given object is an array
   * @param value Boolean
   * @return
   */
  public static boolean isArr(Object value) {
    return value.getClass().isArray();
  }

  /**
   * Function that takes in parameters from the "frontend" and 
   * parses the parameters into a SQL query.
   * @param params Hashtable<String, Object>
   * @param method String (GET, POST)
   * @param initalQuery String
   * @param sqlParams String[] in right order
   * @return String SQL query
   */
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
            if (key.contains("date") && isArr(value)) {
              LocalDate[] d = (LocalDate[]) value;
              initalQuery += "(" + key + " >= '" + d[0] + "' AND " + key + " <= '" + d[1] + "')";
              initalQuery += i < keys.size() ? " AND " : ";";
              continue;
            }

            if (key.contains("difficulty")) {
              String [] diff = (String[]) value;
              int count = 0;
              initalQuery += "(";
              for (String v : diff) {
                initalQuery += key + " = '" + v + "'";
                initalQuery += count < diff.length-1 ? " OR " : "";
                count++;
              }
              initalQuery += ")";
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
    }
    return "ves";
  }

  /**
   * Creates a new daytrip from parameters. Must contain 
   * all parameters needed for the object that's being created
   * 
   * See main() function for examples of usage.
   * @param params
   * @return the DayTripId thats generated in the function
   */
  public static String createDayTrip(Hashtable<String, Object> params) {
    String daytripUUID = UUID.randomUUID().toString();
    params.put("dayTripId", daytripUUID);
    ArrayList<String> values = new ArrayList<>();

    String query = queryParser(params, "POST", "INSERT INTO DAYTRIP(", Arrays.asList(DayTripParams));

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

  /**
   * Function that fetches DayTrips from the database based
   * on given parameters and returns them as an ArrayList
   * of DayTrip objects.
   * 
   * Can have zero parameters, than it returns all DayTrips.
   * 
   * See main() function for examples of usage.
   * @param params Hashtable<String, Object>
   * @return Arraylist of DayTrip Objects.
   */
  public static ArrayList<DayTrip> getDayTrips(Hashtable<String, Object> params) {
    String q = queryParser(params, "GET", "SELECT * FROM DAYTRIP", Arrays.asList(DayTripParams));
    CachedRowSet res = Query.query(q);
    ArrayList<DayTrip> daytrips = new ArrayList<DayTrip>();

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
          LocalDateTime.parse(res.getString("timeStart")),
          LocalDateTime.parse(res.getString("timeEnd")),
          res.getInt("ageLimit"),
          res.getString("difficulty"),
          res.getInt("capacity"),
          res.getString("oId")
        ));
      }
    } catch (Exception e) {
      System.out.println(e);
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

    for (String a: BookingParams) {
      values.add(params.get(a).toString());
    }
      
    try {
      Query.insert(query, values);
      Query.insert("UPDATE DAYTRIP SET capacity = capacity - " 
        + params.get("clientCount").toString() + " WHERE dayTripId = '" 
        + params.get("dtId").toString() + "';");
    } catch (Exception e) {
      System.out.println(e);
    }
    EmailSender.sendConfirmationEmail(params);
    return bookingUUID;
  }

  /**
   * Function that fetches Bookings from the database based
   * on given parameters and returns them as an ArrayList
   * of Booking objects.
   * 
   * Can have zero parameters, than it returns all Bookings.
   * 
   * See main() function for examples of usage.
   * @param params Hashtable<String, Object>
   * @return Arraylist of Booking Objects.
   */
  public static ArrayList<Booking> getBookings(Hashtable<String, Object> params) {
    String q = queryParser(params, "GET", "SELECT * FROM BOOKING", Arrays.asList(BookingParams));
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
          res.getString("dtId")
        );
        bookings.add(b);
      }
    } catch (Exception e) {
      System.out.println(e);
    }

    return bookings;
  }

  /**
   * Function that fetches Operators from the database based
   * on given parameters and returns them as an ArrayList
   * of Operator objects.
   * 
   * Can have zero parameters, than it returns all Operators.
   * 
   * See main() function for examples of usage.
   * @param params Hashtable<String, Object>
   * @return Arraylist of Operator Objects.
   */
  public static ArrayList<Operator> getOperators(Hashtable<String, Object> params) {
    String q = queryParser(params, "GET", "SELECT * FROM OPERATOR", Arrays.asList(OperatorParams));
    CachedRowSet res = Query.query(q);
    ArrayList<Operator> operators = new ArrayList<Operator>();

    try {
      while (res.next()) {
        operators.add(new Operator(
          res.getString("operatorId"),
          res.getString("name"),
          res.getString("phoneNo"),
          res.getString("location"),
          res.getInt("localCode")
        ));
      }
    } catch (Exception e) {
      System.out.println(e);
    }

    return operators;
  }

  /**
   * Function that fetches Reviews from the database based
   * on given parameters and returns them as an ArrayList
   * of Review objects.
   * 
   * Can have zero parameters, than it returns all Reviews.
   * 
   * See main() function for examples of usage.
   * @param params Hashtable<String, Object>
   * @return Arraylist of Review Objects.
   */
  public static ArrayList<Review> getReviews(Hashtable<String, Object> params) {
    String q = queryParser(params, "GET", "SELECT * FROM REVIEW", Arrays.asList(ReviewParams));
    CachedRowSet res = Query.query(q);
    ArrayList<Review> reviews = new ArrayList<Review>();

    try {
      while (res.next()) {
        reviews.add(new Review(
          res.getInt("rating"),
          res.getString("review"),
          LocalDate.parse(res.getString("date")),
          res.getString("clientSSN"),
          res.getString("dtId")
        ));
      }
    } catch (Exception e) {
      System.out.println(e);
    }

    return reviews;
  }

  /**
   * Creates a new review from parameters. Must contain 
   * all parameters needed for the object that's being created
   * Must contain all the information, most importantly 
   * a clientSSN that already exists in the database and a daytripID
   * 
   * See main() function for examples of usage.
   * @param params
   */
  public static void insertReview(Hashtable<String, Object> params) {
    params.put("date", LocalDate.now().toString());
    ArrayList<String> values = new ArrayList<>();
    String query = queryParser(params, "POST", "INSERT INTO REVIEW(", Arrays.asList(ReviewParams));

    for (String a: ReviewParams) {
      values.add(params.get(a).toString());
    }
      
    try {
      Query.insert(query, values);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public static void main(String[] args) {
    System.out.println("Framundan eru ýmiss konar test sem sýnir hvernig DayTrip bakendinn virkar");
    System.out.println("Það má breyta út frá parameterum ef þú vilt prófa aðra parametera o.s.frv");
    System.out.println("Alla parametera fyrir klasana má finna efst í þessu skjali");
    System.out.println("\n------------------------------------------------------------------\n");

    /* ---------------------------------------- */
    /* --------- createDayTrip TEST ----------- */
    System.out.println("Búum til DayTrip með parameterana:");
    System.out.println("name: " + "Sviðasultusmakk");
    System.out.println("price: " + 6500);
    System.out.println("description: " + "Förum á milli bæja,skoðum dýrin og smökkum sviðasultu.");
    System.out.println("location: " + "Neskaupsstaður");
    System.out.println("localCode: " + 5);
    System.out.println("date: " + "\"2022-05-10\"");
    System.out.println("timeStart: " + "\"2022-05-10-13-00\"");
    System.out.println("timeEnd: " + "\"2022-05-10-18-00\"");
    System.out.println("ageLimit: " + 15);
    System.out.println("difficulty: " + "Medium");
    System.out.println("capacity: " + 15);
    System.out.println("oId: " + "2a93cc1f-0b98-4110-95d2-b815667c8431");
    Hashtable<String, Object> dtParams = new Hashtable<>();
    dtParams.put("name", "Sviðasultusmakk");
    dtParams.put("price", 6500);
    dtParams.put("description", "Förum á milli bæja, skoðum dýrin og smökkum sviðasultu.");
    dtParams.put("location", "Neskaupsstaður");
    dtParams.put("localCode", 5);
    dtParams.put("date", LocalDate.of(2022, 5, 10));
    dtParams.put("timeStart", LocalDateTime.of(2022, 5, 10, 13, 00));
    dtParams.put("timeEnd", LocalDateTime.of(2022, 5, 10, 18, 00));
    dtParams.put("ageLimit", 15);
    dtParams.put("difficulty", "Medium");
    dtParams.put("capacity", 15);
    dtParams.put("oId", "2a93cc1f-0b98-4110-95d2-b815667c8431");
    String testDayTripId = createDayTrip(dtParams);

    // Sækjum Daytrip sem við bjuggum til
    Hashtable<String, Object> ourDtParams = new Hashtable<>();
    ourDtParams.put("dayTripId", testDayTripId);
    ArrayList<DayTrip> ourDt = getDayTrips(ourDtParams);
    System.out.println("Fáum dayTripId = " + testDayTripId);
    System.out.println("Köllum á getDayTrips með því sem parameter og fáum það daytrip:");
    for (DayTrip d : ourDt) {
      System.out.println("Nafn: " + d.getName() + " ||| Verð: " + d.getPrice() + " ||| Staðsetning: " + d.getLocation());
    }
    System.out.println("\n------------------------------------------------------------------\n");

    /* ---------------------------------------- */
    /* ----------- getDayTrips TEST ----------- */
    // Sækjum DayTrip með einhverjum parameterum
    Hashtable<String, Object> getDayTripsParams = new Hashtable<>();
    LocalDate[] dates = {LocalDate.of(2022, 5, 1), LocalDate.of(2022, 5, 2)};
    String[] arr = {"Medium", "Easy"};
    getDayTripsParams.put("localCode", 2);
    getDayTripsParams.put("date", dates);
    getDayTripsParams.put("difficulty", arr);
    ArrayList<DayTrip> dts = getDayTrips(getDayTripsParams);
    System.out.println("Hér er test þar sem sótt eru öll Daytrips með parameterana:");
    System.out.println("difficulty: {\"Medium\", \"Easy\"}");
    System.out.println("date: {\"2022-05-01\", \"2022-05-02\"}");
    System.out.println("localCode = 2");
    System.out.println("Fáum:");
    for (DayTrip d : dts) {
      System.out.println("Nafn: " + d.getName() + " ||| Verð: " + d.getPrice() + " ||| Staðsetning: " + d.getLocation());
    }
    System.out.println("\n------------------------------------------------------------------\n");

    // /* ---------------------------------------- */
    // /* ----------- bookDayTrip TEST ----------- */
    // Bókum daytrip, nóg að hafa þessa parametera, bookDayTrip() sér um rest.
    Hashtable<String, Object> bookDayTripParams = new Hashtable<>();
    bookDayTripParams.put("clientSSN", "300321-2240");
    bookDayTripParams.put("clientEmail", "oberen@inspauvila.cat");
    bookDayTripParams.put("clientPhoneNumber", "000-0000");
    bookDayTripParams.put("clientCount", 3);
    bookDayTripParams.put("isPaid", true);
    bookDayTripParams.put("dtId", testDayTripId);
    String testBookDt = bookDayTrip(bookDayTripParams);
    System.out.println("Fáum bookingId = " + testBookDt);

    // Sækjum bókunina út frá því ID sem við fengum
    // Einnig hægt að sækja út frá kennitölu viðkomandi.
    Hashtable<String, Object> bookdttest = new Hashtable<>();
    bookdttest.put("bookingId", testBookDt);
    ArrayList<Booking> bkngs =  getBookings(bookdttest);
    for (Booking b : bkngs) {
      System.out.println("Email: " + b.getClientEmail() + " ||| Kennitala: " + b.getClientSSN() + " ||| Búið að borga: " + b.isPaid());
    }
    System.out.println("\n------------------------------------------------------------------\n");

    /* ---------------------------------------- */
    /* ----------- getBookings TEST ----------- */
    System.out.println("Hér er test þar sem sótt eru öll Bookings án parametera:");
    ArrayList<Booking> bookings = getBookings(new Hashtable<>());
    for (Booking b : bookings) {
      System.out.println(
        b.getClientSSN() + " ||| " + 
        b.getClientEmail() + " ||| " + 
        b.getClientPhoneNumber() + " ||| " +
        b.getClientCount() + " ||| " +
        b.getDate().toString() + " ||| " +
        b.isPaid() + " ||| " +
        b.getDayTripId()
      );
    }
    System.out.println("\n------------------------------------------------------------------\n");

    /* ---------------------------------------- */
    /* ------------- Review TEST -------------- */
    // Búum til Review á daytrip sem við bókuðum hér að ofan
    Hashtable<String, Object> testReview = new Hashtable<>();
    testReview.put("rating", 5);
    testReview.put("review", "Þetta var besta Sviðasulta sem ég hef á ævinni prófað.  Samt smá vont í hálsinn. Fimm stjörnur!");
    testReview.put("date", LocalDate.now());
    testReview.put("clientSSN", "300321-2240");
    testReview.put("dtId", testDayTripId);
    insertReview(testReview);
    System.out.println("Hér er engu skilað þegar review er búið til");
    System.out.println("Því sækjum við review út frá kennitölu notanda");
    System.out.println("Einnig hægt að sækja út frá dayTripId");
    Hashtable<String, Object> revParams = new Hashtable<>();
    revParams.put("clientSSN", "300321-2240");
    ArrayList<Review> reviews = getReviews(revParams);
    for (Review r : reviews) {
      System.out.println(
        r.getRating() + " ||| " + 
        r.getReview() + " ||| " + 
        r.getDate().toString() + " ||| " + 
        r.getClientSSN() + " ||| " +
        r.getDayTripId()
      );
    }
    System.out.println("\n------------------------------------------------------------------\n");
  }
}
