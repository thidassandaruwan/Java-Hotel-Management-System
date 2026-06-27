package com.hotelapp.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.util.List;
import com.hotelapp.model.AdminDashboardStats;


public class AdminUI extends JPanel {
    // ui related
    private final BaseFrame baseFrame;
    private final String adminName;

    // functionality related componenets
    private JButton dashboardButton;
    private JButton employeeButton;
    private JButton roomButton;
    private JButton customerButton;

    // mainpannel
    private JPanel mainPanel;

    // Dashboard
    private JButton todayReportButton;
    private JLabel todaySingleStRoomsLbl;
    private JLabel todaySinglePrRoomsLbl;
    private JLabel todayDoubleStRoomsLbl;
    private JLabel todayDoublePrRoomsLbl;
    private JLabel todayTotalLbl;

    private JButton monthlyReportButton;
    private JLabel monthlySingleStRoomsLbl;
    private JLabel monthlySinglePrRoomsLbl;
    private JLabel monthlyDoubleStRoomsLbl;
    private JLabel monthlyDoublePrRoomsLbl;
    private JLabel monthlyTotalLbl;

    // functionality realted
    private record dashboardStats(double standardRooms, double premiumRooms, double total){}
    //dashboard
    private dashboardStats dailyStats;
    private dashboardStats monthlySats;

    public AdminUI(BaseFrame baseFrame, String adminName) {
        this.baseFrame = baseFrame;
        this.adminName = adminName;

        // set windows layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        // expand in both directions
        gbc.fill = GridBagConstraints.BOTH;
        // take full height
        gbc.weighty = 1.0;

        // limit sidepannel to 30% width
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        JPanel sidePanel = createSidePanel();
        sidePanel.setPreferredSize(new Dimension(0, 0));
        add(sidePanel, gbc);

        // limit mainPannel to 70% width
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.8;
        this.mainPanel = createMainPanel();
        this.mainPanel.setPreferredSize(new Dimension(0, 0));
        add(this.mainPanel, gbc);


    }

    // side pannel
    private JPanel createSidePanel(){
        JPanel sidePannel = new JPanel();
        sidePannel.setBackground(baseFrame.COLOR_BLUE);

        // set sidepanel layout
        sidePannel.setLayout(new BoxLayout(sidePannel, BoxLayout.Y_AXIS));

        // add welcome text to side pannel
        sidePannel.add(createSpace(0, 20));
        JLabel welcomeLabel = new JLabel("Welcome " + this.adminName);
        welcomeLabel.setFont(baseFrame.FONT_SERIF_BOLD);
        welcomeLabel.setForeground(baseFrame.COLOR_GREY);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidePannel.add(welcomeLabel);
        sidePannel.add(createSpace(0, 50));

        // create navigation buttons
        dashboardButton = createButton("Dashboard", baseFrame.COLOR_BEIGE, baseFrame.COLOR_BLUE);
        employeeButton = createButton("Employees", baseFrame.COLOR_BEIGE, baseFrame.COLOR_BLUE);
        roomButton = createButton("Rooms", baseFrame.COLOR_BEIGE, baseFrame.COLOR_BLUE);
        customerButton = createButton("Customers", baseFrame.COLOR_BEIGE, baseFrame.COLOR_BLUE);

        sidePannel.add(dashboardButton);
        sidePannel.add(employeeButton);
        sidePannel.add(roomButton);
        sidePannel.add(customerButton);
        return sidePannel;
    }

    // create mainPannel
    private JPanel createMainPanel(){
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(baseFrame.COLOR_BEIGE);
        mainPanel.setLayout(new BorderLayout());
        return mainPanel;
    }

    // create dashboard
    public JPanel createDashboard(){
        JPanel dashboard = new JPanel();
        dashboard.setLayout(new GridLayout(1, 2, 30, 0));
        dashboard.setBackground(baseFrame.COLOR_BEIGE);
        dashboard.setBorder(createPadding(30));

        dashboard.add(createTodayInfoCard());
        dashboard.add(createMonthlyInfoCard());
        return dashboard;
    }

