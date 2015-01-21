package TestData;

import DB.MongoDB;
import DB.Neo4J;
import Data.Message;
import Data.Registration;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.Random;

public class TestDataCreator {

    private ArrayList<String> list;
    private Neo4J neo4J;
    private MongoDB mongoDB;

    public TestDataCreator() {
        this.list = new ArrayList<>();
        this.neo4J = new Neo4J();
        this.neo4J.init();
        this.mongoDB = new MongoDB();
        this.mongoDB.init();
    }

    public void create(int low, int high, int msg) {
        Random generator = new Random();
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
            for (int j = 0; j<trendArray.length;j++) {
                String trend = trendArray[j].getName();
                putIntoList(trend);
            }
            for (int i = low; i <= high; i++) {
                Registration registration = new Registration("testUser"+i+"@test.de","testUser"+i,"123","127.0.0.1",50001);
                int gender = generator.nextInt(2);
                if (gender<1) {
                    registration.setSex("w");
                } else {
                    registration.setSex("m");
                }
                int day = generator.nextInt(28)+1;
                int month = generator.nextInt(12)+1;
                int year = generator.nextInt(31)+1970;

                if (month<10) {
                    registration.setBirthDate(day+".0"+month+"."+year);
                } else {
                    registration.setBirthDate(day+"."+month+"."+year);
                }
                mongoDB.register(registration,neo4J.createUser(registration.getUserName()));
            }
            for (int l = low; l <= high; l++) {
                for (int k = 0; k<=msg;k++) {
                    int to = generator.nextInt(high)+low;
                    String text = this.getList().get(generator.nextInt(list.size()));
                    Message message = new Message("testUser"+l,"testUser"+to,text);
                    neo4J.createCommunication(mongoDB.getUserNodeID(message.getFromUser()),mongoDB.getUserNodeID(message.getToUser()));
                    neo4J.saveMessage(mongoDB.getUserNodeID(message.getFromUser()),message);
                    mongoDB.saveMessage(message);
                }
            }

        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void putIntoList(String string) {
        this.list.add(string);
    }
}
