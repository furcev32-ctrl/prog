package edu.kit.informatik.entity;

import edu.kit.informatik.Main;
import edu.kit.informatik.Player;

public enum GodFavorType {
    /**
     * "Thor's Strike"
     */
    TS(1) {
        @Override
        public int getCost(int level) {
            switch (level) {
                case 1:
                    return 4;
                case 2:
                    return 8;
                case 3:
                    return 12;
            }
            return 0;
        }

        @Override
        public void executeEffect(int level, Player actor, Player victim) {
            final int damage;
            switch (level) {
                case 1:
                    damage = 2;
                    break;
                case 2:
                    damage = 5;
                    break;
                case 3:
                    damage = 8;
                    break;
                default:
                    damage = 0;
            }
            victim.changeLifePointsBy(-1 * damage);
        }

    },
    /**
     * "Thrymr’s Theft "
     */
    TT(3) {
        @Override
        public int getCost(int level) {
            switch (level) {
                case 1:
                    return 3;
                case 2:
                    return 6;
                case 3:
                    return 9;
            }
            return 0;
        }

        @Override
        public void executeEffect(int level, Player actor, Player victim) {
            final int amountOfWeaken;
            switch (level) {
                case 1:
                    amountOfWeaken = 1;
                    break;
                case 2:
                    amountOfWeaken = 2;
                    break;
                case 3:
                    amountOfWeaken = 3;
                    break;
                default:
                    amountOfWeaken = 0;
            }
            victim.weakenBy(-1 * amountOfWeaken);
        }

    },
    /**
     * "Idun’s Regeneration"
     */
    IR(2) {
        @Override
        public int getCost(int level) {
            switch (level) {
                case 1:
                    return 4;
                case 2:
                    return 7;
                case 3:
                    return 10;
            }
            return 0;
        }

        @Override
        public void executeEffect(int level, Player actor, Player victim) {
            final int amountOfHeal;
            switch (level) {
                case 1:
                    amountOfHeal = 2;
                    break;
                case 2:
                    amountOfHeal = 4;
                    break;
                case 3:
                    amountOfHeal = 6;
                    break;
                default:
                    amountOfHeal = 0;
            }
            actor.changeLifePointsBy(amountOfHeal);
        }
    };

    GodFavorType(int priority) {
        this.priority = priority;
    }

    private final int priority;

    public abstract int getCost(int level);

    public abstract void executeEffect(int level, Player actor, Player victim);

    public int getPriority() {
        return priority;
    }

    public static String getRegex() {
        StringBuilder result = new StringBuilder();
        for (GodFavorType favor : values()) {
            result.append(Main.PIPE).append(favor.name());
        }
        return result.substring(Main.CUTTING_INDEX);
    }

    public static GodFavorType parseToGodFavorType(String godFavorAsString) {
        for (GodFavorType godFavor : values()) {
            if (godFavorAsString.contains(godFavor.toString())) {
                return godFavor;
            }
        }
        return null;
    }


}