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

        this.client = new ClientImpl(serverIPTextField.getText(),Integer.parseInt(serverportTextField.getText()),clientIPTextField.getText(),Integer.parseInt(clientportTextField.getText()));

        try {
            client.connectToServer();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginGUI loginGUI = new LoginGUI();
                loginGUI.setClient(client);
                String[] args = new String[0];
                loginGUI.main(args);
            }
        });
        RegistryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistryGUI registryGUI = new RegistryGUI();
                registryGUI.setClient(client);
                String[] args = new String[0];
                registryGUI.main(args);
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
