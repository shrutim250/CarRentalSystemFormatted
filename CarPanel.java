

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CarPanel extends JPanel {
    private JTextField idField, makeField, modelField, yearField, colorField, rateField;
    private JCheckBox availableCheck;
    private JTable carTable;
    private DefaultTableModel tableModel;
    private DataRepository repository;

    public CarPanel(DataRepository repository) {
        this.repository = repository;
        setLayout(new BorderLayout());

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Car Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Car ID:"), gbc);

        gbc.gridx = 1;
        idField = new JTextField(10);
        formPanel.add(idField, gbc);

        // Make
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Make:"), gbc);

        gbc.gridx = 1;
        makeField = new JTextField(10);
        formPanel.add(makeField, gbc);

        // Model
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Model:"), gbc);

        gbc.gridx = 1;
        modelField = new JTextField(10);
        formPanel.add(modelField, gbc);

        // Year
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Year:"), gbc);

        gbc.gridx = 1;
        yearField = new JTextField(10);
        formPanel.add(yearField, gbc);

        // Color
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Color:"), gbc);

        gbc.gridx = 1;
        colorField = new JTextField(10);
        formPanel.add(colorField, gbc);

        // Daily Rate
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Daily Rate:"), gbc);

        gbc.gridx = 1;
        rateField = new JTextField(10);
        formPanel.add(rateField, gbc);

        // Available
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Available:"), gbc);

        gbc.gridx = 1;
        availableCheck = new JCheckBox();
        availableCheck.setSelected(true);
        formPanel.add(availableCheck, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        // Add the form and buttons to the top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the top panel
        add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Make", "Model", "Year", "Color", "Daily Rate", "Available"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        carTable = new JTable(tableModel);

        // Customize table design
        customizeTable(carTable);

        // Add table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(carTable);
        scrollPane.setPreferredSize(new Dimension(800, 400)); // Set larger size
        add(scrollPane, BorderLayout.CENTER);

        // Load initial data
        loadCarData();

        // Action listeners
        addButton.addActionListener(e -> addCar());
        updateButton.addActionListener(e -> updateCar());
        deleteButton.addActionListener(e -> deleteCar());
        clearButton.addActionListener(e -> clearFields());

        carTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && carTable.getSelectedRow() != -1) {
                displaySelectedCar();
            }
        });
    }

    private void customizeTable(JTable table) {
        // Set larger font for table content
        Font tableFont = new Font("Segoe UI", Font.PLAIN, 16);
        table.setFont(tableFont);
        table.setRowHeight(30); // Increase row height

        // Set alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 240, 240)); // Alternating colors
                }
                return c;
            }
        });

        // Customize table header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 18)); // Larger and bold font
        header.setBackground(new Color(0, 102, 204)); // Blue background
        header.setForeground(Color.WHITE); // White text
        header.setPreferredSize(new Dimension(header.getWidth(), 40)); // Increase header height
    }

    private void loadCarData() {
        tableModel.setRowCount(0);
        for (Car car : repository.getAllCars()) {
            Object[] row = {
                car.getId(),
                car.getMake(),
                car.getModel(),
                car.getYear(),
                car.getColor(),
                car.getDailyRate(),
                car.isAvailable()
            };
            tableModel.addRow(row);
        }
    }

    private void displaySelectedCar() {
        int row = carTable.getSelectedRow();
        idField.setText(tableModel.getValueAt(row, 0).toString());
        makeField.setText(tableModel.getValueAt(row, 1).toString());
        modelField.setText(tableModel.getValueAt(row, 2).toString());
        yearField.setText(tableModel.getValueAt(row, 3).toString());
        colorField.setText(tableModel.getValueAt(row, 4).toString());
        rateField.setText(tableModel.getValueAt(row, 5).toString());
        availableCheck.setSelected((Boolean) tableModel.getValueAt(row, 6));
    }

    private void addCar() {
        try {
            String id = idField.getText();
            String make = makeField.getText();
            String model = modelField.getText();
            int year = Integer.parseInt(yearField.getText());
            String color = colorField.getText();
            double rate = Double.parseDouble(rateField.getText());
            boolean available = availableCheck.isSelected();

            if (id.isEmpty() || make.isEmpty() || model.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields");
                return;
            }

            Car car = new Car(id, make, model, year, color, rate, available);
            repository.addCar(car);
            loadCarData();
            clearFields();
            JOptionPane.showMessageDialog(this, "Car added successfully");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid number values");
        }
    }

    private void updateCar() {
        int selectedRow = carTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a car to update");
            return;
        }

        try {
            String id = idField.getText();
            String make = makeField.getText();
            String model = modelField.getText();
            int year = Integer.parseInt(yearField.getText());
            String color = colorField.getText();
            double rate = Double.parseDouble(rateField.getText());
            boolean available = availableCheck.isSelected();

            Car car = new Car(id, make, model, year, color, rate, available);
            repository.updateCar(car);
            loadCarData();
            JOptionPane.showMessageDialog(this, "Car updated successfully");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid number values");
        }
    }

    private void deleteCar() {
        int selectedRow = carTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a car to delete");
            return;
        }

        String id = tableModel.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this car?", "Confirm Delete",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            repository.deleteCar(id);
            loadCarData();
            clearFields();
            JOptionPane.showMessageDialog(this, "Car deleted successfully");
        }
    }

    private void clearFields() {
        idField.setText("");
        makeField.setText("");
        modelField.setText("");
        yearField.setText("");
        colorField.setText("");
        rateField.setText("");
        availableCheck.setSelected(true);
        carTable.clearSelection();
    }
}