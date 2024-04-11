package com.example.LibraryClient.Panels;

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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import com.example.LibraryClient.Objects.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    private JButton refreshButton;
    private JLabel statusLabel;
    private ArrayList<Book> bookList = new ArrayList<>();
    private DefaultListModel listModel = new DefaultListModel<>();

    public UpdateWindow(){
        jFrame = new JFrame("Update books.");
        jFrame.setContentPane(stockPanel);
        jFrame.setSize(900, 700);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        bookJList.setModel(listModel);
        refreshConnection();
        makeChangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int id = bookList.get(bookJList.getSelectedIndex()).getId();
                    String urlParams = "?id=" + id + "&title="
                            + URLEncoder.encode(titleTextField.getText(), StandardCharsets.UTF_8)
                            + "&author=" + URLEncoder.encode(authorTextField.getText(), StandardCharsets.UTF_8)
                            + "&pageCount=" + URLEncoder.encode(pageTextField.getText(), StandardCharsets.UTF_8)
                            + "&loaned=" + loanedTextField.getText()
                            + "&loanee=" + URLEncoder.encode(loaneeTextField.getText(), StandardCharsets.UTF_8);

                    URL url = new URL("http://localhost:8080/book/updateBook" +
                            urlParams.replace(" ", "%20"));
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
                refreshConnection();
            }
        });
        bookJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                idLabel.setText(Integer.toString(bookList.get(bookJList.getSelectedIndex()).getId()));
                titleTextField.setText(bookList.get(bookJList.getSelectedIndex()).getTitle());
                authorTextField.setText(bookList.get(bookJList.getSelectedIndex()).getAuthor());
                pageTextField.setText(Integer.toString(bookList.get(bookJList.getSelectedIndex()).getPageCount()));
                if (bookList.get(bookJList.getSelectedIndex()).getLoaned()){
                    loanedTextField.setText("true");
                }
                else {
                    loanedTextField.setText("false");
                }
                loaneeTextField.setText(bookList.get(bookJList.getSelectedIndex()).getLoanee());
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshConnection();
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
                statusLabel.setText("Not connected. Code: " + code);
            }
        } catch (Exception x) {
            codeLabel.setText("Something failed. Exception " + x);
        }

        refreshList(bookList);
    }
    public void refreshList(ArrayList<Book> bookList){
        listModel.removeAllElements();
        for(Book book : bookList){
            listModel.addElement(book.fullToString());
        }
    }
}
