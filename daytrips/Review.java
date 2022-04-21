package daytrips;
import java.time.LocalDate;    


public class Review {

  private int rating;
  private String review;
  private LocalDate date;
  private String clientSSN;
  private String dtId;

  public Review(
    int rating,
    String review,
    LocalDate date,
    String clientSSN,
    String dtId
  ) {
    this.rating = rating;
    this.review = review;
    this.date = date;
    this.clientSSN = clientSSN;
    this.dtId = dtId;
  }

  public int getRating() {
    return this.rating;
  }

  public String getReview() {
    return this.review;
  }

  public LocalDate getDate() {
    return this.date;
  }

  public String getClientSSN() {
    return this.clientSSN;
  }

  public String getDayTripId() {
    return this.dtId;
  }
}
