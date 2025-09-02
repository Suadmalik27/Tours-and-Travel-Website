package com.mycompany.tours_and_travel;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Tours_and_travel extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private HomePanel homePanel;
    private ExploreToursPanel exploreToursPanel;
    private HotelBookingPanel hotelBookingPanel;
    private MyBookingsPanel myBookingsPanel;
    private ContactUsPanel contactUsPanel;
    private DestinationsPanel destinationsPanel;
    private PackagesPanel packagesPanel;

    private TourDAO tourDAO;
    private BookingDAO bookingDAO;
    private PaymentDAO paymentDAO;
    private HotelDAO hotelDAO;

    private String previousScreen = "Home";

    public Tours_and_travel() {
        setTitle("Tours and Travel");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tourDAO = new TourDAO();
        bookingDAO = new BookingDAO();
        paymentDAO = new PaymentDAO();
        hotelDAO = new HotelDAO();

        addDummyHotels();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        homePanel = new HomePanel(this);
        exploreToursPanel = new ExploreToursPanel(hotelDAO, hotel -> {
            hotelBookingPanel.loadRoomsForHotel(hotel);
            previousScreen = "ExploreHotels";
            cardLayout.show(mainPanel, "RoomBooking");
        }, this);
        hotelBookingPanel = new HotelBookingPanel(tourDAO, bookingDAO, this);
        myBookingsPanel = new MyBookingsPanel(bookingDAO, paymentDAO, this);
        contactUsPanel = new ContactUsPanel(this);
        destinationsPanel = new DestinationsPanel(this);
        packagesPanel = new PackagesPanel(this);

        mainPanel.add(homePanel, "Home");
        mainPanel.add(exploreToursPanel, "ExploreHotels");
        mainPanel.add(hotelBookingPanel, "RoomBooking");
        mainPanel.add(myBookingsPanel, "MyBookings");
        mainPanel.add(contactUsPanel, "ContactUs");
        mainPanel.add(destinationsPanel, "Destinations");
        mainPanel.add(packagesPanel, "Packages");

        add(mainPanel);

        cardLayout.show(mainPanel, "Home");
    }

    private void addDummyHotels() {
        try {
            hotelDAO.addHotel(new Hotel(1, "Grand Plaza", "New York", "Luxury hotel in the heart of the city", 250.0));
            hotelDAO.addHotel(new Hotel(2, "Sea Breeze Resort", "Miami", "Beachfront resort with stunning views", 180.0));
            hotelDAO.addHotel(new Hotel(3, "Mountain Retreat", "Denver", "Cozy mountain lodge with ski access", 150.0));
            hotelDAO.addHotel(new Hotel(4, "City Center Inn", "Chicago", "Affordable hotel near downtown attractions", 120.0));
            hotelDAO.addHotel(new Hotel(5, "Historic Hotel", "Boston", "Charming hotel with historic architecture", 200.0));
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Home":
                cardLayout.show(mainPanel, "Home");
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
            case "Destinations":
                cardLayout.show(mainPanel, "Destinations");
                break;
            case "Packages":
                cardLayout.show(mainPanel, "Packages");
                break;
            case "PreviousScreen":
                cardLayout.show(mainPanel, previousScreen);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Unknown navigation command: " + command);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Tours_and_travel().setVisible(true);
        });
    }
}
