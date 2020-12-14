package bullscows;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static int turnCounter = 1;
    private static String code;
    private static int codeLength;
    private static int numberOfSymbols;
    private static boolean isGameFinished = false;

    public static void main(String[] args) {
        codeLength = getCodeLength();
        numberOfSymbols = getNumberOfSymbols();
        code = generateCode();
        printCode();
        System.out.println("Okay, let's start a game!");
        while (!isGameFinished) {
            makeTurn();
        }
    }

    private static int getCodeLength() {
        System.out.println("Input the length of the secret code:");
        try {
            codeLength = SCANNER.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: please enter a valid number from 1 to 36.");
            codeLength = getCodeLength();
        }
        if (codeLength < 1 || codeLength > 36) {
            System.out.println("Error: code length should be from 1 to 36 characters.");
            codeLength = getCodeLength();
        }
        return codeLength;
    }

    private static int getNumberOfSymbols() {
        System.out.println("Input the number of possible symbols in the code:");
        try {
            numberOfSymbols = SCANNER.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: please enter a valid number.");
            numberOfSymbols = getNumberOfSymbols();
        }
        if (numberOfSymbols < codeLength) {
            System.out.println("Error: the number of symbols should be greater that the code's length.");
            numberOfSymbols = getNumberOfSymbols();
        }
        if (numberOfSymbols > 36) {
            System.out.println("Error: the maximum number of symbols is 36.");
            numberOfSymbols = getNumberOfSymbols();
        }
        return numberOfSymbols;
    }

    private static String generateCode() {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        while (code.length() < codeLength) {
            int randomNumber = random.nextInt(numberOfSymbols + 1);
            char appendable;
            if (randomNumber < 10) {
                appendable = (char) (randomNumber + '0');
            } else {
                appendable = (char) (randomNumber + 87);
            }
            if (code.toString().indexOf(appendable) >= 0) {
                continue;
            }
            code.append(appendable);
        }
        return code.toString();
    }

    private static void printCode() {
        System.out.print("The secret is prepared: ");
        for (int i = 0; i < codeLength; i++) {
            System.out.print("*");
        }
        System.out.print(" (0");
        if (numberOfSymbols > 1) {
            System.out.print("-");
            if (numberOfSymbols > 9) {
                System.out.print(numberOfSymbols - (numberOfSymbols - 9));
            } else {
                System.out.print(numberOfSymbols - 1);
            }
        }
        if (numberOfSymbols > 9) {
            System.out.print(", a");
        }
        if (numberOfSymbols > 10) {
            System.out.print("-" + (char) (numberOfSymbols + 86));
        }
        System.out.println(").");
    }

    private static void makeTurn() {
        System.out.printf("Turn %d:%n", turnCounter++);
        String answer = SCANNER.next();
        if (answer.length() != codeLength) {
            System.out.println("Error: the length of your answer doesn't match the code's length.");
            return;
        }
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < code.length(); i++) {
            int index = code.indexOf(answer.charAt(i));
            if (index >= 0) {
                if (index == i) {
                    bulls++;
                } else {
                    cows++;
                }
            }
        }
        gradeAnswer(bulls, cows);
    }

    private static void gradeAnswer(int bulls, int cows) {
        System.out.print("Grade: ");
        if (bulls > 0) {
            System.out.printf("%d bull", bulls);
        }
        if (bulls > 1) {
            System.out.print("s");
        }
        if (bulls > 0 && cows > 0) {
            System.out.print(" and ");
        }
        if (cows > 0) {
            System.out.printf("%d cow", cows);
        }
        if (cows > 1) {
            System.out.print("s");
        }
        if (bulls == 0 && cows == 0) {
            System.out.print("None");
        }
        System.out.println();
        if (bulls == code.length()) {
            System.out.println("Congratulations! You guessed the secret code.");
            isGameFinished = true;
        }
    }
}
