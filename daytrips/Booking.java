package daytrips;
import java.time.LocalDate;    

public class Booking {
  private String BookingId;
  private String client;
  private DayTrip dayTrip;
  private LocalDate localDate;
  private boolean isPaid;
  private String clientEmail;
  private String clientPhoneNumber;
  private int clientCount;

  public Booking(
    String BookingId,
    String client,
    DayTrip dayTrip,
    LocalDate localDate,
    boolean isPaid,
    String clientEmail,
    String clientPhoneNumber,
    int clientCount
  ) {
    this.BookingId = BookingId;
    this.client = client;
    this.dayTrip = dayTrip;
    this.localDate = localDate;
    this.isPaid = isPaid;
    this.clientEmail = clientEmail;
    this.clientPhoneNumber = clientPhoneNumber;
    this.clientCount = clientCount;
  }

  public String getBookingId() {
    return this.BookingId;
  }

  public void setBookingId(String BookingId) {
    this.BookingId = BookingId;
  }

  public String getClient() {
    return this.client;
  }

  public void setClient(String client) {
    this.client = client;
  }

  public DayTrip getDayTrip() {
    return this.dayTrip;
  }

  public void setDayTrip(DayTrip dayTrip) {
    this.dayTrip = dayTrip;
  }

  public LocalDate getLocalDate() {
    return this.localDate;
  }

  public void setLocalDate(LocalDate localDate) {
    this.localDate = localDate;
  }

  public boolean isPaid() {
    return this.isPaid;
  }

  public void setIsPaid(boolean isPaid) {
    this.isPaid = isPaid;
  }

  public String getClientEmail() {
    return this.clientEmail;
  }

  public void setClientEmail(String clientEmail) {
    this.clientEmail = clientEmail;
  }

  public String getClientPhoneNumber() {
    return this.clientPhoneNumber;
  }

  public void setClientPhoneNumber(String clientPhoneNumber) {
    this.clientPhoneNumber = clientPhoneNumber;
  }

  public int getClientCount() {
    return this.clientCount;
  }

  public void setClientCount(int clientCount) {
    this.clientCount = clientCount;
  }
}
