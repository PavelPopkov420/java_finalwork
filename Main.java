import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    private static int fieldLineCount;
    private static int fieldColumnCount;
    private static int[][] field;
    private static int[][] answerField;

    /**
     * @param args
     */
    public static void main(String[] args) {
        String path = "C:\\Users\\Пользователь\\fanil work\\input.txt";
        try {
            List<String> strings = Files.readAllLines(Path.of(path));

            String[] sizes = strings.get(0).split(" ");
            fieldLineCount = Integer.parseInt(sizes[0]); // 3
            fieldColumnCount = Integer.parseInt(sizes[1]); // 3

            field = new int[fieldLineCount][fieldColumnCount];
            answerField = new int[fieldLineCount][fieldColumnCount];
            for (int i = 0; i < fieldLineCount; i++) {
                for (int j = 0; j < fieldColumnCount; j++) {
                    answerField[i][j] = Integer.MAX_VALUE;
                }
            }

            String[] startCoordinates = strings.get(1).split(" ");
            int startY = Integer.parseInt(startCoordinates[0]);
            int startX = Integer.parseInt(startCoordinates[1]);

            String[] finishCoordinates = strings.get(2).split(" ");
            int finishY = Integer.parseInt(finishCoordinates[0]);
            int finishX = Integer.parseInt(finishCoordinates[1]);

            answerField[startY][startX] = 0;

            for (int lineNumber = 0; lineNumber < fieldLineCount; lineNumber++) {
                String[] cells = strings.get(lineNumber + 3).split(" ");
                for (int columnNumber = 0; columnNumber < fieldColumnCount; columnNumber++) {
                    field[lineNumber][columnNumber] = Integer.parseInt(cells[columnNumber]);
                }
            }

            waveAlgo(startY, startX);

            for (int i = 0; i < fieldLineCount; i++) {
                System.out.println(Arrays.toString(answerField[i]));
            }

            System.out.println(findWay(finishY, finishX, startY, startX));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void waveAlgo(int lineNumber, int columnNumber) {
        if (columnNumber > 0 && field[lineNumber][columnNumber - 1] != 1 &&
                answerField[lineNumber][columnNumber - 1] > answerField[lineNumber][columnNumber] + 1) {
            answerField[lineNumber][columnNumber - 1] = answerField[lineNumber][columnNumber] + 1;
            waveAlgo(lineNumber, columnNumber - 1);
        }

        if (columnNumber < fieldColumnCount - 1 && field[lineNumber][columnNumber + 1] != 1 &&
                answerField[lineNumber][columnNumber + 1] > answerField[lineNumber][columnNumber] + 1) {
            answerField[lineNumber][columnNumber + 1] = answerField[lineNumber][columnNumber] + 1;
            waveAlgo(lineNumber, columnNumber + 1);
        }

        if (lineNumber > 0 && field[lineNumber - 1][columnNumber] != 1 &&
                answerField[lineNumber - 1][columnNumber] > answerField[lineNumber][columnNumber] + 1) {
            answerField[lineNumber - 1][columnNumber] = answerField[lineNumber][columnNumber] + 1;
            waveAlgo(lineNumber - 1, columnNumber);
        }

        if (lineNumber < fieldLineCount - 1 && field[lineNumber + 1][columnNumber] != 1 &&
                answerField[lineNumber + 1][columnNumber] > answerField[lineNumber][columnNumber] + 1) {
            answerField[lineNumber + 1][columnNumber] = answerField[lineNumber][columnNumber] + 1;
            waveAlgo(lineNumber + 1, columnNumber);
        }
    }

    private static ArrayList<Pair> findWay(int coordinateY, int coordinateX, int startY, int startX) {
        ArrayList<Pair> way = new ArrayList<>();
        way.add(new Pair(coordinateX, coordinateY));

        while (coordinateY != startY || coordinateX != startX) {
            int currentLen = answerField[coordinateY][coordinateX];

            if (coordinateY > 0 && answerField[coordinateY - 1][coordinateX] == currentLen - 1) {
                coordinateY--;
                way.add(new Pair(coordinateX, coordinateY));
            } else if (coordinateY < fieldColumnCount - 1
                    && answerField[coordinateY + 1][coordinateX] == currentLen - 1) {
                coordinateY++;
                way.add(new Pair(coordinateX, coordinateY));
            } else if (coordinateX > 0 && answerField[coordinateY][coordinateX - 1] == currentLen - 1) {
                coordinateX--;
                way.add(new Pair(coordinateX, coordinateY));
            } else if (coordinateX < fieldLineCount && answerField[coordinateY][coordinateX + 1] == currentLen - 1) {
                coordinateX++;
                way.add(new Pair(coordinateX, coordinateY));
            }
        }

        Collections.reverse(way);
        return way;
    }
}

class Pair {
    public int x;
    public int y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}