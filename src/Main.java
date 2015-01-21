import TestData.TestDataCreator;

public class Main {

    public static void main(String[] args) {

        TestDataCreator testDataCreator = new TestDataCreator();
        testDataCreator.putIntoList("HAW");
        testDataCreator.putIntoList("Cola");
        testDataCreator.putIntoList("BigData");
        testDataCreator.create(5,10000,100);

    }

}
