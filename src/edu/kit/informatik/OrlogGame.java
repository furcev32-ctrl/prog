package edu.kit.informatik;

import edu.kit.informatik.entity.DiceFace;
import edu.kit.informatik.entity.GamePhase;
import edu.kit.informatik.entity.GodFavor;
import edu.kit.informatik.entity.GodFavorType;
import edu.kit.informatik.exception.SemanticsException;

import static edu.kit.informatik.entity.GamePhase.*;

public class OrlogGame {

    private boolean isRunning;
    private final Player[] players;
    private Player currentPlayer;
    private GamePhase currentPhase;
    private boolean hasEnded;


    public OrlogGame(Player[] players) {
        isRunning = true;
        hasEnded = false;
        this.players = players;
        currentPlayer = players[0];
        currentPhase = ROLLING_PHASE;
    }


    public boolean isRunning() {
        return isRunning;
    }

    public void quitCommand() {
        isRunning = false;
    }

    public String printCommand() {
        StringBuilder result = new StringBuilder();
        for (Player player : players) {
            result.append(Main.NEW_LINE).append(player.toString());
        }
        return result.substring(Main.CUTTING_INDEX);
    }

    public String rollCommand(DiceFace[] diceFaces) throws SemanticsException {
        checkRollConditions(diceFaces);
        currentPlayer.roll(diceFaces);
        return Main.OK;

    }


    public String turnCommand() throws SemanticsException {

        checkTurnConditions();

        final int nextPlayerIndex = (getArrIndexOfPlayer(currentPlayer) + 1) % players.length;
        currentPlayer = players[nextPlayerIndex];
        if (currentPhase == EVALUATION_PHASE) {
            currentPhase = ROLLING_PHASE;
        }
        return currentPlayer.getName();
    }


    public String godFavorCommand(GodFavor godFavor) throws SemanticsException {
        checkGodFavorConditions(godFavor);
        currentPlayer.chooseGodFavor(godFavor);
        currentPhase = GOD_FAVOR_PHASE;
        return Main.OK;
    }


    public String evaluateCommand() throws SemanticsException {
        checkEvaluate();
        players[0].attackWithDiceFacesTo(players[1]);
        players[1].attackWithDiceFacesTo(players[0]);
        stealing();
        if (isDraw()) {
            return Main.DRAW;
        } else if (players[0].isDead()) {
            return hasWon(players[1]);
        } else if (players[1].isDead()) {
            return hasWon(players[0]);
        }

        if (players[0].getGodFavor() == null && players[1].getGodFavor() == null) {
            return printCommand();
        } else if (players[0].getGodFavor() != null && players[1].getGodFavor() == null) {
            players[0].attackWithGodFavorTo(players[1]);
        } else if (players[0].getGodFavor() == null && players[1].getGodFavor() != null) {
            players[1].attackWithGodFavorTo(players[0]);
        } else {
            if (players[0].getGodFavor().getType().getPriority() >= players[1].getGodFavor().getType().getPriority()) {
                players[0].attackWithGodFavorTo(players[1]);
                players[1].attackWithGodFavorTo(players[0]);
            } else {
                players[1].attackWithGodFavorTo(players[0]);
                players[0].attackWithGodFavorTo(players[1]);
            }
        }

        if (isDraw()) {
            return Main.DRAW;
        } else if (players[0].isDead()) {
            return hasWon(players[1]);
        } else if (players[1].isDead()) {
            return hasWon(players[0]);
        }

        currentPhase = EVALUATION_PHASE;
        resetPlayersNumOfRoll();
        resetPlayersAmountOfStealing();
        return printCommand();
    }


    private int getArrIndexOfPlayer(Player player) {

        for (int i = 0; i < players.length; i++) {
            if (players[i] == player) {
                return i;
            }
        }

        return -1;
    }

