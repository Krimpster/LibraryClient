package com.example.LibraryClient.Panels;

import com.google.gson.Gson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import com.example.LibraryClient.Objects.Book;
import com.google.gson.reflect.TypeToken;


public class LibraryWindow {
    private JFrame jFrame;
    private JList bookJList;
    private JButton addBookButton;
    private JButton addStockButton;
    private JButton deleteBookButton;
    private JLabel idLabel;
    private JLabel titleLabel;
    private JLabel authorLabel;
    private JLabel pageLabel;
    private JPanel libraryPanel;
    private JLabel loanedLabel;
    private JLabel loaneeLabel;
    private JLabel codeLabel;
    private JButton refreshButton;
    private ArrayList<Book> bookList = new ArrayList<>();
    private DefaultListModel listModel = new DefaultListModel<>();

    public LibraryWindow(){
        jFrame = new JFrame("Library interface.");
        jFrame.setContentPane(libraryPanel);
        jFrame.setSize(1000, 1000);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        bookJList.setModel(listModel);

        try {
            URL url = new URL("http://localhost:8080/book");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();

            if(code >= 200 && code <= 299){
                InputStream stream = connection.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(stream);
                BufferedReader br = new BufferedReader(streamReader);

                String message = "";
                String readMessage = br.readLine();

                while (readMessage != null){
                    message += readMessage;
                    readMessage = br.readLine();
                }

                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Book>>() {}.getType();
                bookList = gson.fromJson(message, listType);
                codeLabel.setText("Database entries fetched. Response code: " + code);
            }
            else{
                codeLabel.setText("Could not fetch database entries. Response code: " + code);
            }
        } catch (Exception e) {
            codeLabel.setText("Something failed. Exception " + e);
        }

        refreshList(bookList);

        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddBookWindow addBookWindow = new AddBookWindow();
            }
        });
        addStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateWindow stockWindow = new UpdateWindow(bookList);
            }
        });
        deleteBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int urlParams = bookList.get(bookJList.getSelectedIndex()).getId();
                    URL url = new URL("http://localhost:8080/book/delete?id=" + urlParams);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("DELETE");
                    connection.connect();
                    System.out.println(urlParams);
                }
                catch (Exception x){
                    codeLabel.setText("Something failed. Exception " + x);
                }

            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    URL url = new URL("http://localhost:8080/book");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    int code = connection.getResponseCode();

                    if(code >= 200 && code <= 299){
                        InputStream stream = connection.getInputStream();
                        InputStreamReader streamReader = new InputStreamReader(stream);
                        BufferedReader br = new BufferedReader(streamReader);

                        String message = "";
                        String readMessage = br.readLine();

                        while (readMessage != null){
                            message += readMessage;
                            readMessage = br.readLine();
                        }

                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<Book>>() {}.getType();
                        bookList = gson.fromJson(message, listType);
                        codeLabel.setText("Database entries fetched. Response code: " + code);
                    }
                    else{
                        codeLabel.setText("Could not fetch database entries. Response code: " + code);
                    }
                } catch (Exception x) {
                    codeLabel.setText("Something failed. Exception " + x);
                }

                refreshList(bookList);
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
