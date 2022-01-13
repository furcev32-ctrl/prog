package edu.kit.informatik;

import edu.kit.informatik.entity.DiceFace;
import edu.kit.informatik.entity.GodFavor;

import java.util.ArrayList;
import java.util.Collections;

public class Player {



    private String name;
    private int livePoints;
    private int godPower;
    private ArrayList<DiceFace> rolledDiceFaces;
    private GodFavor godFavor;
    private boolean hasRolled;

    public Player(String name, int livePoints, int godPower) {
        this.name = name;
        this.livePoints = livePoints;
        this.godPower = godPower;
        this.hasRolled = false;
    }

    public void roll(DiceFace[] diceFaces) {
        Collections.addAll(rolledDiceFaces,  diceFaces);
        hasRolled = true;
    }

    public String getName() {
        return name;
    }

    public GodFavor getGodFavor() {
        return godFavor;
    }

    public boolean hasRolled() {
        return hasRolled;
    }

    @Override
    public String toString() {
        return name + Main.SEMICOLON + livePoints + Main.SEMICOLON + godPower;
    }
}
