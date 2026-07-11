package com.hotelapp.util;

import com.hotelapp.model.DatabaseHelper;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class ReportService {

    public static void generateReport(String reportFilepath){
        // connect to the database
        try (Connection conn = DatabaseHelper.connect()){
            // read the jasper report into binary data
            InputStream report = ReportService.class.getResourceAsStream(reportFilepath);
            // check if report is loaded
            if (report == null)
            {
                JOptionPane.showMessageDialog(null,"Report file could not be found at "
                        + reportFilepath + "Report Generation Error " + JOptionPane.ERROR_MESSAGE);
                // stop the repoting printing proces
                return;
            }

            // if report loaded
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, new HashMap<>(), conn);
            // display the jasperPRint
            JasperViewer.viewReport(jasperPrint);
        }
        // exception for both SQL database and jasper report fillReport JRException
        catch (Exception exception){
            JOptionPane.showMessageDialog(null, "Error occured when printing the report"
                    + exception.getMessage() + "Report Generation Error " + JOptionPane.ERROR_MESSAGE);
        }
    }
}