    // create infoCard
    private JPanel createTodayInfoCard(){
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(baseFrame.COLOR_BLUE);
        card.setBorder(createPadding(20));

        // add the top row
        JPanel topRow = new JPanel();
        topRow.setLayout(new GridLayout(1, 2, 10, 0));
        topRow.setBackground(baseFrame.COLOR_BEIGE);
        topRow.setBorder(createPadding(5));
        topRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        // add "TOday' label
        JLabel timeFrame = new JLabel("Today");
        timeFrame.setForeground(baseFrame.COLOR_BLUE);
        timeFrame.setFont(baseFrame.FONT_SERIF_BOLD);
        timeFrame.setHorizontalAlignment(SwingConstants.CENTER);
        topRow.add(timeFrame);
        // add genrate report
        this.todayReportButton = createButton("Generate Report", baseFrame.COLOR_BEIGE, baseFrame.COLOR_BLUE);
        todayReportButton.setBorder(BorderFactory.createCompoundBorder(createBorder(baseFrame.COLOR_BLUE, 2), createPadding(10)));
        topRow.add(this.todayReportButton);

        card.add(topRow);
        card.add(createSpace(0, 10));

        // seperate pannel for rest of the data in the card
        JPanel contentArea = new JPanel(new GridLayout(5, 1, 0, 20));
        contentArea.setBackground(baseFrame.COLOR_BEIGE);
        contentArea.setBorder(BorderFactory.createCompoundBorder(createBorder(baseFrame.COLOR_BEIGE, 2), createPadding(10)));
        contentArea.setOpaque(false);

        // add single standard rooms
        this.todaySingleStRoomsLbl = createLabel("", baseFrame.COLOR_BEIGE, baseFrame.FONT_SERIF_PLAIN);
        contentArea.add(createInfoLabelRow(createLabel("Standard Single Rooms", baseFrame.COLOR_BEIGE, baseFrame.FONT_SERIF_PLAIN), this.todaySingleStRoomsLbl));
        // add double standard rooms
        this.todayDoubleStRoomsLbl = createLabel("", baseFrame.COLOR_BEIGE, baseFrame.FONT_SERIF_PLAIN);
        contentArea.add(createInfoLabelRow(createLabel("Standard Double Rooms", baseFrame.COLOR_BEIGE, baseFrame.FONT_SERIF_PLAIN), this.todayDoubleStRoomsLbl));

        // add single premium room
        this.todaySinglePrRoomsLbl = createLabel("", baseFrame.COLOR_BEIGE, baseFrame.FONT_SERIF_PLAIN);
        contentArea.add(createInfoLabelRow(createLabel("Premium Single Rooms", baseFrame.COLOR_BEIGE, baseFrame.FONT_SERIF_PLAIN), this.todaySinglePrRoomsLbl));
        // add double premium room
        this.todayDoublePrRoomsLbl = createLabel("", baseFrame.COLOR_BEIGE, baseFrame.FONT_SERIF_PLAIN);
        contentArea.add(createInfoLabelRow(createLabel("Premium Double Rooms", baseFrame.COLOR_BEIGE, baseFrame.FONT_SERIF_PLAIN), this.todayDoublePrRoomsLbl));

        // add total row
        this.todayTotalLbl = createLabel("", baseFrame.COLOR_BEIGE, baseFrame.FONT_SERIF_PLAIN);
        contentArea.add(createInfoLabelRow(createLabel("Total", baseFrame.COLOR_BEIGE, baseFrame.FONT_SERIF_PLAIN), this.todayTotalLbl));

        card.add(contentArea);
        return card;
    }

