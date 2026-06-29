package com.hotelapp.controller;

import com.hotelapp.model.AuthModel;
import com.hotelapp.model.AdminModel;
import com.hotelapp.model.ReceptionModel;
import com.hotelapp.view.AdminUI;
import com.hotelapp.view.BaseFrame;
import com.hotelapp.view.LoginUI;
import com.hotelapp.view.ReceptionUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private final BaseFrame baseFrame;
    private final LoginUI loginUI;
    private final AuthModel authModel;

    public LoginController(BaseFrame baseFrame, LoginUI loginUI, AuthModel authModel){
        this.baseFrame = baseFrame;
        this.loginUI = loginUI;
        this.authModel = authModel;

        this.loginUI.getCheckShowPassword().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePasswordVisibility();
            }
        });

        this.loginUI.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    private void handleLogin(){
        String username = loginUI.getUsername();
        String password = loginUI.getPassword();

        if (username.isEmpty() || password.isEmpty())
        {
            loginUI.showMessage("Enter username and password");
            return;
        }

        // get user role from the database
        String role = authModel.validateLogin(username, password);
        // if user exists
        if (role != null)
        {
            if (role.equals("Admin"))
            {
                AdminUI adminView = new AdminUI(username);
                AdminModel adminModel = new AdminModel();
                new AdminController(adminView, adminModel); // Attaches the listeners

                baseFrame.setView(adminView, "Admin Dashboard");
            }
            else if(role.equals("Receptionist"))
            {
                ReceptionUI receptionView = new ReceptionUI(username);
                ReceptionModel receptionModel = new ReceptionModel();
                new ReceptionController(receptionView, receptionModel);


                baseFrame.setView(receptionView, "Reception Dashboard");
            }
            else
            {
                loginUI.showMessage("Error: Unknown Role.");
            }
        }
        else
        {
            loginUI.showMessage("Invalid username or password!");
        }
    }

    private void togglePasswordVisibility()
    {
        // if show password selected, display the real password
        if (loginUI.getCheckShowPassword().isSelected())
        {
            loginUI.getTextPassword().setEchoChar((char) 0);
        }
        else
        {
            // else display password as ****
            loginUI.getTextPassword().setEchoChar('•');
        }
    }
}