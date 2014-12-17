package Client.GUI;

import Client.Implementation.ClientImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public ClientGUI() {
        SearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        ChatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChatGUI chatGUI = new ChatGUI();
                chatGUI.setClient(client);
                String[] args = new String[0];
                chatGUI.main(args);
            }
        });

        DisconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }



    public void setClient(ClientImpl client) {
        this.client = client;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ClientGUI");
        frame.setContentPane(new ClientGUI().ClientPanel);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
