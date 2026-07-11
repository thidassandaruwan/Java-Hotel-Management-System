package com.hotelapp.controller;

import com.hotelapp.model.*;
import com.hotelapp.util.ReportService;
import com.hotelapp.view.AdminUI;
import com.hotelapp.util.ReportService.*;

import javax.swing.*;
import java.util.List;

public class AdminController {
    private final AdminUI view;
    private final AdminModel model;

    public AdminController(AdminUI view, AdminModel model){
        this.view = view;
        this.model = model;

        setupSidePanelNavigationListners();

        // open dashboard by default
        syncDashboardView();
    }

    private void syncDashboardView() {
        // get monthly and daily sales stats
        AdminDashboardStats dailyStats = model.getDashboardStats(true);
        AdminDashboardStats monthlyStats = model.getDashboardStats(false);

        // open the Dashboard Ui by default
        view.updateMainPanel(view.createDashboard());

        // update the dashboard metrics data
        view.updateDashboardMetrics(dailyStats, monthlyStats);

        // report generation functinality
        // daily reports
        if (view.getTodayReportBtn() != null)
        {
            view.getTodayReportBtn().addActionListener(e ->{
                // call the report generat method with daily sales report jasper file
                ReportService.generateReport("/reports/hotel-daily-sales-reports.jasper");
            });
        }

        // monthly reports
        if (view.getMonthlyReportBtn() != null)
        {
            view.getMonthlyReportBtn().addActionListener(e ->{
                // call the report generat method with daily sales report jasper file
                ReportService.generateReport("/reports/hotel-monthly-sales-reports.jasper");
            });
        }
    }

    private void setupSidePanelNavigationListners(){
        // Dashboard actionlistner
        view.getDashboardButton().addActionListener(e -> syncDashboardView());

        // Employees Tab actionlistner
        view.getEmployeeButton().addActionListener(e -> {
            List<Employee> employees = model.getAllEmployees();
            view.updateMainPanel(view.createEmployeeTab(employees));
            // setup navigation listeners
            setupEmployeeTabNavigationListeners();
            // setup filter seach action listeners
            setupEmployeeSearchAndFilter();
        });

        // roomButton actionlistner
        view.getRoomButton().addActionListener(e -> {
            List<Room> rooms = model.getAllRooms();
            view.updateMainPanel(view.createRoomTab(rooms));
            // setup navigation listeners
            setupRoomTabNavigationListeners();
            // setup filter seach action listeners
            setupRoomSearchAndFilter();
        });

        // Customer button actionlistner
        view.getCustomerButton().addActionListener(e -> {
            List<CustomerRecord> customerRecords = model.getAllCustomerRecords();
            view.updateMainPanel(view.createCustomerTab(customerRecords));
            // setup filter seach action listeners
            setupCustomerSearch();
        });
    }

    private void setupEmployeeTabNavigationListeners(){
        if (view.getNewEmployeeTabBtn() != null){
            view.getNewEmployeeTabBtn().addActionListener(e -> {
                view.updateMainPanel(view.createNewEmployeeTab());
                // once new employee tab opens, add the action listenrs to those buttons
                setupNewEmployeeFormListeners();
            });
        }

        if (view.getEditEmployeeBtns() != null){
            for (JButton editButton:view.getEditEmployeeBtns()){
                editButton.addActionListener(e -> {
                    // get the clicked button
                    JButton clickedButton = (JButton) e.getSource();
                    // get the employee object stored inside the clickedbutton ( the object is in a commom object form, hence the Employee casting)
                    Employee selectedEmployee = (Employee) clickedButton.getClientProperty("employeeData");

                    // create the edit employee page
                    view.updateMainPanel(view.createEditEmployeeTab(selectedEmployee));
                    // setup editemployee form button functionality
                    setupEditEmployeeFormListeners();
                });
            }
        }
    }

    private void setupEmployeeSearchAndFilter() {
        // Employee SEARCH Button
        view.getEmpSearchButton().addActionListener(e -> {
            String keyword = view.getEmpNameSearchField().getText();
            // since filtering and searching handles by the same databasemodel method, ignoring filter field by forcing "any" role
            String role = "Any";

            // Fetch filtered data
            List<Employee> results = model.getFilteredEmployees(keyword, role);

            // Refresh UI
            view.updateMainPanel(view.createEmployeeTab(results));

            // Restore the search text, and reset the filter dropdown
            view.getEmpNameSearchField().setText(keyword);
            view.getEmpRoleFilter().setSelectedItem("Any");

            // Re-bind listeners
            setupEmployeeTabNavigationListeners();
            setupEmployeeSearchAndFilter();
        });

        // 2. Employee FILTER Button (Role Only)
        view.getEmpFilterButton().addActionListener(e -> {
            String keyword = ""; // Force search to empty
            String role = (String) view.getEmpRoleFilter().getSelectedItem();

            // Fetch filtered data
            List<Employee> results = model.getFilteredEmployees(keyword, role);

            // Refresh UI
            view.updateMainPanel(view.createEmployeeTab(results));

            // CLEAR the search text box, but restore the filter dropdown
            view.getEmpNameSearchField().setText("");
            view.getEmpRoleFilter().setSelectedItem(role);

            // Re-bind listeners
            setupEmployeeTabNavigationListeners();
            setupEmployeeSearchAndFilter();
        });
    }

