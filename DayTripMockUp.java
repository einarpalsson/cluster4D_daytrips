public class DayTripMockUp {
    private String name;
    private String location;
    private int diffuculty;
    private double price;

    public DayTripMockUp(String name, String location, int diffuculty, double price) {
        this.name = name;
        this.location = location;
        this.diffuculty = diffuculty;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }


    public int getDifficulty() {
        return this.diffuculty;
    }

    public double getPrice() {
        return this.price;
    }

    public void printLocation() {
        System.out.println(this.location);
    }
}