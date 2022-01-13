package edu.kit.informatik;

import edu.kit.informatik.entity.DiceFace;
import edu.kit.informatik.exception.SemanticsException;

import java.util.Scanner;
import edu.kit.informatik.ui.Command;

public class Main {
    public static final String ERROR = "Error, ";
    public static final String QUIT = "quit";
    public static final String PRINT = "print";
    public static final String NEW_LINE = "\n";
    public static final int CUTTING_INDEX = 1;
    public static final String ROLL = "roll";
    public static final String SPACE = " ";
    public static final int FIRST_PARAMETER_INDEX = 1;
    private static final int NUM_OF_PLAYER = 2;
    private static final String NUM = "[0-9]+";
    public static final String PIPE = "|";
    public static final String OK = "OK";
    public static final String TURN = "turn";
    public static final int NUM_OF_ROLLS = 6;
    private static final String PLAYER_NAME_PATTERN = "^[^;| ]*$";
    public static final String SEMICOLON = ";";
    public static final int SHIFTING_INDEX = 1;
    public static String ROLL_INPUT_PATTERN() {
        StringBuilder pattern = new StringBuilder();
        for (int i = 0; i < NUM_OF_ROLLS; i++) {
            pattern.append(SEMICOLON).append(DiceFace.getRegex());
        }
        return pattern.substring(CUTTING_INDEX);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OrlogGame game = null;
        try {
            game = getGame(args);
        } catch (SemanticsException exception) {
            System.out.println(ERROR + exception.getMessage());
        }

        assert game != null;
        while(game.isRunning()) {
            String input = scanner.nextLine();
            String output;
            try {
                output = Command.executeCommand(input, game);
            } catch (SemanticsException exception) {
                System.out.println(ERROR + exception.getMessage());
                continue;
            }

            if (output != null) {
                System.out.println(output);
            }
        }

    }

    private static OrlogGame getGame(String[] args) throws SemanticsException {

        String[] inputs = args[0].split(SEMICOLON);
        String lifePointsInString = inputs[NUM_OF_PLAYER];
        String godPowerInString = inputs[NUM_OF_PLAYER + 1];

        if (!lifePointsInString.matches(NUM)) {
            throw new SemanticsException("No number provided for Life Points. Game couldn't start.");
        }
        if (!godPowerInString.matches(NUM)) {
            throw new SemanticsException("No number provided for God Power. Game couldn't start.");
        }
        int lifePoints = Integer.parseInt(lifePointsInString);
        int godPower = Integer.parseInt(godPowerInString);

        if (lifePoints < 5) {
            throw new SemanticsException("Life Points must be equal to 5 or more. Game couldn't start.");
        }
        if (godPower < 0) {
            throw new SemanticsException("God power must be equal to 0 or more. Game couldn't start.");
        }

        Player[] players = new Player[NUM_OF_PLAYER];

        for (int i = 0; i < NUM_OF_PLAYER; i++) {
            final String newName = inputs[i];

            if(!newName.matches(PLAYER_NAME_PATTERN)) {
                throw new SemanticsException("invalid player name for player " + (i + SHIFTING_INDEX)
                        + ". Game couldn't start.");
            }
            players[i] = new Player(newName, lifePoints, godPower);
        }

        return new OrlogGame(players);
    }
}
