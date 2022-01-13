package edu.kit.informatik.entity;

import edu.kit.informatik.Player;

public abstract class GodFavor {

    private int level;
    private int cost;

    public GodFavor(int level) {
        this.level = level;
    }
    public abstract void executeEffect(Player actor, Player victim);
    public abstract void calculateCost();


    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public int getLevel() {
        return level;
    }
}
