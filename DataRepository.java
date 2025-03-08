
import java.util.ArrayList;

public class DataRepository {
    private ArrayList<Car> cars;
    private ArrayList<Customer> customers;
    private ArrayList<Rental> rentals;
    
    public DataRepository() {
        // Initialize with sample data
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
        
        // Sample cars
        cars.add(new Car("C001", "Toyota", "Camry", 2022, "Silver", 60.0, true));
        cars.add(new Car("C002", "Honda", "Accord", 2023, "Black", 65.0, true));
        cars.add(new Car("C003", "Ford", "Mustang", 2022, "Red", 85.0, true));
        cars.add(new Car("C004", "Chevrolet", "Malibu", 2023, "White", 55.0, true));
        cars.add(new Car("C005", "Nissan", "Altima", 2022, "Blue", 50.0, true));
        
        // Sample customers
        customers.add(new Customer("CUS001", "John Smith", "555-1234", "john@example.com", "123 Main St"));
        customers.add(new Customer("CUS002", "Sarah Johnson", "555-5678", "sarah@example.com", "456 Oak Ave"));
        customers.add(new Customer("CUS003", "Michael Brown", "555-9012", "michael@example.com", "789 Pine Rd"));
    }
    
    // Car methods
    public ArrayList<Car> getAllCars() {
        return cars;
    }
    
    public Car getCarById(String id) {
        for (Car car : cars) {
            if (car.getId().equals(id)) {
                return car;
            }
        }
        return null;
    }
    
    public void addCar(Car car) {
        cars.add(car);
    }
    
    public void updateCar(Car updatedCar) {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getId().equals(updatedCar.getId())) {
                cars.set(i, updatedCar);
                return;
            }
        }
    }
    
    public void deleteCar(String id) {
        cars.removeIf(car -> car.getId().equals(id));
    }
    
    // Customer methods
    public ArrayList<Customer> getAllCustomers() {
        return customers;
    }
    
    public Customer getCustomerById(String id) {
        for (Customer customer : customers) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }
        return null;
    }
    
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
    
    public void updateCustomer(Customer updatedCustomer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId().equals(updatedCustomer.getId())) {
                customers.set(i, updatedCustomer);
                return;
            }
        }
    }
    
    public void deleteCustomer(String id) {
        customers.removeIf(customer -> customer.getId().equals(id));
    }
    
    // Rental methods
    public ArrayList<Rental> getAllRentals() {
        return rentals;
    }
    
    public Rental getRentalById(String id) {
        for (Rental rental : rentals) {
            if (rental.getId().equals(id)) {
                return rental;
            }
        }
        return null;
    }
    
    public void addRental(Rental rental) {
        rentals.add(rental);
    }
    
    public void updateRental(Rental updatedRental) {
        for (int i = 0; i < rentals.size(); i++) {
            if (rentals.get(i).getId().equals(updatedRental.getId())) {
                rentals.set(i, updatedRental);
                return;
            }
        }
    }
    
    public void deleteRental(String id) {
        rentals.removeIf(rental -> rental.getId().equals(id));
    }
}