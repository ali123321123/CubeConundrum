package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.CubeConundrum.inputPerLine;
import static org.example.CubeConundrum.runThroughGames;

 class CubeConundrum {

    static final int RED_INDEX = 0;
    static final int GREEN_INDEX = 1;
    static final int BLUE_INDEX = 2;

    static final int RED_CUBES = 12;
    static final int GREEN_CUBES = 13;
    static final int BLUE_CUBES = 14;

    public static void main(String[] args) {
        String filePath = "C:\\testutvikling\\Cubes\\src\\main\\java\\org\\example\\gamesSet.txt";
        List<String> gamesList = inputPerLine(filePath);

        int sumOfValidGames = runThroughGames(gamesList);
        System.out.println("The sum of valid games is: " + sumOfValidGames);

        int sumOfProducts = powerSums(gamesList);
        System.out.println("The sum of the power of the sets is: " + sumOfProducts);
    }

    static List<String> inputPerLine(String filePath) {
        List<String> games = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                games.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return games;
    }

    private static boolean validateColor(String line, Pattern pattern, int maxCubes) {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            int value = Integer.parseInt(matcher.group(1));
            if (value > maxCubes) {
                return false;
            }
        }
        return true;
    }

    private static boolean determineValidGame(String line) {
        Pattern findBlue = Pattern.compile("(\\d+) blue");
        Pattern findRed = Pattern.compile("(\\d+) red");
        Pattern findGreen = Pattern.compile("(\\d+) green");

        return validateColor(line, findBlue, BLUE_CUBES) &&
                validateColor(line, findRed, RED_CUBES) &&
                validateColor(line, findGreen, GREEN_CUBES);
    }

    static int runThroughGames(List<String> games) {
        int sumOfValidGames = 0;
        for (int i = 0; i < games.size(); i++) {
            if (determineValidGame(games.get(i))) {
                sumOfValidGames += (i + 1); // Add 1 because game IDs start from 1
            }
        }
        return sumOfValidGames;
    }

    private static List<Integer> findMinimumCubes(String game) {
        Pattern findBlue = Pattern.compile("(\\d+) blue");
        Pattern findRed = Pattern.compile("(\\d+) red");
        Pattern findGreen = Pattern.compile("(\\d+) green");

        List<Integer> redValues = new ArrayList<>();
        List<Integer> blueValues = new ArrayList<>();
        List<Integer> greenValues = new ArrayList<>();

        Matcher redMatcher = findRed.matcher(game);
        while (redMatcher.find()) {
            redValues.add(Integer.parseInt(redMatcher.group(1)));
        }

        Matcher blueMatcher = findBlue.matcher(game);
        while (blueMatcher.find()) {
            blueValues.add(Integer.parseInt(blueMatcher.group(1)));
        }

        Matcher greenMatcher = findGreen.matcher(game);
        while (greenMatcher.find()) {
            greenValues.add(Integer.parseInt(greenMatcher.group(1)));
        }

       return Arrays.asList(max(redValues), max(greenValues), max(blueValues));

    }

    static int powerSums(List<String> games) {
        int sumOfProducts = 0;
        for (String game : games) {
            List<Integer> minimumCubes = findMinimumCubes(game);
            int product = minimumCubes.stream().reduce(1, (a, b) -> a * b);
            sumOfProducts += product;
        }
        return sumOfProducts;
    }

    private static int max(List<Integer> values) {
        return values.isEmpty() ? 0 : values.stream().mapToInt(Integer::intValue).max().orElse(0);
    }
}

public class Main {
    public static void main(String[] args) {
        List<String> gamesList = inputPerLine("path/to/your/input.txt");

        int sumOfValidGames = runThroughGames(gamesList);
        System.out.println("The sum of valid games is: " + sumOfValidGames);

        int sumOfProducts = CubeConundrum.powerSums(gamesList);
        System.out.println("The sum of the power of the sets is: " + sumOfProducts);
    }
}

