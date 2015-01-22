import TestData.TestDataCreator;

public class Main {

    public static void main(String[] args) {

        TestDataCreator testDataCreator = new TestDataCreator();
        testDataCreator.putIntoList("9Gag");
        testDataCreator.putIntoList("HAW");
        testDataCreator.putIntoList("Cola");
        testDataCreator.putIntoList("BigData");
        testDataCreator.create(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]));

    }

}
