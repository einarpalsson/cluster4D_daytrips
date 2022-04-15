package daytrips;
import java.time.LocalDate;
import java.util.UUID;    

public class Booking {
  private UUID BookingId;
  private String clientSSN;
  private String clientEmail;
  private String clientPhoneNumber;
  private int clientCount;
  private DayTrip dayTrip;
  private LocalDate localDate;
  private boolean isPaid;

  public Booking(
    UUID BookingId,
    String clientSSN,
    String clientEmail,
    String clientPhoneNumber,
    int clientCount,
    DayTrip dayTrip,
    LocalDate localDate,
    boolean isPaid
  ) {
    this.BookingId = BookingId;
    this.clientSSN = clientSSN;
    this.clientEmail = clientEmail;
    this.clientPhoneNumber = clientPhoneNumber;
    this.dayTrip = dayTrip;
    this.localDate = localDate;
    this.isPaid = isPaid;
    this.clientCount = clientCount;
  }

  public UUID getBookingId() {
    return this.BookingId;
  }

  public void setBookingId(UUID BookingId) {
    this.BookingId = BookingId;
  }

  public String getClient() {
    return this.clientSSN;
  }

  public void setClient(String client) {
    this.clientSSN = client;
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
