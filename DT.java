import java.time.LocalDate;
import java.time.LocalDateTime;

public class DT {
  private String tripName;
  private LocalDate date;
  private LocalDateTime timeStart;
  private LocalDateTime timeEnd;
  private int difficulty;
  private String description;
  private int ageLimit;
  private double price;
  private int operatorID;
  private String location;
  private int capacity;

  public DT(
    String tripName,
    LocalDate date,
    LocalDateTime timeStart,
    LocalDateTime timeEnd,
    int difficulty,
    String description,
    int ageLimit,
    double price,
    int operatorID,
    String location,
    int capacity
  ) {
    this.tripName = tripName;
    this.date = date;
    this.timeStart = timeStart;
    this.timeEnd = timeEnd;
    this.difficulty = difficulty;
    this.description = description;
    this.ageLimit = ageLimit;
    this.price = price;
    this.operatorID = operatorID;
    this.location = location;
    this.capacity = capacity;
  }

  public String getTripName() {
    return this.tripName;
  }

  public void setTripName(String tripName) {
    this.tripName = tripName;
  }

  public LocalDate getDate() {
    return this.date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public LocalDateTime getTimeStart() {
    return this.timeStart;
  }

  public void setTimeStart(LocalDateTime timeStart) {
    this.timeStart = timeStart;
  }

  public LocalDateTime getTimeEnd() {
    return this.timeEnd;
  }

  public void setTimeEnd(LocalDateTime timeEnd) {
    this.timeEnd = timeEnd;
  }

  public int getDifficulty() {
    return this.difficulty;
  }

  public void setDifficulty(int difficulty) {
    this.difficulty = difficulty;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getAgeLimit() {
    return this.ageLimit;
  }

  public void setAgeLimit(int ageLimit) {
    this.ageLimit = ageLimit;
  }

  public double getPrice() {
    return this.price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getOperator() {
    return this.operatorID;
  }

  public void setOperator(int operatorID) {
    this.operatorID = operatorID;
  }

  public String getLocation() {
    return this.location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public int getCapacity() {
    return this.capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }
}
