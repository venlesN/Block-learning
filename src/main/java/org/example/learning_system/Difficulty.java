package org.example.learning_system;

public enum Difficulty {
    EASY(10), MEDIUM(20), HARD(30);

    private final int baseXP;

    Difficulty(int baseXP) {
        this.baseXP = baseXP;
    }

    public int getBaseXP() { return baseXP; }

}
