package Client.GUI;

import Client.Implementation.ClientImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Hero
 * Date: 08.12.14
 * Time: 21:00
 * To change this template use File | Settings | File Templates.
 */
public class ChatGUI {
    private JPanel ChatPanel;
    private JTextArea HistoryTextarea;
    private JTextArea MessageTextarea;
    private JButton SubmitButton;
    private ClientImpl client;

    public ChatGUI() {
        SubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void setClient(ClientImpl client) {
        this.client = client;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ChatGUI");
        frame.setContentPane(new ChatGUI().ChatPanel);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
