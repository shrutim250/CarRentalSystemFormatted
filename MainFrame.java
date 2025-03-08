

import java.awt.Font;

import javax.swing.*;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private CarPanel carPanel;
    private CustomerPanel customerPanel;
    private RentalPanel rentalPanel;

    public MainFrame() {
        setTitle("Car Rental System");
        setSize(1000, 700); // Larger window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set a modern font with larger size
        Font customFont = new Font("Segoe UI", Font.PLAIN, 16);
        UIManager.put("Label.font", customFont);
        UIManager.put("Button.font", customFont);
        UIManager.put("TextField.font", customFont);

        // Initialize data repository
        DataRepository repository = new DataRepository();

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Car Rental System v1.0\nDeveloped with Java Swing",
                "About", JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        // Create tabbed pane
        tabbedPane = new JTabbedPane();

        // Create panels
        carPanel = new CarPanel(repository);
        customerPanel = new CustomerPanel(repository);
        rentalPanel = new RentalPanel(repository);

        // Add panels to tabbed pane
        tabbedPane.addTab("Cars", new ImageIcon(), carPanel, "Manage Cars");
        tabbedPane.addTab("Customers", new ImageIcon(), customerPanel, "Manage Customers");
        tabbedPane.addTab("Rentals", new ImageIcon(), rentalPanel, "Manage Rentals");

        add(tabbedPane);
    }
}