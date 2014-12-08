package Client.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Hero
 * Date: 08.12.14
 * Time: 19:27
 */
public class StartGUI {
    private JPanel StartPanel;
    private JButton LoginButton;
    private JButton RegistryButton;

    public StartGUI() {
        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
}
