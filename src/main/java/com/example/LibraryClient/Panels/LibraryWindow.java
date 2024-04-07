package com.example.LibraryClient.Panels;

import com.example.LibraryClient.Panels.AddBookWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryWindow {
    private JFrame jFrame;
    private JList bookJList;
    private JButton addBookButton;
    private JButton addStockButton;
    private JButton deleteBookButton;
    private JCheckBox areYouSureCheckBox;
    private JLabel idLabel;
    private JLabel titleLabel;
    private JLabel pageCountLabel;
    private JLabel stockLabel;
    private JPanel libraryPanel;
    private DefaultListModel listModel = new DefaultListModel<>();

    public LibraryWindow(){
        jFrame = new JFrame("Library interface.");
        jFrame.setContentPane(libraryPanel);
        jFrame.setSize(1000, 1000);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        bookJList.setModel(listModel);

        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddBookWindow addBookWindow = new AddBookWindow();
            }
        });
    }


}
