package com.example.LibraryClient.Panels;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookWindow {
    private JFrame jFrame;
    private JTextField idTextField;
    private JTextField titleTextField;
    private JTextField pageTextField;
    private JTextField stockTextField;
    private JButton addBookButton2;
    private JButton backToMainButton;
    private JPanel addPanel;

    public AddBookWindow(){
        jFrame = new JFrame("Add books.");
        jFrame.setContentPane(addPanel);
        jFrame.setSize(400, 300);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);

        addBookButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
            }
        });
    }
}
