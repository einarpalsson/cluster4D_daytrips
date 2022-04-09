package daytrips;
import java.time.LocalDate;    


public class Review {

  private int rating;
  private String review;
  private LocalDate timeStamp;
  private String client;
  private String tripName;

  public Review(
    int rating,
    String review,
    LocalDate timeStamp,
    String client,
    String tripName
  ) {
    this.rating = rating;
    this.review = review;
    this.timeStamp = timeStamp;
    this.client = client;
    this.tripName = tripName;
  }

  public int getRating() {
    return this.rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public String getReview() {
    return this.review;
  }

  public void setReview(String review) {
    this.review = review;
  }

  public LocalDate getTimeStamp() {
    return this.timeStamp;
  }

  public void setTimeStamp(LocalDate timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getClient() {
    return this.client;
  }

  public void setClient(String client) {
    this.client = client;
  }

  public String getTripName() {
    return this.tripName;
  }

  public void setTripName(String tripName) {
    this.tripName = tripName;
  }
}
