package Analytics.GUI;

import DB.MongoDB;
import DB.Neo4J;
import Data.Registration;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnalyticsFrame {
    private JPanel AnalyticsFrame;
    private JTextField searchTextField;
    private JButton sucheButton;
    private JTextArea mailTextArea;
    private JComboBox comboBox;
    private JButton freundeButton;
    private JTextArea friendsTextArea;
    private JComboBox trendingComboBox;
    private JButton trendingButton;
    private JTextArea statisticTextArea;
    private MongoDB mongoDB = new MongoDB();
    private Neo4J neo4J = new Neo4J();



    public AnalyticsFrame() {

        mongoDB.init();
        neo4J.init();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("EmIsOVYdiobomjrLrwKv3wWdx")
                .setOAuthConsumerSecret("dNvVNJib24XOZxgVgTaqwxctE3hHBKzEHPzC3ZIyuDsAbgzXEt")
                .setOAuthAccessToken("1029077160-kikoix5LKcDuT0eknUC3Z381g0PLFv95AFqUAc7")
                .setOAuthAccessTokenSecret("zJHkoi1ahrhj9qfK9b71ZgS6TD57XgDyVoEOlErPgzZLU");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        try {
            Trends trends = twitter.trends().getPlaceTrends(1);
            Trend[] trendArray = trends.getTrends();
            for (int i = 0; i<trendArray.length;i++) {
                trendingComboBox.addItem(trendArray[i].getName());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        sucheButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search(searchTextField.getText());
            }
        });
        freundeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                friendsTextArea.setText("");
                long id = mongoDB.getUserNodeID(comboBox.getSelectedItem().toString());
                ArrayList<String> list = neo4J.getFriends(id);
                for (String s : list) {
                    friendsTextArea.append(s+"\n");
                }
            }
        });
        trendingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search(trendingComboBox.getSelectedItem().toString());
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

    private void search(String string) {
        mailTextArea.setText("");
        statisticTextArea.setText("");
        Map<String,Integer> statistic = new HashMap<>();
        statistic.put("m",0);
        statistic.put("w",0);
        statistic.put("unbekannt",0);
        ArrayList<Integer> age = new ArrayList<>();
        ArrayList<String> list = mongoDB.messageContained(searchTextField.getText());
        Object[] objects = list.toArray();
        for (int i=0; i < objects.length; i++) {
            comboBox.addItem(objects[i]);
        }
        for (String s : list) {
            Registration registration = mongoDB.getUser(s);
            //E-Mails
            mailTextArea.append(registration.geteMail()+"\n");
            //Statistik
            age.add(getAge(getDate(registration.getBirthDate())));
            if (registration.getSex().equals("m")) {
                statistic.put("m",statistic.get("m")+1);
            } else if (registration.getSex().equals("w")){
                statistic.put("w",statistic.get("w")+1);
            } else {
                statistic.put("unbekannt",statistic.get("unbekannt")+1);
            }

            statisticTextArea.append("Gefundene Benutzer: "+list.size()+"\n");
            statisticTextArea.append("Durchschnitts Alter: "+getAvAge(age)+"\n");
            statisticTextArea.append("Männlich: "+statistic.get("m")+"\n");
            statisticTextArea.append("Weiblich: "+statistic.get("w")+"\n");
            statisticTextArea.append("Unbekannt: "+statistic.get("unbekannt")+"\n");
        }
    }

    private DateTime getDate(String string) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd.MM.yyyy");
        DateTime dt = DateTime.parse(string,fmt);
        return dt;
    }

    private int getAge(DateTime dateTime) {
        DateTime today = DateTime.now();
        Period period = new Period(dateTime,today);
        return period.getYears();
    }

    private int getAvAge(ArrayList<Integer> arrayList) {
        int anzahl = arrayList.size();
        int gesammt = 0;
        for (int i = 0;i<arrayList.size();i++) {
            gesammt+=arrayList.get(i);
        }
        int avAge = gesammt/anzahl;
        return avAge;
    }

}
