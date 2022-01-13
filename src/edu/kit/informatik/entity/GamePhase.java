package edu.kit.informatik.entity;

public enum GamePhase {
    ROLLING_PHASE("rolling phase"),
    GOD_FAVOR_PHASE("god favor phase"),
    EVALUATION_PHASE("evaluation phase");

    private final String name;

    GamePhase(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
