package daytrips;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class DayTrip {
  private String dayTripId;
  private String name;
  private double price;
  private String description;
  private String location;
  private LocalDate date;
  private LocalDateTime timeStart;
  private LocalDateTime timeEnd;
  private int ageLimit;
  private int difficulty;
  private int capacity;
  private String operatorId;

  public DayTrip(
    String dayTripId,
    String name,
    double price,
    String description,
    String location,
    LocalDate date,
    LocalDateTime timeStart,
    LocalDateTime timeEnd,
    int ageLimit,
    int difficulty,
    int capacity,
    String operatorId
  ) {
    this.dayTripId = dayTripId;
    this.name = name;
    this.price = price;
    this.description = description;
    this.location = location;
    this.date = date;
    this.timeStart = timeStart;
    this.timeEnd = timeEnd;
    this.ageLimit = ageLimit;
    this.difficulty = difficulty;
    this.capacity = capacity;
    this.operatorId = operatorId;
  }

  public String getDayTripId() {
      return dayTripId;
  }

  public String getName() {
    return this.name;
  }

  public LocalDate getDate() {
    return this.date;
  }

  public LocalDateTime getTimeStart() {
    return this.timeStart;
  }

  public LocalDateTime getTimeEnd() {
    return this.timeEnd;
  }

  public int getDifficulty() {
    return this.difficulty;
  }

  public String getDescription() {
    return this.description;
  }

  public int getAgeLimit() {
    return this.ageLimit;
  }

  public double getPrice() {
    return this.price;
  }

  public String getOperatorId() {
    return this.operatorId.toString();
  }

  public String getLocation() {
    return this.location;
  }

  public int getCapacity() {
    return this.capacity;
  }
}
