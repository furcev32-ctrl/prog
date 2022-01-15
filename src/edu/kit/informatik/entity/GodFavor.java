package edu.kit.informatik.entity;

import edu.kit.informatik.Main;

public enum GodFavor {
    TS("Thor's Strike"),
    TT("Thrymr’s Theft "),
    IR("Idun’s Regeneration");

    private final String name;
    private int level;

    GodFavor(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public static String getRegex() {
        StringBuilder result = new StringBuilder();
        for (GodFavor favor: values()) {
            result.append(Main.PIPE).append(favor.name());
        }
        return result.substring(Main.CUTTING_INDEX);
    }

    public static GodFavor parseToGodFavor(String godFavorAsString) {
        for (GodFavor godFavor : values()) {
            if (godFavorAsString.contains(godFavor.toString())) {
                return godFavor;
            }
        }
        return null;
    }

}
