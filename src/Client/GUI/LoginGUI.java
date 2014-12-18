package Client.GUI;

import Client.Implementation.ClientImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Hero
 * Date: 08.12.14
 * Time: 19:29
 */
public class LoginGUI {
    private JPanel LoginPanel;
    private JLabel UsernameLabel;
    private JTextField UserTextfield;
    private JLabel PasswordLabel;
    private JTextField PasswortTextfield;
    private JButton LoginButton;
    private JTextField ServerIPTextField;
    private JTextField ClientIPTextField;
    private JTextField ServerportTextField;
    private JTextField ClientportTextField;
    private JLabel ServerIPLabel;
    private JLabel ClientIPLabel;
    private JLabel ServerportLabel;
    private JLabel ClientportLabel;
    private ClientImpl client;

    public LoginGUI() {

        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(UserTextfield.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Bitte Benutzernamen angeben!");
                }
                else if(PasswortTextfield.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null,"Bitte Passwort angeben!");
                }
                else{
                    ClientGUI clientGUI = new ClientGUI();
                    clientGUI.setClient(client);
                    String[] args = new String[0];
                    clientGUI.main(args);
                }

            }
        });
    }

    public void setClient(ClientImpl client) {
        this.client = client;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LoginGUI");
        frame.setContentPane(new LoginGUI().LoginPanel);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
