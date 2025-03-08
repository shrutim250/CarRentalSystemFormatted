
public class Car {
    private String id;
    private String make;
    private String model;
    private int year;
    private String color;
    private double dailyRate;
    private boolean available;
    
    public Car(String id, String make, String model, int year, String color, double dailyRate, boolean available) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.dailyRate = dailyRate;
        this.available = available;
    }
    
    public String getId() {
        return id;
    }
    
    public String getMake() {
        return make;
    }
    
    public String getModel() {
        return model;
    }
    
    public int getYear() {
        return year;
    }
    
    public String getColor() {
        return color;
    }
    
    public double getDailyRate() {
        return dailyRate;
    }
    
    public boolean isAvailable() {
        return available;
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }
}
