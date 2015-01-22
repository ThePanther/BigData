package Client.GUI;

import Client.Implementation.ClientImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StartGUI {
    private JPanel StartPanel;
    private JButton LoginButton;
    private JButton RegistryButton;
    private JTextField serverIPTextField;
    private JTextField clientIPTextField;
    private JTextField serverportTextField;
    private JTextField clientportTextField;
    private ClientImpl client;

    public StartGUI() {



        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client = new ClientImpl(serverIPTextField.getText(),Integer.parseInt(serverportTextField.getText()),clientIPTextField.getText(),Integer.parseInt(clientportTextField.getText()));

                try {
                    client.connectToServer();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                LoginGUI loginGUI = new LoginGUI();
                loginGUI.setClient(client);
                JFrame frame = new JFrame("LoginGUI");
                frame.setContentPane(loginGUI.LoginPanel);
                frame.pack();
                frame.setVisible(true);
            }
        });
        RegistryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client = new ClientImpl(serverIPTextField.getText(),Integer.parseInt(serverportTextField.getText()),clientIPTextField.getText(),Integer.parseInt(clientportTextField.getText()));

                try {
                    client.connectToServer();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                RegistryGUI registryGUI = new RegistryGUI();
                registryGUI.setClient(client);
                JFrame frame = new JFrame("RegistryGUI");
                frame.setContentPane(registryGUI.RegistryPanel);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("StartGUI");
        frame.setContentPane(new StartGUI().StartPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