    private void setupNewEmployeeFormListeners(){
        view.getBackToEmployeesBtn().addActionListener(e -> {
            view.updateMainPanel(view.createEmployeeTab(model.getAllEmployees()));
            // rebind the navigation buttons in employee tab
            setupEmployeeTabNavigationListeners();
        });

        // Handle 'Save Employee'
        view.getSaveNewEmployeeBtn().addActionListener(e -> {
            String username = view.getNewEmpUsername().getText();
            String password = view.getNewEmpPassword().getText();
            String role = (view.getNewEmpRole().getSelectedItem()).toString();

            if (!username.isEmpty() && !password.isEmpty() && !role.isEmpty())
            {
                // remove any white spaces
                username = username.trim();
                password = password.trim();
                // throw error if username or password has any spaces
                if ((username.contains(" ") || password.contains(" "))){
                    JOptionPane.showMessageDialog(null, "No spaces are allowed in username or password");
                }
                else
                {
                    Employee newEmp = new Employee(username, password, role);
                    if (model.addEmployee(newEmp)) {
                        // Success! show a success popup and clear the textfields
                        JOptionPane.showMessageDialog(null, "Employee " + username + " added successfully!");
                        view.getNewEmpUsername().setText("");
                        view.getNewEmpPassword().setText("");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Error adding employee. Username may already exist.");
                    }
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Please fill out all fields.");
            }
        });
    }

    private void setupEditEmployeeFormListeners() {
        // go back button
        view.getBackToEmployeesBtn().addActionListener(e -> {
            view.updateMainPanel(view.createEmployeeTab(model.getAllEmployees()));
            setupEmployeeTabNavigationListeners();
        });

        // save employee
        view.getSaveEditEmployeeBtn().addActionListener(e -> {
            String username = view.getEditEmpUsernameField().getText();
            String password = view.getEditEmpPasswordField().getText().trim();
            String role = view.getEditEmpRoleField().getSelectedItem().toString();

            if (!password.isEmpty()) {
                if (password.contains(" ")) {
                    JOptionPane.showMessageDialog(null, "No spaces allowed in password.");
                    return;
                }

                Employee updatedEmp = new Employee(username, password, role);
                if (model.updateEmployee(updatedEmp)) {
                    JOptionPane.showMessageDialog(null, "Employee updated successfully!");
                    view.updateMainPanel(view.createEmployeeTab(model.getAllEmployees()));
                    setupEmployeeTabNavigationListeners();
                } else {
                    JOptionPane.showMessageDialog(null, "Error updating employee.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Password cannot be empty.");
            }
        });

        // remove Employee
        view.getRemoveEmployeeBtn().addActionListener(e -> {
            String username = view.getEditEmpUsernameField().getText();

            // Add a confirmation dialog before deleting!
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to permanently delete " + username + "?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (model.deleteEmployee(username)) {
                    JOptionPane.showMessageDialog(null, "Employee removed.");
                    view.updateMainPanel(view.createEmployeeTab(model.getAllEmployees()));
                    setupEmployeeTabNavigationListeners();
                } else {
                    JOptionPane.showMessageDialog(null, "Error removing employee.");
                }
            }
        });
    }

    // room actionlisteners
    private void setupRoomTabNavigationListeners(){
        if (view.getNewRoomTabBtn() != null) {
            view.getNewRoomTabBtn().addActionListener(e -> {
                view.updateMainPanel(view.createNewRoomTab());
                // once new room tab opens, add the action listenrs to those buttons
                setupNewRoomFormActionListeners();
            });
        }

        if (view.getEditRoomBtns() != null){
            for (JButton editButton:view.getEditRoomBtns()){
                editButton.addActionListener(e -> {
                    // get the clicked button
                    JButton clickedButton = (JButton) e.getSource();
                    // get the room object stored inside the clickedbutton ( the object is in a commom object form, hence the Employee casting)
                    Room selectedRoom = (Room) clickedButton.getClientProperty("roomData");

                    // create the edit room page
                    view.updateMainPanel(view.createEditRoomTab(selectedRoom));
                    // implement editroom form buttons funcionality
                    setupEditRoomFormListeners();
                });
            }
        }
    }

    private void setupRoomSearchAndFilter() {
        // search button
        view.getRoomSearchButton().addActionListener(e -> {
            String keyword = view.getRoomIdSearchField().getText();
            // Pass "Any" to all filters to ignore them
            List<Room> results = model.getFilteredRooms(keyword, "Any", "Any", "Any");

            view.updateMainPanel(view.createRoomTab(results));

            // Restore search input, RESET all dropdowns
            view.getRoomIdSearchField().setText(keyword);
            view.getRoomTierFilter().setSelectedItem("Any");
            view.getRoomSpaceFilter().setSelectedItem("Any");
            view.getRoomStatusFilter().setSelectedItem("Any");

            setupRoomTabNavigationListeners();
            setupRoomSearchAndFilter();
        });

        // Room FILTER Button (Dropdowns Only)
        view.getRoomFilterButton().addActionListener(e -> {
            String tier = (String) view.getRoomTierFilter().getSelectedItem();
            String space = (String) view.getRoomSpaceFilter().getSelectedItem();
            String status = (String) view.getRoomStatusFilter().getSelectedItem();

            // Pass empty string to the ID search to ignore it
            List<Room> results = model.getFilteredRooms("", tier, space, status);

            view.updateMainPanel(view.createRoomTab(results));

            // CLEAR search input, restore dropdown selections
            view.getRoomIdSearchField().setText("");
            view.getRoomTierFilter().setSelectedItem(tier);
            view.getRoomSpaceFilter().setSelectedItem(space);
            view.getRoomStatusFilter().setSelectedItem(status);

            setupRoomTabNavigationListeners();
            setupRoomSearchAndFilter();
        });
    }

    private void setupNewRoomFormActionListeners(){
        view.getBackToRoomBtn().addActionListener(e -> {
            view.updateMainPanel(view.createRoomTab(model.getAllRooms()));
            // rebind the navigation buttons in employee tab
            setupEditRoomFormListeners();
        });

        // adding new rooms
        view.getSaveNewRoomBtn().addActionListener(e -> {
            String tier = view.getNewRoomTierField().getSelectedItem().toString();
            String space = view.getNewRoomSpaceField().getSelectedItem().toString();
            String priceString = view.getNewRoomPriceField().getText();

            if (!tier.isEmpty() && !space.isEmpty() && !priceString.isEmpty())
            {
                // remove any white spaces
                priceString.trim();
                // convert the price to double
                double price;
                // try convert the price to double, if any error throw a error pop up and clear the price field
                try{
                    price = Double.parseDouble(priceString);
                }
                catch (Exception priceError)
                {
                    JOptionPane.showMessageDialog(null, "Enter a valid price" );
                    view.getNewRoomPriceField().setText("");
                    return;
                }

                Room newRoom = new Room(0, space, tier, "Ready", price);
                if (model.addRoom(newRoom)) {
                    // Success! show a success popup and clear the textfields
                    JOptionPane.showMessageDialog(null, "New " + tier + " room added successfully!");
                    view.getNewRoomPriceField().setText("");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Error adding room");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Please fill out all fields.");
            }
        });
    }

    private void setupEditRoomFormListeners() {
        // go back actionlisner
        view.getBackToRoomBtn().addActionListener(e -> {
            view.updateMainPanel(view.createRoomTab(model.getAllRooms()));
            setupRoomTabNavigationListeners();
        });

        // save room button
        view.getSaveEditRoomBtn().addActionListener(e -> {
            int roomId = Integer.parseInt(view.getEditRoomIdField().getText());
            String tier = view.getEditRoomTierField().getSelectedItem().toString();
            String space = view.getEditRoomSpaceField().getSelectedItem().toString();
            String status = view.getEditRoomStatusField().getSelectedItem().toString();
            String priceString = view.getEditRoomPriceField().getText().trim();

            if (!priceString.isEmpty()) {
                try {
                    double price = Double.parseDouble(priceString);
                    Room updatedRoom = new Room(roomId, space, tier, status, price);

                    if (model.updateRoom(updatedRoom)) {
                        JOptionPane.showMessageDialog(null, "Room updated successfully!");
                        view.updateMainPanel(view.createRoomTab(model.getAllRooms()));
                        setupRoomTabNavigationListeners();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error updating room.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number for the price.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Price cannot be empty.");
            }
        });

        // remove room button
        view.getRemoveRoomBtn().addActionListener(e -> {
            int roomId = Integer.parseInt(view.getEditRoomIdField().getText());

            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to permanently delete Room ID " + roomId + "?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (model.deleteRoom(roomId)) {
                    JOptionPane.showMessageDialog(null, "Room removed.");
                    view.updateMainPanel(view.createRoomTab(model.getAllRooms()));
                    setupRoomTabNavigationListeners();
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Cannot remove a room that has customer records associated with it.");
                }
            }
        });
    }

    // customer search controller
    private void setupCustomerSearch() {
        view.getCustomerSearchButton().addActionListener(e -> {
            String keyword = view.getCustSearchField().getText();
            String type = (String) view.getCustomerSearchTypeField().getSelectedItem();

            // Fetch filtered data
            List<CustomerRecord> results = model.searchCustomerRecords(type, keyword);

            // Refresh UI
            view.updateMainPanel(view.createCustomerTab(results));

            // Restore inputs
            view.getCustSearchField().setText(keyword);
            view.getCustomerSearchTypeField().setSelectedItem(type);

            // Re-bind listener
            setupCustomerSearch();
        });
    }
}
