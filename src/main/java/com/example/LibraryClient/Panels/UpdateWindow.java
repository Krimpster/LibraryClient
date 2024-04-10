package com.example.LibraryClient.Panels;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import com.example.LibraryClient.Objects.Book;

public class UpdateWindow {
    private JFrame jFrame;
    private JPanel stockPanel;
    private JList bookJList;
    private JTextField authorTextField;
    private JButton addToStockButton;
    private JLabel currentStockLabel;
    private JTextField titleTextField;
    private JTextField pageTextField;
    private JTextField loanedTextField;
    private JTextField loaneeTextField;
    private JLabel idLabel;
    private JButton makeChangeButton;
    private JLabel codeLabel;
    DefaultListModel listModel = new DefaultListModel<>();

    public UpdateWindow(ArrayList<Book> bookList){
        jFrame = new JFrame("Add books.");
        jFrame.setContentPane(stockPanel);
        jFrame.setSize(400, 300);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        refreshList(bookList);
        makeChangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int id = bookList.get(bookJList.getSelectedIndex()).getId();
                    String urlParams = "?id=" + id + "?title=" + titleTextField.getText() + "?author="
                            + authorTextField.getText() + "?pagecount=" + pageTextField.getText() + "?loaned="
                            + loanedTextField.getText() + "?loanee=" + loaneeTextField.getText();

                    URL url = new URL("http://localhost:8080/book/updateBook" + urlParams);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("PUT");
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
                    System.out.println("Error " + x);
                }
                jFrame.dispose();
            }
        });
    }
    public void refreshList(ArrayList<Book> bookList){
        listModel.removeAllElements();
        for(Book book : bookList){
            listModel.addElement(book.toString());
        }
    }
}
