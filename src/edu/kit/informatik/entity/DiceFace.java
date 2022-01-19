package edu.kit.informatik.entity;

import edu.kit.informatik.Main;

import static edu.kit.informatik.entity.FightType.ATTACK;
import static edu.kit.informatik.entity.FightType.DEFEND;
import static edu.kit.informatik.entity.Range.*;

public enum DiceFace {

    MA(false, ATTACK, CLOSE, 1),
    MD(false, DEFEND, CLOSE, 1),
    GMD(true, DEFEND, CLOSE, 1),
    RA(false, ATTACK, RANGED, 1),
    GRA(true, ATTACK, RANGED, 1),
    RD(false, DEFEND, RANGED, 1),
    GRD(true, DEFEND, RANGED, 1),
    ST(false, ATTACK, WHEREVER, 1),
    GST(true, ATTACK, WHEREVER, 1);

    private final boolean hasGodPower;
    private final FightType fightType;
    private final Range range;
    private final int fightPower;

    DiceFace(boolean hasGodPower, FightType fightType, Range range, int fightPower) {
        this.hasGodPower = hasGodPower;
        this.fightType = fightType;
        this.range = range;
        this.fightPower = fightPower;
    }

    public static String getRegex() {
        StringBuilder result = new StringBuilder();
        for (DiceFace type : values()) {
            result.append(Main.PIPE).append(type.name());
        }
        return result.substring(Main.CUTTING_INDEX);
    }

    public static DiceFace parseToDiceFace(String diceFaceAsString) {
        for (DiceFace diceFace : values()) {
            if (diceFaceAsString.equalsIgnoreCase(diceFace.toString())) {
                return diceFace;
            }
        }
        return null;
    }

    public boolean hasGodPower() {
        return hasGodPower;
    }

    public Vector getVector() {
        if (this.range == CLOSE) {
            return new Vector(new int[]{this.fightPower, 0});
        } else if (this.range == RANGED) {
            return new Vector(new int[]{0, this.fightPower});
        } else {
            return null;
        }
    }

    public FightType getFightType() {
        return fightType;
    }

    @Override
    public String toString() {
        return name();
    }
}
