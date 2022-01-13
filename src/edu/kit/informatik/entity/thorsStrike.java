package edu.kit.informatik.entity;

import edu.kit.informatik.Player;

public class thorsStrike extends GodFavor {

    public thorsStrike(int level) {
        super(level);
    }

    @Override
    public void executeEffect(Player actor, Player victim) {

    }

    @Override
    public void calculateCost() {
        super.setCost(super.getLevel() * 4);
    }
}
