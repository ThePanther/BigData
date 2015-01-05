package Client.GUI;

import Client.Implementation.ClientImpl;
import Data.Response;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
                    try {
                        Response response = client.login(UserTextfield.getText(),PasswortTextfield.getText());
                        System.out.println(response.getReason());
                        if (response.getState()) {
                            ClientGUI clientGUI = new ClientGUI();
                            clientGUI.setClient(client);
                            String[] args = new String[0];
                            clientGUI.main(args);
                        } else {
                            JOptionPane.showMessageDialog(LoginPanel,
                                    "Benutzername oder Passwort falsch!",
                                    "Login Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
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
