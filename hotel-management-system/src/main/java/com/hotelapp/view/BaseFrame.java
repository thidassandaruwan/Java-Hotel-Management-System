package com.hotelapp.view;

import java.awt.*;
import javax.swing.*;

public class BaseFrame extends JFrame{
    // custom colors for this project
    public final Color COLOR_BEIGE = new Color(0xf6f4f0); // #f6f4f0
    public final Color COLOR_BLUE = new Color(0x5068aa); // #5068aa
    public final Color COLOR_GREY = new Color(0xc4cadb); // #c4cadb
    public final Color COLOR_RED = new Color(0xcc0000); // #cc0000
    public final Color COLOR_GREEN = new Color(0x79c139); // #

    // font for the project
    public final Font FONT_SERIF_BOLD = new Font("SansSerif", Font.BOLD, 20);
    public final Font FONT_SERIF_PLAIN = new Font("SansSerif", Font.PLAIN, 20);
    
    // Jpannel to hold everything visible
    private final JPanel mainContainer;

    public BaseFrame(){
        setSize(1280, 720);
        setMinimumSize(new Dimension(800, 600));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // set logo for the applicatoin
        // logo image is in the resources/img folder following Maven guidelines
        java.net.URL logUrl = getClass().getResource("/img/HotelLogo.png");
        if (logUrl != null)
        {
            ImageIcon applicationLogo = new ImageIcon(logUrl);
            setIconImage(applicationLogo.getImage());
        }
        else
        {
            System.err.println("Could not find the logo image!");
        }

        // Jpannel to hold everything visible
        mainContainer = new JPanel();
        mainContainer.setLayout(new BorderLayout());
        mainContainer.setBackground(COLOR_BEIGE);
        // add the mainContainer to the Baseframe
        add(mainContainer);
    }

    public void setView(JPanel newView, String title){
        // set title for the application
        setTitle("The Pearl | " + title);
        // remove all exisitng content form the jpanel and replace with the newView
        mainContainer.removeAll();
        mainContainer.add(newView, BorderLayout.CENTER);
        // refresh the frame
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public JButton createButton(String text, Color foreground, Color background){
        return new JButton();
    }
}
