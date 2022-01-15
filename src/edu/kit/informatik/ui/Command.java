package edu.kit.informatik.ui;


import edu.kit.informatik.Main;
import edu.kit.informatik.OrlogGame;
import edu.kit.informatik.entity.DiceFace;
import edu.kit.informatik.entity.GodFavor;
import edu.kit.informatik.exception.SemanticsException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * List of available commands with their command line interaction expressions.
 *
 * @author
 * @version 1.0
 */
public enum Command {

    PRINT(Main.PRINT) {
        @Override
        String execute(Matcher input, OrlogGame game) throws SemanticsException {

            return game.print();
        }
    },
    ROLL(Main.ROLL + Main.SPACE + "(" + Main.ROLL_INPUT_PATTERN + ")") {
        @Override
        String execute(Matcher input, OrlogGame game) throws SemanticsException {

            final DiceFace[] diceFaces = new DiceFace[Main.NUM_OF_ROLLS];
            final String[] diceFacesAsString = input.group(Main.FIRST_PARAMETER_INDEX).split(Main.SEMICOLON);

            if (diceFacesAsString.length != 6) {
               throw new SemanticsException("The roll accepts exact only 6 parameters.");
            }
            for (int i = 0; i < diceFaces.length; i++) {
                diceFaces[i] = DiceFace.parseToDiceFace(diceFacesAsString[i]);
                if(diceFaces[i] == null) {
                    throw new SemanticsException("The dice face name in the place " + i + " is wrong written.");
                }
            }

            return game.roll(diceFaces);
        }
    },
    GOD_FAVOR(Main.GOD_FAVOR + Main.SPACE + "(" + GodFavor.getRegex()
            + Main.SEMICOLON + Main.NUM + ")") {
        @Override
        String execute(Matcher input, OrlogGame game) throws SemanticsException {
            final String[] arguments = input.group(Main.FIRST_PARAMETER_INDEX).split(Main.SEMICOLON);
            final GodFavor godFavor = GodFavor.parseToGodFavor(arguments[0]);
            final int level = Integer.parseInt(arguments[1]);
            return game.godfavor(godFavor, level);
        }
    },
    TURN(Main.TURN) {
        @Override
        String execute(Matcher input, OrlogGame game) throws SemanticsException {
            return game.turn();
        }
    },
    EVALUATE(Main.EVALUATE){
        @Override
        String execute(Matcher input, OrlogGame game) throws SemanticsException {
            return game.evaluate();
        }
    },
    QUIT(Main.QUIT) {
        @Override
        String execute(Matcher input, OrlogGame game) {
            game.quit();
            return null;
        }
    };


    /**
     * String constant containing an error message for the case that no command
     * could be found in this enum.
     */
    public static final String COMMAND_NOT_FOUND = Main.ERROR + "command not found!";

    /**
     * The pattern of this command.
     */
    private final Pattern pattern;

    /**
     * Instantiates a new command with the given String. The given String must be a
     * compilable {@link Pattern}.
     *
     * @param pattern the pattern of this command
     */
    Command(final String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    /**
     * Executes the command contained in the input if there is any, returns an error
     * message otherwise. If a command is found in the input, returns the result of
     * this input performed on the given playlist.
     *
     * @param input the line of input
     * @param game  the {@link OrlogGame} the command is executed on
     * @return the result of the command execution, may contain error messages or be
     * null if there is no output
     */
    public static String executeCommand(final String input, final OrlogGame game) throws SemanticsException {
        for (final Command command : Command.values()) {
            final Matcher matcher = command.pattern.matcher(input);
            if (matcher.matches()) {
                return command.execute(matcher, game);
            }
        }
        return COMMAND_NOT_FOUND;
    }

    /**
     * Executes the given input on the given game.
     *
     * @param input the line of input
     * @param game  the game that command is executed on.
     * @return the result of the command execution, may be null if there is no output
     */
    abstract String execute(Matcher input, OrlogGame game) throws SemanticsException;
}