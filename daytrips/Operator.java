package daytrips;

public class Operator {
  private String operatorId;
  private String name;
  private String location;
  private String phoneNo;

  public Operator(String operatorId, String name, String phoneNo, String location) {
    this.operatorId = operatorId;
    this.name = name;
    this.phoneNo = phoneNo;
    this.location = location;
  }

  public String getOperatorId() {
    return operatorId;
  }

  public String getName() {
    return this.name;
  }

  public String getLocation() {
    return this.location;
  }

  public String getPhoneNo() {
    return this.phoneNo;
  }
}
