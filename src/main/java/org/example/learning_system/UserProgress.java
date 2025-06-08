package org.example.learning_system;

import java.util.*;

public class UserProgress {
    private final Map<Integer, Integer> levelProgress; // levelIndex -> stars
    private final Set<Achievement> unlockedAchievements;
    //private final Set<Badge> unlockedBadges;
    private int totalExperience;
    private Map<Integer, Integer> levelStars;

    public UserProgress() {
        this.levelProgress = new HashMap<>();
        this.unlockedAchievements = new HashSet<>();
        //this.unlockedBadges = new HashSet<>();
        this.totalExperience = 0;
    }

    public void updateProgress(Level level) {
        levelProgress.put(level.getIndex(), level.getUserStars());
    }

    public int getCompletedLevelsCount() {
        return (int) levelProgress.values().stream().filter(stars -> stars > 0).count();
    }

    public void unlockAchievement(Achievement achievement) {
        if (!achievement.isUnlocked()) {
            achievement.unlock();
            unlockedAchievements.add(achievement);
        }
    }


    // Геттеры
    public Map<Integer, Integer> getLevelProgress() {
        return levelProgress;
    }

    public Set<Achievement> getUnlockedAchievements() {
        return unlockedAchievements;
    }

    public int getTotalExperience() {
        return totalExperience;
    }

    public Map<Integer, Integer> getLevelStars() {
        return levelStars;
    }

    public void addExperience(int earnedXP) {
        totalExperience += earnedXP;
    }


}
