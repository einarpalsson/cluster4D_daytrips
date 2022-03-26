package daytrips;

public class Operator {

  private String operatorName;
  private String location;
  private String phoneNo;

  public Operator(String operatorName, String location, String phoneNo) {
    this.operatorName = operatorName;
    this.location = location;
    this.phoneNo = phoneNo;
  }

  public String getOperatorName() {
    return this.operatorName;
  }

  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }

  public String getLocation() {
    return this.location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getPhoneNo() {
    return this.phoneNo;
  }

  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }
}
