package Client.GUI;

import Client.Implementation.ClientImpl;
import Data.Response;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StartGUI {
    private JPanel StartPanel;
    private JButton LoginButton;
    private JButton RegistryButton;

    ClientImpl client = new ClientImpl("localhost",50000,50001);

    public StartGUI() {
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
