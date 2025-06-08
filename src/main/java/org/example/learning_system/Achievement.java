package org.example.learning_system;

public class Achievement {
    private String name;
    private String description;
    private boolean unlocked;
    private final int xpReward;

    public Achievement(String name, String description, int xpReward) {
        this.name = name;
        this.description = description;
        this.unlocked = false;
        this.xpReward = xpReward;
    }

    public void unlock() {
        this.unlocked = true;
    }

    // Геттеры
    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean isUnlocked() { return unlocked; }

    public int getXpReward() {return xpReward;}


}
