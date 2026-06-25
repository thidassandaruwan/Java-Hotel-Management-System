package com.hotelapp.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminUI extends JPanel {
    // ui related
    private final BaseFrame baseFrame;
    private final String adminName;

    // functionality related componenets
    // Dashboard
    private JButton todayReportButton;
    private JLabel todayStRoomsLbl;
    private JLabel todayPrRoomsLbl;
    private JLabel todayTotalLbl;

    private JButton monthlyReportButton;
    private JLabel monthlyStRoomsLbl;
    private JLabel monthlyPrRoomsLbl;
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
        add(createSidePanel(), gbc);

        // limit mainPannel to 70% width
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.8;
        add(createDashboard(), gbc);


    }

    // side pannel
    private JPanel createSidePanel(){
        JPanel sidePannel = new JPanel();
        sidePannel.setBackground(baseFrame.COLOR_BLUE);
        sidePannel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // set sidepanel layout
        sidePannel.setLayout(new BoxLayout(sidePannel, BoxLayout.Y_AXIS));

        // add welcome text to side pannel
        sidePannel.add(createSpace(0, 10));
        JLabel welcomeLabel = new JLabel("Welcome " + this.adminName);
        welcomeLabel.setFont(baseFrame.FONT_SERIF_BOLD);
        welcomeLabel.setForeground(baseFrame.COLOR_GREY);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidePannel.add(welcomeLabel);
        sidePannel.add(createSpace(0, 50));

        // create navigation buttons
        sidePannel.add(createButton("Dashboard"));
        sidePannel.add(createButton("Employees"));
        sidePannel.add(createButton("Rooms"));
        return sidePannel;
    }

    // create mainPannel
    private JPanel createMainPanel(){
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(baseFrame.COLOR_BEIGE);
        mainPanel.add(createDashboard());
        return mainPanel;
    }

    // create dashboard
    private JPanel createDashboard(){
        JPanel dashboard = new JPanel();
        dashboard.setLayout(new GridLayout(1, 2, 30, 0));
        dashboard.setBackground(baseFrame.COLOR_BEIGE);
        dashboard.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        dashboard.add(createTodayInfoCard());
        dashboard.add(createMonthlyInfoCard());
        return dashboard;
    }

    // create employeeTab
    private JPanel createEmployeeTab(){
        return  new JPanel();
    }

    // create employeeTab
    private JPanel createRoomTab(){
        return  new JPanel();
    }



    // create infoCard
    private JPanel createTodayInfoCard(){
        JPanel card = new JPanel(new GridLayout(4, 1, 0, 20));
        card.setBackground(baseFrame.COLOR_BLUE);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // add the top row
        JPanel topRow = new JPanel();
        topRow.setLayout(new GridLayout(1, 2, 10, 0));
        topRow.setBackground(baseFrame.COLOR_BEIGE);
        topRow.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // add "TOday' label
        JLabel timeFrame = new JLabel("Today");
        timeFrame.setForeground(baseFrame.COLOR_BLUE);
        timeFrame.setFont(baseFrame.FONT_SERIF_BOLD);
        timeFrame.setHorizontalAlignment(SwingConstants.CENTER);
        topRow.add(timeFrame);
        // add genrate report
        this.todayReportButton = createButton("Generate Report");
        topRow.add(this.todayReportButton);

        card.add(topRow);

        // add standard room
        this.todayStRoomsLbl = createCardInfoLabel("300");
        card.add(createInfoLabelRow(createCardInfoLabel("Standard Rooms"), this.todayStRoomsLbl));


        // add premium room row
        this.todayPrRoomsLbl = createCardInfoLabel("300");
        card.add(createInfoLabelRow(createCardInfoLabel("Premium Rooms"), this.todayPrRoomsLbl));

        // add total row
        this.todayTotalLbl = createCardInfoLabel("30s00000");
        card.add(createInfoLabelRow(createCardInfoLabel("Total"), this.todayTotalLbl));

        return card;
    }

    private JPanel createMonthlyInfoCard(){
        JPanel card = new JPanel(new GridLayout(4, 1, 0, 20));
        card.setBackground(baseFrame.COLOR_BLUE);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // add the top row
        JPanel topRow = new JPanel();
        topRow.setLayout(new GridLayout(1, 2, 10, 0));
        topRow.setBackground(baseFrame.COLOR_BEIGE);
        topRow.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // add "TOday' label
        JLabel timeFrame = new JLabel("Monthly");
        timeFrame.setForeground(baseFrame.COLOR_BLUE);
        timeFrame.setFont(baseFrame.FONT_SERIF_BOLD);
        timeFrame.setHorizontalAlignment(SwingConstants.CENTER);
        topRow.add(timeFrame);
        // add genrate report
        this.monthlyReportButton = createButton("Generate Report");
        topRow.add(this.monthlyReportButton);

        card.add(topRow);

        // add standard room
        this.monthlyStRoomsLbl = createCardInfoLabel("300");
        card.add(createInfoLabelRow(createCardInfoLabel("Standard Rooms"), this.monthlyStRoomsLbl));


        // add premium room row
        this.monthlyPrRoomsLbl = createCardInfoLabel("300");
        card.add(createInfoLabelRow(createCardInfoLabel("Premium Rooms"), this.monthlyPrRoomsLbl));

        // add total row
        this.monthlyTotalLbl = createCardInfoLabel("30s00000");
        card.add(createInfoLabelRow(createCardInfoLabel("Total"), this.monthlyTotalLbl));

        return card;
    }

    // create cardInfoLabel
    private JLabel createCardInfoLabel(String text){
        JLabel label = new JLabel(text);
        label.setFont(baseFrame.FONT_SERIF_PLAIN);
        label.setForeground(baseFrame.COLOR_BEIGE);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        return label;
    }

    // take info label and add them as a jpanel
    private JPanel createInfoLabelRow(JLabel lbl1, JLabel lbl2){
        JPanel infoRow = new JPanel(new GridLayout(1, 2, 10 , 0));
        infoRow.setOpaque(false);
        infoRow.add(lbl1);
        infoRow.add(lbl2);

        return infoRow;
    }

    // create employeeInfoBar

    // create roomInforBar

    // create white space
    private Component createSpace(int width, int height){
        return Box.createRigidArea(new Dimension(width, height));
    }

    // create navButton
    private JButton createButton(String buttonName){
        JButton button = new JButton(buttonName);
        button.setBackground(baseFrame.COLOR_BLUE);
        button.setForeground(baseFrame.COLOR_BEIGE);
        button.setFont(baseFrame.FONT_SERIF_BOLD);
        button.setBorder(BorderFactory.createLineBorder(baseFrame.COLOR_BLUE, 2));
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        // set hovering effects go the jbutton
        button.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseEntered(java.awt.event.MouseEvent event){
                button.setBackground(baseFrame.COLOR_BEIGE);
                button.setForeground(baseFrame.COLOR_BLUE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent event){
                button.setBackground(baseFrame.COLOR_BLUE);
                button.setForeground(baseFrame.COLOR_BEIGE);
            }
        });
        return button;
    }
}