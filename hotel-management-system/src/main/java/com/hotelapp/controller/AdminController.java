package com.hotelapp.controller;

import com.hotelapp.model.AdminModel;
import com.hotelapp.model.AdminDashboardStats;
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
        // Dashboard Tab Routing
        view.getDashboardButton().addActionListener(e -> syncDashboardView());

        // Employees Tab Routing
        view.getEmployeeButton().addActionListener(e -> {
            List<String[]> activeEmployees = model.getAllEmployees();
            view.updateMainPanel(view.createEmployeeTab(activeEmployees));
        });

        // Room Tab Routing Component Hooks
        view.getRoomButton().addActionListener(e -> {
            // Future feature insertion: update dynamically using room query patterns
        });

        // Customer Tab Routing Component Hooks
        view.getCustomerButton().addActionListener(e -> {
            // Future feature insertion: update dynamically using booking logs query patterns
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
