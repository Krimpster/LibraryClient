package com.example.LibraryClient.Panels;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);

        addBookButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String urlParams ="?title=" + URLEncoder.encode(titleTextField.getText(), StandardCharsets.UTF_8)
                            + "&author=" + URLEncoder.encode(pageTextField.getText(), StandardCharsets.UTF_8)
                            + "&pagecount=" + Integer.parseInt(stockTextField.getText());
                    URL url = new URL("http://localhost:8080/book/add" + urlParams);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.connect();
                    int code = connection.getResponseCode();

                    if(code >= 200 && code <= 299){
                        codeLabel.setText("Book was successfully added. Response code: " + code + ".");
                    }
                    else{
                        codeLabel.setText("Book could not be added. Response code: " + code + ".");
                    }

                }
                catch (Exception x){
                    codeLabel.setText("Error " + x);
                }
            }

        });
    }
}
