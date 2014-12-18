package Client.GUI;

import Client.Implementation.ClientImpl;
import Data.Response;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Hero
 * Date: 08.12.14
 * Time: 19:31
 * To change this template use File | Settings | File Templates.
 */
public class ClientGUI {
    private JPanel ClientPanel;
    private JTextField UsernameTextField;
    private JLabel UsernameLabel;
    private JButton SearchButton;
    private JComboBox ContactComboBox;
    private JLabel ContactLabel;
    private JButton ChatButton;
    private JButton DisconnectButton;
    private ClientImpl client;
    private ArrayList<String> contacts;

    public ClientGUI() {

        this.contacts = new ArrayList<String>();

        SearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Response response = client.searchUser(UsernameTextField.getText());
                    System.out.println(response.getReason());
                    if (response.getState()) {
                        addUser(UsernameTextField.getText());
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        ChatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String toUser = (String) ContactComboBox.getSelectedItem();
                ChatGUI chatGUI = new ChatGUI();
                chatGUI.addUser(toUser);
                chatGUI.setClient(client);
                String[] args = new String[0];
                chatGUI.main(args);
            }
        });

        DisconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Response response = client.logout(client.getUserName());
                    System.out.println(response.getReason());
                    if (response.getState()) {
                        client.disconnectFromServer();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }



    public void setClient(ClientImpl client) {
        this.client = client;
    }

    public void addUser(String user) {
        ContactComboBox.addItem(user);
        contacts.add(user);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ClientGUI");
        frame.setContentPane(new ClientGUI().ClientPanel);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
