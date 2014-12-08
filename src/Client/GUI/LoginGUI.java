package Client.GUI;

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
                    //TODO: ClientGUI starten
                }

            }
        });
    }
}
