package com.example.LibraryClient.Panels;

import com.google.gson.Gson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JLabel statusLabel;
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

        refreshConnection();

        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddBookWindow addBookWindow = new AddBookWindow();
            }
        });
        addStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateWindow stockWindow = new UpdateWindow();
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
                    int code = connection.getResponseCode();

                    if(code >= 200 && code <= 299){
                        codeLabel.setText("Book entry was successfully removed. Response code: " + code + ".");
                    }
                    else{
                        codeLabel.setText("Book could not be removed. Response code: " + code + ".");
                    }
                }
                catch (Exception x){
                    codeLabel.setText("Something failed. Exception " + x);
                }
                refreshConnection();
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshConnection();
            }
        });
        bookJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int index = bookJList.getSelectedIndex();
                idLabel.setText("ISBN: " + Integer.toString(bookList.get(index).getId()));
                titleLabel.setText("Title: " + bookList.get(index).getTitle());
                authorLabel.setText("Author: " + bookList.get(index).getAuthor());
                pageLabel.setText("Page count: " + Integer.toString(bookList.get(index).getPageCount()));

                if(bookList.get(index).getLoaned()){
                    loanedLabel.setText("Loaned: Loaned");
                }
                else{
                    loanedLabel.setText("Loaned: Not loaned");
                }

                if (bookList.get(index).getLoanee() == null){
                    loaneeLabel.setText("Loaner: Not loaned");
                }
                else{
                    loaneeLabel.setText("Loaner: " + bookList.get(index).getLoanee());
                }
            }
        });
    }
    public void refreshConnection(){
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
                statusLabel.setText("Connected. Code: " + code);
            }
            else{
                statusLabel.setText("No connection. Code: " + code);
            }
        } catch (Exception x) {
            codeLabel.setText("Something failed. Exception " + x);
        }

        refreshList(bookList);
    }
    public void refreshList(ArrayList<Book> bookList){
        listModel.removeAllElements();
        for(Book book : bookList){
            listModel.addElement(book.toString());
        }
    }
}
