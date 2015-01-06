package Analytics.GUI;

import DB.MongoDB;
import DB.Neo4J;
import Data.Registration;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AnalyticsFrame {
    private JPanel AnalyticsFrame;
    private JTextField searchTextField;
    private JButton sucheButton;
    private JTextArea textArea;
    private JComboBox comboBox;
    private JButton freundeButton;
    private JTextArea textArea1;
    private MongoDB mongoDB = new MongoDB();
    private Neo4J neo4J = new Neo4J();



    public AnalyticsFrame() {

        mongoDB.init();
        neo4J.init();

        sucheButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                ArrayList<String> list = mongoDB.messageContained(searchTextField.getText());
                Object[] objects = list.toArray();
                for (int i=0; i < objects.length; i++) {
                    comboBox.addItem(objects[i]);
                }
                for (String s : list) {
                    Registration registration = mongoDB.getUser(s);
                    textArea.append(registration.geteMail()+"\n");
                }
            }
        });
        freundeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText("");
                long id = mongoDB.getUserNodeID(comboBox.getSelectedItem().toString());
                ArrayList<String> list = neo4J.getFriends(id);
                for (String s : list) {
                    textArea1.append(s+"\n");
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("AnalyticsFrame");
        frame.setContentPane(new AnalyticsFrame().AnalyticsFrame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
