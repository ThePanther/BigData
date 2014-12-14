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
                try {
                    System.out.println("trying to connect....");
                    client.connectToServer();
                    System.out.println("connected :)");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                try {
                    System.out.println("trying to login");
                    Response response = client.login("benni", "abc");
                    System.out.println(response.getReason());
                    System.out.println("Done :)");
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }

                //TODO: Startet die LoginGUI
            }
        });
        RegistryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Startet die RegistryGUI
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
