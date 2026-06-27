package com.hotelapp.controller;

import com.hotelapp.model.*;
import com.hotelapp.view.AdminUI;
import java.util.List;

public class AdminController {
    private final AdminUI view;
    private final AdminModel model;

    public AdminController(AdminUI view, AdminModel model){
        this.view = view;
        this.model = model;

        setupNavigationListners();

        // open dashboard by default
        syncDashboardView();
    }

    private void setupNavigationListners(){
        // Dashboard actionlistner
        view.getDashboardButton().addActionListener(e -> syncDashboardView());

        // Employees Tab actionlistner
        view.getEmployeeButton().addActionListener(e -> {
            List<Employee> employees = model.getAllEmployees();
            view.updateMainPanel(view.createEmployeeTab(employees));
        });

        // roomButton actionlistner
        view.getRoomButton().addActionListener(e -> {
            List<Room> rooms = model.getAllRooms();
            view.updateMainPanel(view.createRoomTab(rooms));
        });

        // Customer button actionlistner
        view.getCustomerButton().addActionListener(e -> {
            List<CustomerRecord> customerRecords = model.getAllCustomerRecords();
            view.updateMainPanel(view.createCustomerTab(customerRecords));
        });
    }

    private void syncDashboardView() {
        // get monthly and daily sales stats
        AdminDashboardStats dailyStats = model.getDashboardStats(true);
        AdminDashboardStats monthlyStats = model.getDashboardStats(false);

        // open the Dashboard Ui by default
        view.updateMainPanel(view.createDashboard());

        // update the dashboard metrics data
        view.updateDashboardMetrics(dailyStats, monthlyStats);
    }
}
