import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {

        String filename = "src/main/resources/input.txt";
//        System.out.println(runDay14CodeP1(filename));
        System.out.println(runDay14CodeP2(filename, 1000000000));
    }

    public static int runDay14CodeP1(String filename){

        File inputFile = new File(filename);

        PlatformMap platformMap = initialisePlatformMap(inputFile);

        populatePlatformMap(inputFile, platformMap);

        platformMap.tiltNW(PlatformMap.Direction.NORTH);

        return platformMap.weighNorth();
    }

    private static PlatformMap initialisePlatformMap(File inputFile) {

        int rowCount = 0, charCount = 0;

        try {
            Scanner lineCounter = new Scanner(inputFile);

            charCount = lineCounter.nextLine().length();
            rowCount ++;

            while (lineCounter.hasNextLine()) {
                rowCount++;
                lineCounter.nextLine();
            }
            lineCounter.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return new PlatformMap (rowCount, charCount);
    }

    private static void populatePlatformMap(File inputFile, PlatformMap platformMap) {

        try {
            Scanner myReader = new Scanner(inputFile);
            int rowCount = 0;

            while (myReader.hasNextLine()) {
                String charString = myReader.nextLine();
                platformMap.populateMatrix(charString, rowCount);
                rowCount++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static int runDay14CodeP2(String filename, long numSpins) {
        File inputFile = new File(filename);

        PlatformMap platformMap = initialisePlatformMap(inputFile);

        populatePlatformMap(inputFile, platformMap);

        platformMap.spinCycle(numSpins);

        return platformMap.weighNorth();
    }
}
