package com.example.LibraryClient.Panels;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddBookWindow {
    private JFrame jFrame;
    private JTextField titleTextField;
    private JTextField pageTextField;
    private JTextField stockTextField;
    private JButton addBookButton2;
    private JButton backToMainButton;
    private JPanel addPanel;
    private JLabel codeLabel;

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
                try{
                    String urlParams ="?title=" + titleTextField.getText() + "?author="
                            + pageTextField.getText() + "?pagecount=" + Integer.parseInt(stockTextField.getText());
                    URL url = new URL("http://localhost:8080/book/add" + urlParams.replace(" ", "%20"));
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.connect();
                    int code = connection.getResponseCode();

                    if(code >= 200 && code <= 299){
                        codeLabel.setText("Book entry was successfully updated. Response code: " + code + ".");
                    }
                    else{
                        codeLabel.setText("Book could not be updated. Response code: " + code + ".");
                    }

                }
                catch (Exception x){
                    codeLabel.setText("Error " + x);
                }
                jFrame.dispose();
            }

        });
    }
}
