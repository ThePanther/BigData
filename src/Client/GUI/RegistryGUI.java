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
 * Time: 19:30
  */
public class RegistryGUI {
    private JPanel RegistryPanel;
    private JLabel UsernameLabel;
    private JLabel EmailLabel;
    private JLabel AdressLabel;
    private JLabel GenderLabel;
    private JLabel BirthdayLabel;
    private JLabel JobLabel;
    private JLabel PasswordLabel1;
    private JTextField UsernameTextField;
    private JTextField EmailTextField;
    private JTextArea AddresTextArea;
    private JTextField PasswordTextField1;
    private JTextField PasswordTextField2;
    private JLabel PasswordLabel2;
    private JButton RegistryButton;
    private JTextField GenderTextField;
    private JTextField BirthdayTextField;
    private JTextField JobTextField;
    private ClientImpl client;

    public RegistryGUI() {
        RegistryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(UsernameTextField.getText().isEmpty() || EmailTextField.getText().isEmpty() || AddresTextArea.getText().isEmpty() ||
                        GenderTextField.getText().isEmpty() || BirthdayTextField.getText().isEmpty() || JobTextField.getText().isEmpty() ||
                        PasswordTextField1.getText().isEmpty() || PasswordTextField2.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Bitte alle Felder ausfüllen!");
                }
                else if(PasswordTextField1.getText().compareTo(PasswordTextField2.getText()) != 0)
                {
                    //Wenn das wiederholte Passwort nicht identisch ist
                    JOptionPane.showMessageDialog(null, "Passwort stimmt nicht überein!");
                }
                else
                {
                    try {
                        Response response = client.register(EmailTextField.getText(), UsernameTextField.getText(), PasswordTextField1.getText(), BirthdayTextField.getText(), GenderTextField.getText(), AddresTextArea.getText(), JobTextField.getText());
                        System.out.println(response.getReason());
                        if (response.getState()) {
                            ClientGUI clientGUI = new ClientGUI();
                            clientGUI.setClient(client);
                            String[] args = new String[0];
                            clientGUI.main(args);
                        } else {
                            //TODO: user already in DB
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
        JFrame frame = new JFrame("RegistryGUI");
        frame.setContentPane(new RegistryGUI().RegistryPanel);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
