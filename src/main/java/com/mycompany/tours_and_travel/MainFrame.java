package com.mycompany.tours_and_travel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private HomePanel homePanel;
    private DestinationsPanel destinationsPanel;
    private PackagesPanel packagesPanel;
    private ExploreToursPanel exploreToursPanel;
    private MyBookingsPanel myBookingsPanel;
    private ContactUsPanel contactUsPanel;

    private LoginPanel loginPanel;
    private SignupPanel signupPanel;

    private AuthDialog authDialog;

    private TourDAO tourDAO;
    private BookingDAO bookingDAO;
    private PaymentDAO paymentDAO;
    private HotelDAO hotelDAO;
    private UserDAO userDAO;

    private TopBarPanel topBarPanel;
    private HeroSectionPanel heroSectionPanel;

    private TripPlannerPanel tripPlannerPanel; // Add field for TripPlannerPanel

    private int loggedInUserId = -1; // Add field to store logged-in user ID

    private HotelRoomBookingPanel hotelRoomBookingPanel; // Use only this panel for hotel room booking

    public MainFrame() {
        setTitle("ExploreEase - Travel Booking System");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tourDAO = new TourDAO();
        bookingDAO = new BookingDAO();
        paymentDAO = new PaymentDAO();
        hotelDAO = new HotelDAO();
        try {
            userDAO = new UserDAO(DBConnection.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE);

        homePanel = new HomePanel(this);
        destinationsPanel = new DestinationsPanel(this);
        packagesPanel = new PackagesPanel();
        exploreToursPanel = new ExploreToursPanel(hotelDAO, hotel -> {
            hotelRoomBookingPanel.loadRoomsForHotel(hotel);
            cardLayout.show(mainPanel, "HotelRoomBooking");
        }, this);
        myBookingsPanel = new MyBookingsPanel(bookingDAO, paymentDAO, this);
        contactUsPanel = new ContactUsPanel(this);

        loginPanel = new LoginPanel(this);
        signupPanel = new SignupPanel(this);

        authDialog = new AuthDialog(this, this);
authDialog.setLoginSuccessListener(new AuthDialog.LoginSuccessListener() {
    @Override
    public void onLoginSuccess(String username, String password) {
        try {
            User user = userDAO.authenticate(username, password);
            if (user != null) {
                System.out.println("Login successful for user: " + username + " with ID: " + user.getId());
                setLoggedInUserId(user.getId());
                authDialog.setVisible(false);
                cardLayout.show(mainPanel, "Home");
                // Enable booking UI after login
                hotelRoomBookingPanel.setBookingEnabled(true);
            } else {
                System.out.println("Login failed or invalid credentials.");
                javax.swing.JOptionPane.showMessageDialog(MainFrame.this, "Login failed: Invalid credentials.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(MainFrame.this, "Login failed: " + ex.getMessage());
        }
    }
});

        topBarPanel = new TopBarPanel();
        heroSectionPanel = new HeroSectionPanel();

        mainPanel.add(homePanel, "Home");
        mainPanel.add(destinationsPanel, "Destinations");
        mainPanel.add(packagesPanel, "Packages");
        mainPanel.add(exploreToursPanel, "ExploreHotels");
        mainPanel.add(myBookingsPanel, "MyBookings");
        mainPanel.add(contactUsPanel, "ContactUs");

        tripPlannerPanel = new TripPlannerPanel(); // Initialize TripPlannerPanel
        mainPanel.add(tripPlannerPanel, "TripPlanner");

        hotelRoomBookingPanel = new HotelRoomBookingPanel(tourDAO, bookingDAO, this);
        mainPanel.add(hotelRoomBookingPanel, "HotelRoomBooking");

        // Removed adding loginPanel to mainPanel because login is handled via AuthDialog
        // mainPanel.add(loginPanel, "Login");
        mainPanel.add(signupPanel, "Signup");

        setJMenuBar(createMenuBar());

        setLayout(new BorderLayout());
        // Removed topBarPanel to remove the top navigation bar GUI
        // add(topBarPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(heroSectionPanel, BorderLayout.NORTH);
        centerPanel.add(mainPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        cardLayout.show(mainPanel, "Home");
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(30, 144, 255)); // Dodger Blue
        menuBar.setBorder(new EmptyBorder(5, 5, 5, 5));

        JMenu menu = new JMenu("Navigate");
        menu.setForeground(Color.WHITE);
        menu.setFont(new Font("SansSerif", Font.BOLD, 16));

        JMenuItem homeItem = new JMenuItem("Home");
        homeItem.setActionCommand("Home");
        homeItem.addActionListener(this);
        menu.add(homeItem);

        JMenuItem destinationsItem = new JMenuItem("Destinations");
        destinationsItem.setActionCommand("Destinations");
        destinationsItem.addActionListener(this);
        menu.add(destinationsItem);

        JMenuItem packagesItem = new JMenuItem("Packages");
        packagesItem.setActionCommand("Packages");
        packagesItem.addActionListener(this);
        menu.add(packagesItem);

        JMenuItem hotelsItem = new JMenuItem("Hotels");
        hotelsItem.setActionCommand("Hotels");
        hotelsItem.addActionListener(this);
        menu.add(hotelsItem);

        JMenuItem tripPlannerItem = new JMenuItem("Trip Planner");
        tripPlannerItem.setActionCommand("TripPlanner");
        tripPlannerItem.addActionListener(this);
        menu.add(tripPlannerItem);

        JMenuItem contactUsItem = new JMenuItem("Contact Us");
        contactUsItem.setActionCommand("ContactUs");
        contactUsItem.addActionListener(this);
        menu.add(contactUsItem);

        JMenuItem loginSignupItem = new JMenuItem("Login / Signup");
        loginSignupItem.setActionCommand("LoginSignup");
        loginSignupItem.addActionListener(this);
        menu.add(loginSignupItem);

        menuBar.add(menu);

        return menuBar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Home":
                cardLayout.show(mainPanel, "Home");
                break;
            case "Destinations":
                cardLayout.show(mainPanel, "Destinations");
                break;
            case "Packages":
                cardLayout.show(mainPanel, "Packages");
                break;
            case "ExploreHotels":
                cardLayout.show(mainPanel, "ExploreHotels");
                break;
            case "RoomBooking":
                cardLayout.show(mainPanel, "RoomBooking");
                break;
            case "MyBookings":
                myBookingsPanel.loadBookings();
                cardLayout.show(mainPanel, "MyBookings");
                break;
            case "ContactUs":
                cardLayout.show(mainPanel, "ContactUs");
                break;
            case "TripPlanner":
                cardLayout.show(mainPanel, "TripPlanner");
                break;
            case "Hotels":
                cardLayout.show(mainPanel, "HotelRoomBooking");
                break;
            case "PreviousScreen":
                cardLayout.show(mainPanel, "ExploreHotels");
                break;
            case "LoginSignup":
            case "Login/Signup":
                authDialog.showLogin();
                break;
            case "LoginSubmit":
                // Handle login submission, validate credentials, set logged-in user ID and propagate
                String username = loginPanel.getUsername();
                String password = loginPanel.getPassword();
            //int userId = authenticateUser(username, password);
            //if (userId != -1) {
            //    setLoggedInUserId(userId);
            //    cardLayout.show(mainPanel, "Home");
            //} else {
            //    System.out.println("Login failed or invalid credentials.");
            //    // Optionally show error message dialog here
            //}
            try {
                User user = userDAO.authenticate(username, password);
                if (user != null) {
                    setLoggedInUserId(user.getId());
                    cardLayout.show(mainPanel, "Home");
                } else {
                    System.out.println("Login failed or invalid credentials.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
                break;
            default:
                System.out.println("Unknown navigation command: " + command);
                break;
        }
    }

    private int authenticateUser(String username, String password) {
        // Implement basic authentication logic here
        // For example, query the database to verify username and password
        // Return user ID if successful, else return -1

        // Placeholder implementation:
        if ("user".equals(username) && "password".equals(password)) {
            return 1; // Example user ID
        }
        return -1;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }

    // Add method to set logged-in user ID and pass it to TripPlannerPanel and HotelRoomBookingPanel
public void setLoggedInUserId(int userId) {
    System.out.println("MainFrame: Setting loggedInUserId to " + userId);
    this.loggedInUserId = userId;
    if (tripPlannerPanel != null) {
        tripPlannerPanel.setLoggedInUserId(userId);
    }
    if (hotelRoomBookingPanel != null) {
        hotelRoomBookingPanel.setLoggedInUserId(userId);
        hotelRoomBookingPanel.setBookingEnabled(userId != -1);
    }
}
}
