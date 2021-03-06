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
    System.out.println("Framundan eru ??miss konar test sem s??nir hvernig DayTrip bakendinn virkar");
    System.out.println("??a?? m?? breyta ??t fr?? parameterum ef ???? vilt pr??fa a??ra parametera o.s.frv");
    System.out.println("Alla parametera fyrir klasana m?? finna efst ?? ??essu skjali");
    System.out.println("\n------------------------------------------------------------------\n");

    /* ---------------------------------------- */
    /* --------- createDayTrip TEST ----------- */
    System.out.println("B??um til DayTrip me?? parameterana:");
    System.out.println();
    System.out.println("name: " + "Svi??asultusmakk");
    System.out.println("price: " + 6500);
    System.out.println("description: " + "F??rum ?? milli b??ja,sko??um d??rin og sm??kkum svi??asultu.");
    System.out.println("location: " + "Neskaupssta??ur");
    System.out.println("localCode: " + 5);
    System.out.println("date: " + "\"2022-05-10\"");
    System.out.println("timeStart: " + "\"2022-05-10-13-00\"");
    System.out.println("timeEnd: " + "\"2022-05-10-18-00\"");
    System.out.println("ageLimit: " + 15);
    System.out.println("difficulty: " + "Medium");
    System.out.println("capacity: " + 15);
    System.out.println("oId: " + "2a93cc1f-0b98-4110-95d2-b815667c8431");
    System.out.println();
    
    Hashtable<String, Object> dtParams = new Hashtable<>();
    dtParams.put("name", "Svi??asultusmakk");
    dtParams.put("price", 6500);
    dtParams.put("description", "F??rum ?? milli b??ja, sko??um d??rin og sm??kkum svi??asultu.");
    dtParams.put("location", "Neskaupssta??ur");
    dtParams.put("localCode", 5);
    dtParams.put("date", LocalDate.of(2022, 5, 10));
    dtParams.put("timeStart", LocalDateTime.of(2022, 5, 10, 13, 00));
    dtParams.put("timeEnd", LocalDateTime.of(2022, 5, 10, 18, 00));
    dtParams.put("ageLimit", 15);
    dtParams.put("difficulty", "Medium");
    dtParams.put("capacity", 15);
    dtParams.put("oId", "2a93cc1f-0b98-4110-95d2-b815667c8431");
    String testDayTripId = createDayTrip(dtParams);

    // S??kjum Daytrip sem vi?? bjuggum til
    Hashtable<String, Object> ourDtParams = new Hashtable<>();
    ourDtParams.put("dayTripId", testDayTripId);
    ArrayList<DayTrip> ourDt = getDayTrips(ourDtParams);
    System.out.println("F??um dayTripId = " + testDayTripId);
    System.out.println("K??llum ?? getDayTrips me?? ??v?? sem parameter og f??um ??a?? daytrip:");
    for (DayTrip d : ourDt) {
      System.out.println("Nafn: " + d.getName() + " ||| Ver??: " + d.getPrice() + " ||| Sta??setning: " + d.getLocation());
    }
    System.out.println("\n------------------------------------------------------------------\n");

    /* ---------------------------------------- */
    /* ----------- getDayTrips TEST ----------- */
    // S??kjum DayTrip me?? einhverjum parameterum
    Hashtable<String, Object> getDayTripsParams = new Hashtable<>();
    LocalDate[] dates = {LocalDate.of(2022, 5, 1), LocalDate.of(2022, 5, 2)};
    String[] arr = {"Medium", "Easy"};
    getDayTripsParams.put("localCode", 2);
    getDayTripsParams.put("date", dates);
    getDayTripsParams.put("difficulty", arr);
    ArrayList<DayTrip> dts = getDayTrips(getDayTripsParams);
    System.out.println("H??r er test ??ar sem s??tt eru ??ll Daytrips me?? parameterana:");
    System.out.println();
    System.out.println("difficulty: {\"Medium\", \"Easy\"}");
    System.out.println("date: {\"2022-05-01\", \"2022-05-02\"}");
    System.out.println("localCode = 2");
    System.out.println();

    System.out.println("F??um:");
    for (DayTrip d : dts) {
      System.out.println("Nafn: " + d.getName() + " ||| Ver??: " + d.getPrice() + " ||| Sta??setning: " + d.getLocation());
    }
    System.out.println("\n------------------------------------------------------------------\n");

    // /* ---------------------------------------- */
    // /* ----------- bookDayTrip TEST ----------- */
    // B??kum daytrip, n??g a?? hafa ??essa parametera, bookDayTrip() s??r um rest.
    System.out.println("H??r er b??kun b??in til me?? parameterum:");
    System.out.println();
    System.out.println("clientSSN: " + "300321-2240");
    System.out.println("clientEmail: " + "EHVEMAIL@GMAIL.COM + | <----- H??r er h??gt a?? setja alv??ru email til a?? pr??fa EmailSender function");
    System.out.println("clientPhoneNumber: " + "000-0000");
    System.out.println("clientCount: " + 3);
    System.out.println("isPaid: " + true);
    System.out.println("dtId: " + testDayTripId);
    System.out.println();
    
    Hashtable<String, Object> bookDayTripParams = new Hashtable<>();
    bookDayTripParams.put("clientSSN", "300321-2240");
    bookDayTripParams.put("clientEmail", "EHVEMAIL@GMAIL.COM");
    bookDayTripParams.put("clientPhoneNumber", "000-0000");
    bookDayTripParams.put("clientCount", 3);
    bookDayTripParams.put("isPaid", true);
    bookDayTripParams.put("dtId", testDayTripId);
    String testBookDt = bookDayTrip(bookDayTripParams);
    System.out.println("F??um bookingId = " + testBookDt);

    // S??kjum b??kunina ??t fr?? ??v?? ID sem vi?? fengum
    // Einnig h??gt a?? s??kja ??t fr?? kennit??lu vi??komandi.
    Hashtable<String, Object> bookdttest = new Hashtable<>();
    bookdttest.put("bookingId", testBookDt);
    ArrayList<Booking> bkngs =  getBookings(bookdttest);
    System.out.println("S??kjum svo b??kunina me?? gefnu bookingId, einnig h??gt me?? kennit??lu");
    for (Booking b : bkngs) {
      System.out.println("Email: " + b.getClientEmail() + " ||| Kennitala: " + b.getClientSSN() + " ||| B??i?? a?? borga: " + b.isPaid());
    }
    System.out.println("\n------------------------------------------------------------------\n");

    /* ---------------------------------------- */
    /* ----------- getBookings TEST ----------- */
    System.out.println("H??r er test ??ar sem s??tt eru ??ll Bookings ??n parametera:");
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
    // B??um til Review ?? daytrip sem vi?? b??ku??um h??r a?? ofan
    Hashtable<String, Object> testReview = new Hashtable<>();
    testReview.put("rating", 5);
    testReview.put("review", "??etta var besta Svi??asulta sem ??g hef ?? ??vinni pr??fa??.  Samt sm?? vont ?? h??lsinn. Fimm stj??rnur!");
    testReview.put("date", LocalDate.now());
    testReview.put("clientSSN", "300321-2240");
    testReview.put("dtId", testDayTripId);
    insertReview(testReview);
    System.out.println("H??r er engu skila?? ??egar review er b??i?? til");
    System.out.println("??v?? s??kjum vi?? review ??t fr?? kennit??lu notanda");
    System.out.println("Einnig h??gt a?? s??kja ??t fr?? dayTripId");
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
