package edu.kit.informatik;

import edu.kit.informatik.entity.DiceFace;
import edu.kit.informatik.entity.GamePhase;
import edu.kit.informatik.exception.SemanticsException;

import static edu.kit.informatik.entity.GamePhase.GOD_FAVOR_PHASE;
import static edu.kit.informatik.entity.GamePhase.ROLLING_PHASE;

public class OrlogGame {

    private boolean isRunning;
    private Player[] players;
    private Player currentPlayer;
    private GamePhase currentPhase;



    public OrlogGame(Player[] players) {
        isRunning = true;
        this.players = players;
        currentPlayer = players[0];
        currentPhase = ROLLING_PHASE;
    }


    public boolean isRunning() {
        return isRunning;
    }

    public void quit() {
        isRunning = false;
    }

    public String print() {
        StringBuilder result = new StringBuilder();
        for(Player player: players) {
            result.append(Main.NEW_LINE).append(player.toString());
        }
        return result.substring(Main.CUTTING_INDEX);
    }

    public String roll(DiceFace[] diceFaces) throws SemanticsException {

        checkRollConditions(diceFaces);
        currentPlayer.roll(diceFaces);
        return Main.OK;

    }


    public String turn() throws SemanticsException {

        checkTurnConditions();

        final int nextPlayerIndex = (getArrIndexOfPlayer(currentPlayer) + 1) % players.length;
        currentPlayer = players[nextPlayerIndex];
        return currentPlayer.getName();
    }



    private int getArrIndexOfPlayer(Player player) {

        for(int i = 0; i < players.length; i++) {
            if(players[i] == player) {
                return i;
            }
        }

        return -1;
    }

    private void checkRollConditions(DiceFace[] diceFaces) throws SemanticsException {
        if(currentPhase != ROLLING_PHASE) {
            throw new SemanticsException("You can't roll in the " + currentPhase.getName() + ".");
        }

        int numOfDiceFacesWithGodPower = 0;
        for (DiceFace diceFace: diceFaces) {
            if (diceFace.hasGodPower()) {
                numOfDiceFacesWithGodPower++;
            }
        }

        if (numOfDiceFacesWithGodPower > 4) {
            throw new SemanticsException("Rolling more than 4 Dice Faces with god power isn't allowed.");
        }

    }


    private void checkTurnConditions() throws SemanticsException {

        if (isLastPlayer(currentPlayer)) {
            if (currentPhase == GOD_FAVOR_PHASE) {
                if (currentPlayer.getGodFavor() != null) {
                    throw new SemanticsException("You have to execute the evaluation command.");
                }
                else {
                    throw new SemanticsException("You could choose a god favor or execute the evaluation command.");
                }
            }
        }

    }

    private boolean isLastPlayer(Player player) {
        return getArrIndexOfPlayer(currentPlayer) == players.length - Main.SHIFTING_INDEX;
    }
}
