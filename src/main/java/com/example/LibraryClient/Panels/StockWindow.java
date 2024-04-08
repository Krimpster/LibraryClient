package com.example.LibraryClient.Panels;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StockWindow {
    private JFrame jFrame;
    private JPanel stockPanel;
    private JList list1;
    private JTextField stockTextField;
    private JButton addToStockButton;
    private JLabel currentStockLabel;
    DefaultListModel listModel = new DefaultListModel<>();

    public StockWindow(){
        jFrame = new JFrame("Add books.");
        jFrame.setContentPane(stockPanel);
        jFrame.setSize(400, 300);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);

        addToStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
            }
        });
    }
}