    private JPanel createMonthlyInfoCard(){
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(baseFrame.COLOR_BLUE);
        card.setBorder(createPadding(20));

        // add the top row
        JPanel topRow = new JPanel();
        topRow.setLayout(new GridLayout(1, 2, 10, 0));
        topRow.setBackground(baseFrame.COLOR_BEIGE);
        topRow.setBorder(createPadding(5));
        topRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        // add "TOday' label
        JLabel timeFrame = new JLabel("Monthly");
        timeFrame.setForeground(baseFrame.COLOR_BLUE);
        timeFrame.setFont(baseFrame.FONT_SERIF_BOLD);
        timeFrame.setHorizontalAlignment(SwingConstants.CENTER);
        topRow.add(timeFrame);
        // add genrate report
        this.monthlyReportButton = createButton("Generate Report", baseFrame.COLOR_BEIGE, baseFrame.COLOR_BLUE);
        monthlyReportButton.setBorder(BorderFactory.createCompoundBorder(createBorder(baseFrame.COLOR_BLUE, 2), createPadding(10)));
        topRow.add(this.monthlyReportButton);

        card.add(topRow);
        card.add(createSpace(0, 10));

        // seperate pannel for rest of the data in the card
        JPanel contentArea = new JPanel(new GridLayout(5, 1, 0, 20));
        contentArea.setBorder(BorderFactory.createCompoundBorder(createBorder(baseFrame.COLOR_BEIGE, 2), createPadding(10)));
        contentArea.setOpaque(false);

        // add single standard room
        this.monthlySingleStRoomsLbl = createLabel("", baseFrame.COLOR_BEIGE, baseFrame.FONT_SERIF_PLAIN);
        contentArea.add(createInfoLabelRow(createLabel("Standard Single Rooms", baseFrame.COLOR_BEIGE, baseFrame.FONT_SERIF_PLAIN), this.monthlySingleStRoomsLbl));
        // add Double standard room
        this.monthlyDoubleStRoomsLbl = createLabel("", baseFrame.COLOR_BEIGE, baseFrame.FONT_SERIF_PLAIN);
        contentArea.add(createInfoLabelRow(createLabel("Standard Double Rooms", baseFrame.COLOR_BEIGE, baseFrame.FONT_SERIF_PLAIN), this.monthlyDoubleStRoomsLbl));

        // add single preimum room
        this.monthlySinglePrRoomsLbl = createLabel("", baseFrame.COLOR_BEIGE, baseFrame.FONT_SERIF_PLAIN);
        contentArea.add(createInfoLabelRow(createLabel("Premium Single Rooms", baseFrame.COLOR_BEIGE, baseFrame.FONT_SERIF_PLAIN), this.monthlySinglePrRoomsLbl));
        // add double premium room
        this.monthlyDoublePrRoomsLbl = createLabel("", baseFrame.COLOR_BEIGE, baseFrame.FONT_SERIF_PLAIN);
        contentArea.add(createInfoLabelRow(createLabel("Premium Double Rooms", baseFrame.COLOR_BEIGE, baseFrame.FONT_SERIF_PLAIN), this.monthlyDoublePrRoomsLbl));

        // add total row
        this.monthlyTotalLbl = createLabel("", baseFrame.COLOR_BEIGE, baseFrame.FONT_SERIF_PLAIN);
        contentArea.add(createInfoLabelRow(createLabel("Total", baseFrame.COLOR_BEIGE, baseFrame.FONT_SERIF_PLAIN), this.monthlyTotalLbl));

        card.add(contentArea);

        return card;
    }

    // take info label and add them as a jpanel
    private JPanel createInfoLabelRow(JLabel lbl1, JLabel lbl2){
        JPanel infoRow = new JPanel(new GridLayout(1, 2, 10 , 0));
        infoRow.setOpaque(false);
        infoRow.add(lbl1);
        infoRow.add(lbl2);
        infoRow.setBorder(BorderFactory.createCompoundBorder(createBorder(baseFrame.COLOR_BEIGE, 2), createPadding(10)));

        return infoRow;
    }

    // update dashboard metrics
    public void updateDashboardMetrics(AdminDashboardStats dailyStats, AdminDashboardStats monthlyStats){
        // Update Today Metrics
        this.todaySingleStRoomsLbl.setText(String.valueOf(dailyStats.standardSingle()));
        this.todayDoubleStRoomsLbl.setText(String.valueOf(dailyStats.standardDouble()));
        this.todaySinglePrRoomsLbl.setText(String.valueOf(dailyStats.premiumSingle()));
        this.todayDoublePrRoomsLbl.setText(String.valueOf(dailyStats.premiumDouble()));
        this.todayTotalLbl.setText(String.format("LKR%.2f", dailyStats.totalRevenue()));

        // Update Monthly Metrics
        this.monthlySingleStRoomsLbl.setText(String.valueOf(monthlyStats.standardSingle()));
        this.monthlyDoubleStRoomsLbl.setText(String.valueOf(monthlyStats.standardDouble()));
        this.monthlySinglePrRoomsLbl.setText(String.valueOf(monthlyStats.premiumSingle()));
        this.monthlyDoublePrRoomsLbl.setText(String.valueOf(monthlyStats.premiumDouble()));
        this.monthlyTotalLbl.setText(String.format("LKR%.2f", monthlyStats.totalRevenue()));
    }

