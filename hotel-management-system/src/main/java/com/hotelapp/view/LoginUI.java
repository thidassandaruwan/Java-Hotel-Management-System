package com.hotelapp.view;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class LoginUI extends JPanel {
    private final Border PANNEL_BORDER = BorderFactory.createLineBorder(Color.BLACK, 4,true);
    private final Border PANNEL_PADDING = BorderFactory.createEmptyBorder(40, 50, 40, 50);
    private final Border FIELD_BORDER = BorderFactory.createLineBorder(Color.BLACK, 2, true);
    private final Border FIELD_PADDING = BorderFactory.createEmptyBorder(5, 10, 5, 10);
    
    private final BaseFrame baseFrame;

    // input components
    private final JTextField textUsername;
    private final JPasswordField textPassword;
    private final JCheckBox checkShowPassword;
    private final JButton loginButton;

    public LoginUI(BaseFrame baseFrame){
        // assign baseFrame so that other methods can use it
        this.baseFrame = baseFrame;

        // layout and bgColor for the main pannel
        setLayout(new GridBagLayout());
        setBackground(baseFrame.COLOR_BEIGE);

        //sub jpannel with the login UI
        JPanel loginBox = new JPanel();
        loginBox.setLayout(new BoxLayout(loginBox, BoxLayout.Y_AXIS));
        loginBox.setBackground(baseFrame.COLOR_BEIGE);
        // curve the edges and set paddings for the sub jpannel
        loginBox.setBorder(BorderFactory.createCompoundBorder(PANNEL_BORDER, PANNEL_PADDING));

        // USERNAME  
        //label
        loginBox.add(createLabel("Username: "));
        // space between label and textfield
        loginBox.add(createSpace(0, 10));

        //inputField
        textUsername = new JTextField(20);
        styleTextField(textUsername);
        loginBox.add(textUsername);
        loginBox.add(createSpace(0, 20));

        // PASSWORD 
        //label
        loginBox.add(createLabel("Password: "));
        // space between label and textfield
        loginBox.add(createSpace(0, 10));

        //inputField
        textPassword = new JPasswordField(20);
        styleTextField(textPassword);
        loginBox.add(textPassword);
        loginBox.add(createSpace(0, 10));
        // add show password checkbox
        checkShowPassword = createCheckBox("Show Password");
        loginBox.add(checkShowPassword);
        loginBox.add(createSpace(0, 25));

        loginButton = createButton("Login");
        loginBox.add(loginButton);
        
        // add loginbox pannel to the main pannel
        add(loginBox);
    }
    
    private JLabel createLabel(String labelText){
        // create label with text, assign font
        JLabel label = new JLabel(labelText);
        label.setFont(baseFrame.FONT_SERIF_BOLD);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JCheckBox createCheckBox(String text){
        JCheckBox checkShowPassword = new JCheckBox(text);
        checkShowPassword.setBackground(baseFrame.COLOR_BEIGE);
        checkShowPassword.setFont(baseFrame.FONT_SERIF_PLAIN);
        checkShowPassword.setAlignmentX(Component.LEFT_ALIGNMENT);

        return checkShowPassword;
    }

    private JButton createButton(String buttonName){
        JButton button = new JButton(buttonName);
        button.setBackground(baseFrame.COLOR_BLUE);
        button.setForeground(baseFrame.COLOR_BEIGE);
        button.setFont(baseFrame.FONT_SERIF_BOLD);
        button.setBorder(BorderFactory.createLineBorder(baseFrame.COLOR_BLUE, 2));
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(400, 45));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);

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

    private void styleTextField(JTextField textField){
        textField.setFont(baseFrame.FONT_SERIF_PLAIN);
        textField.setMaximumSize(new Dimension(400, 40));
        textField.setBorder(BorderFactory.createCompoundBorder(FIELD_BORDER, FIELD_PADDING));
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);

        // focus gained lost effects to the textFields
        textField.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent event){
                Border activeBorder = BorderFactory.createLineBorder(baseFrame.COLOR_BLUE, 2, true);
                textField.setBorder(BorderFactory.createCompoundBorder(activeBorder, FIELD_PADDING));
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent event){
                textField.setBorder(BorderFactory.createCompoundBorder(FIELD_BORDER, FIELD_PADDING));
            }
        });
    }
    
    private Component createSpace(int width, int height){
        return Box.createRigidArea(new Dimension(width, height));
    } 

    // getters
    public String getUsername(){return textUsername.getText();}
    public String getPassword(){return new String(textPassword.getPassword());}
    public JCheckBox getCheckShowPassword(){return checkShowPassword;}
    public JPasswordField getTextPassword(){return textPassword;}
    public JButton getLoginButton(){return loginButton;}

    // show message method
    public void showMessage(String message){
        JOptionPane.showMessageDialog(this, message);
    }
}   
