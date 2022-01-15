package edu.kit.informatik;

import edu.kit.informatik.entity.DiceFace;
import edu.kit.informatik.entity.FightType;
import edu.kit.informatik.entity.GodFavor;
import edu.kit.informatik.entity.Vector;

import java.util.ArrayList;
import java.util.Collections;

import static edu.kit.informatik.entity.FightType.ATTACK;
import static edu.kit.informatik.entity.FightType.DEFEND;

public class Player {

    private String name;
    private int livePoints;
    private int godPower;
    private final Vector attackVector;
    private final Vector defenseVector;
    private GodFavor godFavor;
    private boolean hasRolled;

    public Player(String name, int livePoints, int godPower) {
        this.name = name;
        this.livePoints = livePoints;
        this.godPower = godPower;
        this.hasRolled = false;
        attackVector = new Vector(new int[]{0, 0});
        defenseVector = new Vector(new int[]{0, 0});
    }

    public void roll(DiceFace[] diceFaces) {
        for (DiceFace diceFace:diceFaces) {
            if(diceFace.hasGodPower()) {
                godPower++;
            }
            if(diceFace == DiceFace.ST || diceFace == DiceFace.GST) {
                continue;
            }
            if (diceFace.getFightType() == ATTACK) {
                attackVector.add(diceFace.getVector());
            }
            else if(diceFace.getFightType() == DEFEND) {
                defenseVector.add(diceFace.getVector());
            }
        }
        hasRolled = true;
    }

    public void fight(Vector attackingVector) {
        defenseVector.sub(attackingVector);
        livePoints = livePoints + defenseVector.getSumOfValues();
        livePoints = Math.max(livePoints, 0);
    }

    public void chooseGodFavor(GodFavor godFavor) {
        this.godFavor = godFavor;
    }

    public String getName() {
        return name;
    }

    public GodFavor getGodFavor() {
        return godFavor;
    }

    public Vector getAttackVector() {
        return attackVector;
    }

    public int getLivePoints() {
        return livePoints;
    }

    public boolean hasRolled() {
        return hasRolled;
    }

    @Override
    public String toString() {
        return name + Main.SEMICOLON + livePoints + Main.SEMICOLON + godPower;
    }


}