    // create employeeTab
    public JPanel createEmployeeTab(List<String[]> employees){
        JPanel employeePanel = new JPanel();
        employeePanel.setLayout(new BoxLayout(employeePanel, BoxLayout.Y_AXIS));

        // add top row
        JPanel header = new JPanel(new GridLayout(1, 3, 0, 0));
        header.setBackground(baseFrame.COLOR_GREY);
        header.setBorder(createPadding(10));

        header.add(createLabel("Employee Name", baseFrame.COLOR_BLUE, baseFrame.FONT_SERIF_BOLD));
        header.add(createLabel("Employee Role", baseFrame.COLOR_BLUE, baseFrame.FONT_SERIF_BOLD));
        header.add(createButton("Add New Employee", baseFrame.COLOR_BEIGE, baseFrame.COLOR_BLUE));

        employeePanel.add(header);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(createPadding(30));

        // iterate to the number of employees there are
        for (String[] employee:employees){
            // print employee name and role
            contentPanel.add(createEmployeeRow(employee[0], employee[1]));
            contentPanel.add(createSpace(0, 10));
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);

        scrollPane.getVerticalScrollBar().setUnitIncrement(5);

        employeePanel.add(scrollPane);

        return  employeePanel;
    }

    private JPanel createEmployeeRow(String empName, String empRole){
        JPanel row = new JPanel(new GridLayout(1, 3, 0, 0));
        row.setBackground(baseFrame.COLOR_BEIGE);
        row.setBorder(BorderFactory.createCompoundBorder(createBorder(baseFrame.COLOR_BLUE, 2), createPadding(10)));

        row.add(createLabel(empName, baseFrame.COLOR_BLUE, baseFrame.FONT_SERIF_PLAIN));
        row.add(createLabel(empRole, baseFrame.COLOR_BLUE, baseFrame.FONT_SERIF_PLAIN));
        row.add(createButton("Modify", baseFrame.COLOR_BEIGE, baseFrame.COLOR_BLUE));

        return row;
    }

    // create roomTab
    public JPanel createRoomTab(){
        JPanel roomPannel = new JPanel();
        roomPannel.setLayout(new BoxLayout(roomPannel, BoxLayout.Y_AXIS));

        // add top row
        JPanel header = new JPanel(new GridLayout(1, 5, 0, 0));
        header.setBackground(baseFrame.COLOR_GREY);
        header.setBorder(createPadding(10));

        header.add(createLabel("Room ID", baseFrame.COLOR_BLUE, baseFrame.FONT_SERIF_BOLD));
        header.add(createLabel("Size", baseFrame.COLOR_BLUE, baseFrame.FONT_SERIF_BOLD));
        header.add(createLabel("Tier", baseFrame.COLOR_BLUE, baseFrame.FONT_SERIF_BOLD));
        header.add(createLabel("Status", baseFrame.COLOR_BLUE, baseFrame.FONT_SERIF_BOLD));
        header.add(createButton("Add New Room", baseFrame.COLOR_BEIGE, baseFrame.COLOR_BLUE));

        roomPannel.add(header);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(createPadding(30));

        for (int i = 0; i < 30; i++){
            contentPanel.add(createRoomRow("01", "Single Bed", "Standard", "Ready"));
            contentPanel.add(createSpace(0, 10));
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);

        scrollPane.getVerticalScrollBar().setUnitIncrement(5);

        roomPannel.add(scrollPane);

        return  roomPannel;
    }

    // create room row
    private JPanel createRoomRow(String roomId, String size, String tier, String status){
        JPanel row = new JPanel(new GridLayout(1, 5, 0, 0));
        row.setBackground(baseFrame.COLOR_BEIGE);
        row.setBorder(BorderFactory.createCompoundBorder(createBorder(baseFrame.COLOR_BLUE, 2), createPadding(10)));

        row.add(createLabel(roomId, baseFrame.COLOR_BLUE, baseFrame.FONT_SERIF_PLAIN));
        row.add(createLabel(size, baseFrame.COLOR_BLUE, baseFrame.FONT_SERIF_PLAIN));
        row.add(createLabel(tier, baseFrame.COLOR_BLUE, baseFrame.FONT_SERIF_PLAIN));
        row.add(createLabel(status, baseFrame.COLOR_BLUE, baseFrame.FONT_SERIF_PLAIN));
        row.add(createButton("Modify", baseFrame.COLOR_BEIGE, baseFrame.COLOR_BLUE));

        return row;
    }

