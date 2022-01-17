package edu.kit.informatik.entity;

import edu.kit.informatik.Player;

public class GodFavor {
    public int level;
    public GodFavorType type;
    public int cost;

    public GodFavor(GodFavorType type, int level) {
        this.level = level;
        this.type = type;
        this.cost = type.getCost(level);
    }

    public void executeEffect(Player actor, Player victim) {

        if (actor.hasEnoughPower(cost)) {
            type.executeEffect(level, actor, victim);
        }

    }


    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getLevel() {
        return level;
    }

    public GodFavorType getType() {
        return type;
    }

    public void weakenBy(int i) {
        level = level + i;
        level = Math.max(level, 0);
    }
}

