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

        type.executeEffect(level, actor, victim);


    }

    public int getCost() {
        return cost;
    }

    public int getLevel() {
        return level;
    }

    public GodFavorType getType() {
        return type;
    }

    public void weakenBy(int amountOfWeaken) {
        level = level + amountOfWeaken;
        level = Math.max(level, 0);
    }
}

