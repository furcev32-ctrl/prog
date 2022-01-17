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
            return switch (level) {
                case 1 -> 4;
                case 2 -> 8;
                case 3 -> 12;
                default -> 0;
            };


        }

        @Override
        public void executeEffect(int level, Player actor, Player victim) {
            final int damage = switch (level) {
                case 1 -> 2;
                case 2 -> 5;
                case 3 -> 8;
                default -> 0;
            };
            victim.changeLifePointsBy(-1 * damage);
        }

    },
    /**
     * "Thrymr’s Theft "
     */
    TT(3) {
        @Override
        public int getCost(int level) {
            return switch (level) {
                case 1 -> 3;
                case 2 -> 6;
                case 3 -> 9;
                default -> 0;
            };
        }

        @Override
        public void executeEffect(int level, Player actor, Player victim) {
            final int amountOfWeaken = switch (level) {
                case 1 -> 1;
                case 2 -> 2;
                case 3 -> 3;
                default -> 0;
            };
            victim.weakenBy(amountOfWeaken);
        }

    },
    /**
     * "Idun’s Regeneration"
     */
    IR(2) {
        @Override
        public int getCost(int level) {
            return switch (level) {
                case 1 -> 4;
                case 2 -> 7;
                case 3 -> 10;
                default -> 0;
            };
        }

        @Override
        public void executeEffect(int level, Player actor, Player victim) {
            final int amountOfHeal = switch (level) {
                case 1 -> 2;
                case 2 -> 4;
                case 3 -> 6;
                default -> 0;
            };
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