    // create customerTab
    private JPanel createCustomerTab(){
        JPanel customerPanel = new JPanel();
        customerPanel.setLayout(new BoxLayout(customerPanel, BoxLayout.Y_AXIS));

        // add top row
        JPanel header = new JPanel(new GridLayout(1, 5, 0, 0));
        header.setBackground(baseFrame.COLOR_GREY);
        header.setBorder(createPadding(10));

        header.add(createLabel("Custmer ID", baseFrame.COLOR_BLUE, baseFrame.FONT_SERIF_BOLD));
        header.add(createLabel("Customer Name", baseFrame.COLOR_BLUE, baseFrame.FONT_SERIF_BOLD));
        header.add(createLabel("Room ID", baseFrame.COLOR_BLUE, baseFrame.FONT_SERIF_BOLD));
        header.add(createLabel("Check-in Date", baseFrame.COLOR_BLUE, baseFrame.FONT_SERIF_BOLD));
        header.add(createLabel("Check-out Date", baseFrame.COLOR_BLUE, baseFrame.FONT_SERIF_BOLD));

        customerPanel.add(header);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(createPadding(30));

        for (int i = 0; i < 30; i++){
            contentPanel.add(createCustomerRow("01", "Yehan", "01", "2026-06-27", "2026-06-29"));
            contentPanel.add(createSpace(0, 10));
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);

        scrollPane.getVerticalScrollBar().setUnitIncrement(5);

        customerPanel.add(scrollPane);

        return  customerPanel;
    }

    // create customer row
    private JPanel createCustomerRow(String id, String name, String roomId, String checkIn, String checkOut){
        JPanel row = new JPanel(new GridLayout(1, 5, 0, 0));
        row.setBackground(baseFrame.COLOR_BEIGE);
        row.setBorder(BorderFactory.createCompoundBorder(createBorder(baseFrame.COLOR_BLUE, 2), createPadding(10)));

        row.add(createLabel(id, baseFrame.COLOR_BLUE, baseFrame.FONT_SERIF_PLAIN));
        row.add(createLabel(name, baseFrame.COLOR_BLUE, baseFrame.FONT_SERIF_PLAIN));
        row.add(createLabel(roomId, baseFrame.COLOR_BLUE, baseFrame.FONT_SERIF_PLAIN));
        row.add(createLabel(checkIn, baseFrame.COLOR_BLUE, baseFrame.FONT_SERIF_PLAIN));
        row.add(createLabel(checkOut, baseFrame.COLOR_BLUE, baseFrame.FONT_SERIF_PLAIN));

        return row;
    }

    // create white space
    private Component createSpace(int width, int height){
        return Box.createRigidArea(new Dimension(width, height));
    }

    // create padding border
    private Border createPadding(int size){
        return BorderFactory.createEmptyBorder(size, size, size, size);
    }
    // create visible border
    private Border createBorder(Color color, int thickness){
        return BorderFactory.createLineBorder(color, thickness);
    }

    // create navButton
    private JButton createButton(String buttonName, Color foreground, Color background){
        JButton button = new JButton(buttonName);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFont(baseFrame.FONT_SERIF_BOLD);
        button.setBorder(createBorder(background, 2));
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        // set hovering effects go the jbutton
        button.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseEntered(java.awt.event.MouseEvent event){
                button.setBackground(foreground);
                button.setForeground(background);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent event){
                button.setBackground(background);
                button.setForeground(foreground);
            }
        });
        return button;
    }

    // create label method
    private JLabel createLabel(String text, Color textColor, Font font){
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(textColor);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        return label;
    }

    // update MainPanel
    public void updateMainPanel(JPanel newView){
        mainPanel.removeAll();
        mainPanel.add(newView, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // TODO getters for components
    public JButton getDashboardButton(){return  this.dashboardButton;}
    public JButton getEmployeeButton(){return this.employeeButton;}
    public JButton getRoomButton(){return this.roomButton;}
    public JButton getCustomerButton(){return this.customerButton;}

    // TODO getters for values
}