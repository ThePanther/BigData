package Client.GUI;

import Client.Implementation.ClientImpl;
import Client.Implementation.MessageReceiver;
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
 * Time: 21:00
 * To change this template use File | Settings | File Templates.
 */
public class ChatGUI {
    public JPanel ChatPanel;
    private JTextArea HistoryTextarea;
    private JTextArea MessageTextarea;
    private JButton SubmitButton;
    private JComboBox toUserComboBox;
    private ClientImpl client;
    private ArrayList<String> contacts;

    public ChatGUI() {

        HistoryTextarea.setText("");

        contacts = new ArrayList<String>();

        SubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Response response = client.sendMessage((String) toUserComboBox.getSelectedItem(), MessageTextarea.getText());
                    System.out.println(response.getReason());
                    if (response.getState()) {
                        addText(client.getUserName(),MessageTextarea.getText());
                    }
                    MessageTextarea.setText("");
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
        MessageReceiver messageReceiver = new MessageReceiver(client.getClientPort(),this);
        messageReceiver.start();
    }

    public void addText(String from, String text) {
        HistoryTextarea.append("[" + from + "]: " + text + "\n");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ChatGUI");
        frame.setContentPane(new ChatGUI().ChatPanel);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void addUser(String toUser) {
        toUserComboBox.addItem(toUser);
        contacts.add(toUser);

    }

    public ArrayList<String> getContacts() {
        return contacts;
    }
}
