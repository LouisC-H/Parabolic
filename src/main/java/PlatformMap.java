import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class PlatformMap {
    private final int rowCount;
    private final int colCount;
    private final char[][] platformMatrix;
    private char[][] stableMatrix;
    private int stabilityGuess = 10000;
    private int stablePeriodicity = 0;

    enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    public PlatformMap(int rowCount, int colCount) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.platformMatrix = new char[rowCount][colCount];
    }

    public void populateMatrix(String charRow, int rowIndex) {
        for (int i = 0; i < charRow.length(); i++) {
            char c = charRow.charAt(i);
            platformMatrix[rowIndex][i] = c;
        }
    }

    public void tiltNW(Direction direction) {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (platformMatrix[i][j] == 'O'){
                    platformMatrix[i][j] = '.';
                    switch (direction){
                        case NORTH -> rollNorth(i,j);
                        case WEST -> rollWest(i,j);
                    }
                }
            }
        }
    }

    public void tiltSE(Direction direction) {
        for (int i = rowCount -1; i >= 0; i--) {
            for (int j = colCount -1; j >= 0; j--) {
                if (platformMatrix[i][j] == 'O'){
                    platformMatrix[i][j] = '.';
                    switch (direction){
                        case SOUTH -> rollSouth(i,j);
                        case EAST -> rollEast(i,j);
                    }
                }
            }
        }
    }

    private void rollNorth(int i, int j) {
        if (i == 0 || platformMatrix[i - 1][j] != '.'){
            platformMatrix[i][j] = 'O';
        } else {
            rollNorth(i - 1, j);
        }
    }

    private void rollSouth(int i, int j) {
        if (i == rowCount - 1 || platformMatrix[i + 1][j] != '.'){
            platformMatrix[i][j] = 'O';
        } else {
            rollSouth(i + 1, j);
        }
    }

    private void rollEast(int i, int j) {
        if (j == colCount - 1 || platformMatrix[i][j + 1] != '.'){
            platformMatrix[i][j] = 'O';
        } else {
            rollEast(i, j + 1);
        }
    }

    private void rollWest(int i, int j) {
        if (j == 0 || platformMatrix[i][j - 1] != '.'){
            platformMatrix[i][j] = 'O';
        } else {
            rollWest(i, j - 1);
        }
    }

    public void spinCycle(long maxSpin) {
        for (long j = 0; j < maxSpin; j++) {

            if (stablePeriodicity != 0){
                if (maxSpin % stablePeriodicity == j % stablePeriodicity){
                    System.out.println("Returning after "+ j +" spin cycles, with a periodicity of "+ stablePeriodicity);
                    return;
                }
            }

            tiltNW(Direction.NORTH);
            tiltNW(Direction.WEST);
            tiltSE(Direction.SOUTH);
            tiltSE(Direction.EAST);

            if (j == 100007){
                return;
            }
            // Assume that spin cycle has stabilised into a periodic pattern by now
            if (j == stabilityGuess) {
                saveMatrix();
            }

//                check current matrix against the old matrix to establish periodicity
            if (j > stabilityGuess && stablePeriodicity == 0){
                establishPeriodicity(j);
            }
        }
    }

    private void saveMatrix() {
        // save current matrix to old matrix list
        this.stableMatrix = new char[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            System.arraycopy(platformMatrix[i], 0, stableMatrix[i], 0, colCount);
        }
    }

    private void establishPeriodicity(long j) {
        boolean unchanged = true;
        for (int i = 0; i < rowCount; i++) {
            if (!Arrays.equals(platformMatrix[i], stableMatrix[i])){
                unchanged = false;
                break;
            }
        }
        if (unchanged){
            stablePeriodicity = (int) (j - stabilityGuess);
        }
    }

    public int weighNorth() {
        int sumLoad = 0;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (platformMatrix[i][j] == 'O'){
                    sumLoad += rowCount - i;
                }
            }
        }
        return sumLoad;
    }
}
