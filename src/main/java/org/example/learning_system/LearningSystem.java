package org.example.learning_system;

import java.util.*;

public class LearningSystem {
    private final List<Level> levels;
    private final Map<String, Achievement> achievements;
    //private final Map<String, Badge> badges;
    private final UserProgress userProgress;
    private int currentLevelIndex;
    private int experiencePoints;
    private int hintsUsedInCurrentLevel;
    private boolean solutionViewedInCurrentLevel;
    private final Map<String, CourseModule> courseModules;

    public LearningSystem() {
        this.levels = IntroduceLevels.createInitialLevels();
        this.achievements = new HashMap<>();
        //this.badges = new HashMap<>();
        this.userProgress = new UserProgress();
        this.courseModules = new LinkedHashMap<>();
        this.currentLevelIndex = 0;
        this.experiencePoints = 0;
        this.hintsUsedInCurrentLevel = 0;
        this.solutionViewedInCurrentLevel = false;

        initializeAchievements();
        //initializeBadges();
        initializeCourseModules();
        linkLevelsToModules();
    }

    // Инициализация системы достижений
    private void initializeAchievements() {
        achievements.put("FIRST_STEPS", new Achievement("Первые шаги", "Завершил первый уровень", 10));
        achievements.put("PERFECTIONIST", new Achievement("Перфекционист", "Прошел 5 уровней с 3 звездами", 50));
        achievements.put("QUICK_LEARNER", new Achievement("Быстрый ученик", "Прошел уровень без подсказок", 20));
        achievements.put("JAVA_MASTER", new Achievement("Мастер Java", "Завершил все уровни", 100));
        achievements.put("HELPLESS", new Achievement("Без помощи", "Не использовал ни одной подсказки в 10 уровнях", 30));
    }

    // Инициализация системы титулов
//    private void initializeBadges() {
//        badges.put("BEGINNER", new Badge("Новичок", "Начал обучение", "bronze"));
//        badges.put("VARIABLE_EXPERT", new Badge("Эксперт переменных", "Завершил модуль переменных", "silver"));
//        badges.put("LOOP_MASTER", new Badge("Мастер циклов", "Завершил модуль циклов", "gold"));
//    }

    // Инициализация тематических модулей
    private void initializeCourseModules() {
        courseModules.put("VARIABLES", new CourseModule("Переменные", "Основы работы с переменными в Java"));
        courseModules.put("OPERATORS", new CourseModule("Операторы", "Арифметические и логические операторы"));
        courseModules.put("LOOPS", new CourseModule("Циклы", "Циклические конструкции в Java"));
        courseModules.put("OOP", new CourseModule("ООП", "Основы объектно-ориентированного программирования"));
    }

    // Связывание уровней с модулями
    private void linkLevelsToModules() {
        // Уровни 1-3 - переменные
        for (int i = 0; i < 3; i++) {
            levels.get(i).setModule(courseModules.get("VARIABLES"));
            courseModules.get("VARIABLES").addLevel(levels.get(i));
        }
        // Уровни 4-6 - операторы
        for (int i = 3; i < 6; i++) {
            levels.get(i).setModule(courseModules.get("OPERATORS"));
            courseModules.get("OPERATORS").addLevel(levels.get(i));
        }
    }

    // Основные методы системы обучения

    public void startLevel(int levelIndex) {
        if (levelIndex >= 0 && levelIndex < levels.size()) {
            currentLevelIndex = levelIndex;
            hintsUsedInCurrentLevel = 0;
            solutionViewedInCurrentLevel = false;
        }
    }

    public Hint getHint() {
        Level currentLevel = getCurrentLevel();
        if (currentLevel == null) return null;

        hintsUsedInCurrentLevel++;

        // Определяем уровень подсказки на основе количества использованных подсказок
        int hintLevel = Math.min(hintsUsedInCurrentLevel, 4);
        return currentLevel.getHint(hintLevel);
    }

    public boolean checkSolution(String userSolution) {
        Level currentLevel = getCurrentLevel();
        if (currentLevel == null) return false;

        boolean isCorrect = currentLevel.getTask().getSolution().equals(userSolution.trim());

        if (isCorrect) {
            int starsEarned = calculateStarsEarned();
            currentLevel.setUserStars(starsEarned);
            currentLevel.setUserSolution(userSolution);
            userProgress.updateProgress(currentLevel);

            awardExperience(starsEarned);
            checkForAchievements();
            //checkForBadges();

            return true;
        }

        return false;
    }

    private int calculateStarsEarned() {
        if (solutionViewedInCurrentLevel) {
            return 1;
        } else if (hintsUsedInCurrentLevel > 0) {
            return 2;
        } else {
            return 3;
        }
    }

    private void awardExperience(int starsEarned) {
        int baseXP = getCurrentLevel().getDifficulty().getBaseXP();
        int xpMultiplier = starsEarned; // 1, 2 или 3
        int xpEarned = baseXP * xpMultiplier;

        experiencePoints += xpEarned;
        userProgress.addExperience(xpEarned);
    }

    private void checkForAchievements() {
        // Проверка достижения "Первые шаги"
        if (currentLevelIndex == 0 && !achievements.get("FIRST_STEPS").isUnlocked()) {
            unlockAchievement("FIRST_STEPS");
        }

        // Проверка достижения "Быстрый ученик"
        if (calculateStarsEarned() == 3) {
            unlockAchievement("QUICK_LEARNER");
        }

        // Проверка достижения "Перфекционист"
        long perfectLevels = userProgress.getLevelStars().values().stream()
                .filter(stars -> stars == 3)
                .count();
        if (perfectLevels >= 5 && !achievements.get("PERFECTIONIST").isUnlocked()) {
            unlockAchievement("PERFECTIONIST");
        }
    }

//    private void checkForBadges() {
//        // Проверка бейджа "Новичок"
//        if (userProgress.getCompletedLevelsCount() > 0 && !badges.get("BEGINNER").isUnlocked()) {
//            unlockBadge("BEGINNER");
//        }
//
//        // Проверка бейджа "Эксперт переменных"
//        CourseModule variablesModule = courseModules.get("VARIABLES");
//        if (variablesModule.isCompleted() && !badges.get("VARIABLE_EXPERT").isUnlocked()) {
//            unlockBadge("VARIABLE_EXPERT");
//        }
//    }

    public void unlockAchievement(String achievementId) {
        Achievement achievement = achievements.get(achievementId);
        if (achievement != null && !achievement.isUnlocked()) {
            achievement.unlock();
            userProgress.unlockAchievement(achievement);
            experiencePoints += achievement.getXpReward();
        }
    }

//    public void unlockBadge(String badgeId) {
//        Badge badge = badges.get(badgeId);
//        if (badge != null && !badge.isUnlocked()) {
//            badge.unlock();
//            userProgress.addBadge(badge);
//        }
//    }

    public void viewSolution() {
        solutionViewedInCurrentLevel = true;
    }


    public Level getCurrentLevel() {
        if (currentLevelIndex >= 0 && currentLevelIndex < levels.size()) {
            return levels.get(currentLevelIndex);
        }
        return null;
    }

    public List<Level> getLevelsForModule(String moduleId) {
        return courseModules.getOrDefault(moduleId, new CourseModule("", ""))
                .getLevels();
    }

    public int getUserLevel() {
        return (experiencePoints / 100) + 1; // Каждые 100 XP - новый уровень
    }

    public String getProgressSummary() {
        int completed = userProgress.getCompletedLevelsCount();
        int total = levels.size();
        double percentage = (double) completed / total * 100;

        return String.format("Прогресс: %d/%d (%.1f%%)", completed, total, percentage);
    }

}
