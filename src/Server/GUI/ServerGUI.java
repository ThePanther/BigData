package Server.GUI;

import Server.Implementation.ServerImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ServerGUI {
    private JPanel ServerPanel;
    private JButton StartButton;
    private JButton StopButton;
    private JTextArea ConsoleTextArea;
    private JSeparator ServerSeperator;
    private JTextField ServerPortTextField;
    private JLabel ServerPortLabel;

    private ServerImpl server = new ServerImpl(50000);

    public ServerGUI() {
        StartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.setServiceRequested(true);
                server.start();
            }
        });
        StopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.setServiceRequested(false);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ServerGUI");
        frame.setContentPane(new ServerGUI().ServerPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
