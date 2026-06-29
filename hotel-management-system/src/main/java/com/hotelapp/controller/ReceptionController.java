package com.hotelapp.controller;

import com.hotelapp.model.*;
import com.hotelapp.view.AdminUI;
import com.hotelapp.view.ReceptionUI;

import javax.swing.*;
import java.util.List;

public class ReceptionController {
    private final ReceptionUI view;
    private final ReceptionModel model;

    public ReceptionController(ReceptionUI view, ReceptionModel model){
        this.view = view;
        this.model = model;

        setupSidePanelNavigationListners();

        // open room tab by default
        syncRoomTabView();
    }

    private void syncRoomTabView() {
        // get all rooms
        List<Room> rooms = model.getAllRooms();
        // create the room tab view
        view.updateMainPanel(view.createRoomTab(rooms));
        // setup navigation listeners
        setupRoomTabNavigationListeners();
        // setup filter seach action listeners
        setupRoomSearchAndFilter();
    }

    private void setupSidePanelNavigationListners(){
        // roomButton actionlistner
        view.getRoomButton().addActionListener(e -> syncRoomTabView());

        // Customer button actionlistner
        view.getBookRoomButton().addActionListener(e -> {
            view.updateMainPanel(view.createBookRoomTab());
        });

        // Customer button actionlistner
        view.getCustomerButton().addActionListener(e -> {
            List<CustomerRecord> customerRecords = model.getAllCustomerRecords();
            view.updateMainPanel(view.createCustomerTab(customerRecords));
            // setup filter seach action listeners
            setupCustomerSearch();
        });
    }

    // room actionlisteners
    private void setupRoomTabNavigationListeners(){
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

    private void setupEditRoomFormListeners() {
        // go back actionlisner
        view.getBackToRoomBtn().addActionListener(e -> {
            view.updateMainPanel(view.createRoomTab(model.getAllRooms()));
            setupRoomTabNavigationListeners();
        });

        // save room button
        view.getSaveEditRoomBtn().addActionListener(e -> {
            int roomId = Integer.parseInt(view.getEditRoomIdField().getText());
            String status = view.getEditRoomStatusField().getSelectedItem().toString();


            if (model.updateRoom(roomId, status)) {
                JOptionPane.showMessageDialog(null, "Room updated successfully!");
                view.updateMainPanel(view.createRoomTab(model.getAllRooms()));
                setupRoomTabNavigationListeners();
            } else {
                JOptionPane.showMessageDialog(null, "Error updating room.");
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
