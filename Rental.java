import java.util.Date;

public class Rental {
    private String id;
    private Car car;
    private Customer customer;
    private Date startDate;
    private Date endDate;
    private double totalCost;
    private boolean returned;
    
    public Rental(String id, Car car, Customer customer, Date startDate, Date endDate, double totalCost, boolean returned) {
        this.id = id;
        this.car = car;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
        this.returned = returned;
    }
    
    public String getId() {
        return id;
    }
    
    public Car getCar() {
        return car;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public double getTotalCost() {
        return totalCost;
    }
    
    public boolean isReturned() {
        return returned;
    }
    
    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}