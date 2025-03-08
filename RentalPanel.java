
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RentalPanel extends JPanel {
    private JTextField idField, totalField;
    private JComboBox<String> carCombo, customerCombo;
    private JFormattedTextField startDateField, endDateField;
    private JTable rentalTable;
    private DefaultTableModel tableModel;
    private DataRepository repository;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public RentalPanel(DataRepository repository) {
        this.repository = repository;
        setLayout(new BorderLayout());
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Rental Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Rental ID:"), gbc);
        
        gbc.gridx = 1;
        idField = new JTextField(10);
        formPanel.add(idField, gbc);
        
        // Car
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Car:"), gbc);
        
        gbc.gridx = 1;
        carCombo = new JComboBox<>();
        formPanel.add(carCombo, gbc);
        
        // Customer
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Customer:"), gbc);
        
        gbc.gridx = 1;
        customerCombo = new JComboBox<>();
        formPanel.add(customerCombo, gbc);
        
        // Start Date
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Start Date (yyyy-MM-dd):"), gbc);
        
        gbc.gridx = 1;
        startDateField = new JFormattedTextField(dateFormat);
        startDateField.setColumns(10);
        formPanel.add(startDateField, gbc);
        
        // End Date
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("End Date (yyyy-MM-dd):"), gbc);
        
        gbc.gridx = 1;
        endDateField = new JFormattedTextField(dateFormat);
        endDateField.setColumns(10);
        formPanel.add(endDateField, gbc);
        
        // Total
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Total Cost:"), gbc);
        
        gbc.gridx = 1;
        totalField = new JTextField(10);
        totalField.setEditable(false);
        formPanel.add(totalField, gbc);
        
        // Calculate Button
        JButton calculateButton = new JButton("Calculate Total");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        formPanel.add(calculateButton, gbc);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Create Rental");
        JButton returnButton = new JButton("Return Car");
        JButton clearButton = new JButton("Clear Fields");
        
        buttonPanel.add(addButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(clearButton);
        
        // Add the form and buttons to the top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add the top panel
        add(topPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Car", "Customer", "Start Date", "End Date", "Total Cost", "Returned"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        rentalTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(rentalTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // Load combo boxes
        loadComboBoxes();
        
        // Load initial data
        loadRentalData();
        
        // Action listeners
        calculateButton.addActionListener(e -> calculateTotal());
        addButton.addActionListener(e -> createRental());
        returnButton.addActionListener(e -> returnCar());
        clearButton.addActionListener(e -> clearFields());
        
        rentalTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && rentalTable.getSelectedRow() != -1) {
                displaySelectedRental();
            }
        });
    }
    
    private void loadComboBoxes() {
        carCombo.removeAllItems();
        customerCombo.removeAllItems();
        
        for (Car car : repository.getAllCars()) {
            if (car.isAvailable()) {
                carCombo.addItem(car.getId() + " - " + car.getMake() + " " + car.getModel());
            }
        }
        
        for (Customer customer : repository.getAllCustomers()) {
            customerCombo.addItem(customer.getId() + " - " + customer.getName());
        }
    }
    
    private void loadRentalData() {
        tableModel.setRowCount(0);
        for (Rental rental : repository.getAllRentals()) {
            Object[] row = {
                rental.getId(),
                rental.getCar().getMake() + " " + rental.getCar().getModel(),
                rental.getCustomer().getName(),
                dateFormat.format(rental.getStartDate()),
                dateFormat.format(rental.getEndDate()),
                String.format("$%.2f", rental.getTotalCost()),
                rental.isReturned() ? "Yes" : "No"
            };
            tableModel.addRow(row);
        }
    }
    
    private void displaySelectedRental() {
        int row = rentalTable.getSelectedRow();
        
        String rentalId = tableModel.getValueAt(row, 0).toString();
        Rental rental = repository.getRentalById(rentalId);
        
        if (rental != null) {
            idField.setText(rental.getId());
            startDateField.setText(dateFormat.format(rental.getStartDate()));
            endDateField.setText(dateFormat.format(rental.getEndDate()));
            totalField.setText(String.format("%.2f", rental.getTotalCost()));
            
            // These might not match exactly due to filtering
            // In a real app, you'd want to check by ID
            for (int i = 0; i < carCombo.getItemCount(); i++) {
                if (carCombo.getItemAt(i).contains(rental.getCar().getId())) {
                    carCombo.setSelectedIndex(i);
                    break;
                }
            }
            
            for (int i = 0; i < customerCombo.getItemCount(); i++) {
                if (customerCombo.getItemAt(i).contains(rental.getCustomer().getId())) {
                    customerCombo.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
    
    private void calculateTotal() {
        try {
            if (carCombo.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Please select a car");
                return;
            }
            
            String carId = carCombo.getSelectedItem().toString().split(" - ")[0];
            Car car = repository.getCarById(carId);
            
            if (car == null) {
                JOptionPane.showMessageDialog(this, "Selected car not found");
                return;
            }
            
            Date startDate = dateFormat.parse(startDateField.getText());
            Date endDate = dateFormat.parse(endDateField.getText());
            
            if (endDate.before(startDate)) {
                JOptionPane.showMessageDialog(this, "End date cannot be before start date");
                return;
            }
            
            long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
            long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            // Ensure at least 1 day
            days = Math.max(1, days);
            
            double total = days * car.getDailyRate();
            totalField.setText(String.format("%.2f", total));
            
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid dates in yyyy-MM-dd format");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error calculating total: " + e.getMessage());
        }
    }
    
    private void createRental() {
        try {
            String id = idField.getText();
            
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter rental ID");
                return;
            }
            
            if (carCombo.getSelectedIndex() == -1 || customerCombo.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Please select car and customer");
                return;
            }
            
            String carId = carCombo.getSelectedItem().toString().split(" - ")[0];
            String customerId = customerCombo.getSelectedItem().toString().split(" - ")[0];
            
            Car car = repository.getCarById(carId);
            Customer customer = repository.getCustomerById(customerId);
            
            if (car == null || customer == null) {
                JOptionPane.showMessageDialog(this, "Car or customer not found");
                return;
            }
            
            Date startDate = dateFormat.parse(startDateField.getText());
            Date endDate = dateFormat.parse(endDateField.getText());
            
            if (endDate.before(startDate)) {
                JOptionPane.showMessageDialog(this, "End date cannot be before start date");
                return;
            }
            
            double total;
            if (totalField.getText().isEmpty()) {
                calculateTotal();
            }
            total = Double.parseDouble(totalField.getText());
            
            Rental rental = new Rental(id, car, customer, startDate, endDate, total, false);
            repository.addRental(rental);
            
            // Update car availability
            car.setAvailable(false);
            repository.updateCar(car);
            
            loadRentalData();
            loadComboBoxes();
            clearFields();
            
            JOptionPane.showMessageDialog(this, "Rental created successfully");
            
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid dates in yyyy-MM-dd format");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error creating rental: " + e.getMessage());
        }
    }
    
    private void returnCar() {
        int selectedRow = rentalTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a rental");
            return;
        }
        
        String rentalId = tableModel.getValueAt(selectedRow, 0).toString();
        boolean isReturned = tableModel.getValueAt(selectedRow, 6).toString().equals("Yes");
        
        if (isReturned) {
            JOptionPane.showMessageDialog(this, "Car has already been returned");
            return;
        }
        
        Rental rental = repository.getRentalById(rentalId);
        if (rental != null) {
            rental.setReturned(true);
            repository.updateRental(rental);
            
            // Update car availability
            Car car = rental.getCar();
            car.setAvailable(true);
            repository.updateCar(car);
            
            loadRentalData();
            loadComboBoxes();
            clearFields();
            
            JOptionPane.showMessageDialog(this, "Car returned successfully");
        }
    }
    
    private void clearFields() {
        idField.setText("");
        startDateField.setText("");
        endDateField.setText("");
        totalField.setText("");
        carCombo.setSelectedIndex(-1);
        customerCombo.setSelectedIndex(-1);
        rentalTable.clearSelection();
    }
}