    private void checkRollConditions(DiceFace[] diceFaces) throws SemanticsException {
        if (hasEnded) {
            throw new SemanticsException("The game has ended. This command can't be executed.");
        }

        if (currentPlayer.getNumOfRoll() == 3) {
            throw new SemanticsException("You can't roll more 3 in a round.");
        }
        if (isFirstPlayer(currentPlayer) && currentPlayer.getNumOfRoll() > nextPlayerOf(currentPlayer).getNumOfRoll()) {
            throw new SemanticsException("You rolled more than you should. Please type turn.");
        }
        if (currentPhase != ROLLING_PHASE) {
            throw new SemanticsException("You can't roll in the " + currentPhase.getName() + ".");
        }

        int numOfDiceFacesWithGodPower = 0;
        for (DiceFace diceFace : diceFaces) {
            if (diceFace.hasGodPower()) {
                numOfDiceFacesWithGodPower++;
            }
        }

        if (numOfDiceFacesWithGodPower > 4) {
            throw new SemanticsException("Rolling more than 4 Dice Faces with god power isn't allowed.");
        }

    }

    private Player nextPlayerOf(Player currentPlayer) {
        return players[(getArrIndexOfPlayer(currentPlayer) + 1) % players.length];
    }


    private void checkTurnConditions() throws SemanticsException {
        if (hasEnded) {
            throw new SemanticsException("The game has ended. This command can't be executed.");
        }
        if (isLastPlayer(currentPlayer)) {
            if (currentPhase == GOD_FAVOR_PHASE) {
                if (currentPlayer.getGodFavor() != null) {
                    throw new SemanticsException("You have to execute the evaluation command.");
                } else {
                    throw new SemanticsException("You could choose a god favor or execute the evaluation command.");
                }
            }
        }

    }


    private void checkGodFavorConditions(GodFavor godFavor) throws SemanticsException {
        if (hasEnded) {
            throw new SemanticsException("The game has ended. This command can't be executed.");
        }
        if (currentPlayer.getGodFavor() != null) {
            throw new SemanticsException("You can't choose a god favor twice.");
        }
        if (godFavor.getLevel() < Main.MIN_GOD_FAVOR_LEVEL || godFavor.getLevel() > Main.MAX_GOD_FAVOR_LEVEL) {
            throw new SemanticsException("The god favor level should be between "
                    + Main.MIN_GOD_FAVOR_LEVEL + " and " + Main.MAX_GOD_FAVOR_LEVEL + ".");
        }

    }

    private void checkEvaluate() throws SemanticsException {
        if (hasEnded) {
            throw new SemanticsException("The game has ended. This command can't be executed.");
        }
        if (currentPhase == EVALUATION_PHASE) {
            throw new SemanticsException("The evaluation could be done only once.");
        }
        if (!isLastPlayer(currentPlayer)) {
            throw new SemanticsException("You should give you turn to the next player. The last " +
                    "player could execute the evaluate command.");
        }

    }

    private boolean isLastPlayer(Player player) {
        return getArrIndexOfPlayer(currentPlayer) == players.length - Main.SHIFTING_INDEX;
    }

    private boolean isFirstPlayer(Player player) {
        return getArrIndexOfPlayer(currentPlayer) == 0;
    }

    private String hasWon(Player player) {
        hasEnded = true;
        return player.getName() + Main.SPACE + Main.WINS;
    }

    private void resetPlayersNumOfRoll() {
        for (Player player : players) {
            player.resetNumOfRoll();
        }
    }

    private void resetPlayersAmountOfStealing() {
        for (Player player : players) {
            player.resetPlayersAmountOfStealing();
        }
    }

    private boolean isDraw() {
        for (Player player : players) {
            if (!player.isDead()) {
                return false;
            }
        }
        return true;
    }

    private void stealing() {

        if (players[0].getAmountOfStealing() == players[1].getAmountOfStealing()) {
            return;
        }
        if (players[0].getAmountOfStealing() > players[1].getAmountOfStealing()) {
            int diff = players[0].getAmountOfStealing() - players[1].getAmountOfStealing();
            players[0].changeGodPowerBy(Math.min(diff, players[1].getGodPower()));
            players[1].changeGodPowerBy(-1 * diff);
        } else if (players[1].getAmountOfStealing() > players[0].getAmountOfStealing()) {
            int diff = players[1].getAmountOfStealing() - players[0].getAmountOfStealing();
            players[1].changeGodPowerBy(Math.min(diff, players[0].getGodPower()));
            players[0].changeGodPowerBy(-1 * diff);
        }

    }
}
