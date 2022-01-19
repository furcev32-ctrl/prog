package edu.kit.informatik;

import edu.kit.informatik.entity.DiceFace;
import edu.kit.informatik.entity.GodFavorType;
import edu.kit.informatik.entity.Vector;
import edu.kit.informatik.entity.GodFavor;

import static edu.kit.informatik.entity.FightType.ATTACK;
import static edu.kit.informatik.entity.FightType.DEFEND;

public class Player {

    private String name;
    private long livePoints;
    private int godPower;
    private Vector attackVector;
    private Vector defenseVector;
    private int amountOfStealing;
    private GodFavor godFavor;
    private int numOfRoll;

    public Player(String name, long livePoints, int godPower) {
        this.name = name;
        this.livePoints = livePoints;
        this.godPower = godPower;
        this.numOfRoll = 0;
        amountOfStealing = 0;
        attackVector = new Vector(new int[]{0, 0});
        defenseVector = new Vector(new int[]{0, 0});
    }

    public void roll(DiceFace[] diceFaces) {
        for (DiceFace diceFace : diceFaces) {
            if (diceFace.hasGodPower()) {
                godPower++;
            }
            if (diceFace == DiceFace.ST || diceFace == DiceFace.GST) {
                amountOfStealing++;
                continue;
            }
            if (diceFace.getFightType() == ATTACK) {
                attackVector.add(diceFace.getVector());
            } else if (diceFace.getFightType() == DEFEND) {
                defenseVector.add(diceFace.getVector());
            }
        }
        numOfRoll++;
    }

    public void attackWithDiceFacesTo(Player victim) {
        victim.defendAgainst(this.getAttackVector());
        attackVector = Vector.nullVector();
    }

    public void defendAgainst(Vector attackingVector) {
        defenseVector.sub(attackingVector);
        livePoints = livePoints + defenseVector.getSumOfNegativeEntries();
        livePoints = Math.max(livePoints, 0);
        defenseVector = Vector.nullVector();
    }

    public void chooseGodFavor(GodFavor godFavor) {
        this.godFavor = godFavor;
    }

    public String getName() {
        return name;
    }

    public Vector getAttackVector() {
        return attackVector;
    }

    public void resetNumOfRoll() {
        numOfRoll = 0;
    }

    @Override
    public String toString() {
        return name + Main.SEMICOLON + livePoints + Main.SEMICOLON + godPower;
    }


    public void changeLifePointsBy(int amount) {
        livePoints = livePoints + amount;
        livePoints = Math.max(livePoints, 0);
    }

    public void changeGodPowerBy(int amount) {
        godPower = godPower + amount;
        godPower = Math.max(godPower, 0);
    }

    public boolean hasEnoughPower(int cost) {
        return godPower >= cost;
    }

    public GodFavor getGodFavor() {
        return godFavor;
    }

    public void weakenBy(int amountOfWeaken) {
        if (godFavor != null) {
            godFavor.weakenBy(amountOfWeaken);
        }
    }

    public void attackWithGodFavorTo(Player victim) {
        final int cost = godFavor.getCost();
        if (hasEnoughPower(cost)) {
            godFavor.executeEffect(this, victim);
            godPower = godPower - cost;
        }
        godFavor = null;
    }

    public boolean isDead() {
        return livePoints == 0;
    }

    public int getNumOfRoll() {
        return numOfRoll;
    }

    public int getAmountOfStealing() {
        return amountOfStealing;
    }

    public void resetPlayersAmountOfStealing() {
        amountOfStealing = 0;
    }

    public int getGodPower() {
        return godPower;
    }
}
