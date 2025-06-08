package org.example.learning_system;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final int index;
    private final String theory;
    private int userStars;
    private final Task task;
    private final String visualAppearance;
    private String userSolution;
    private final List<Hint> hints;
    private CourseModule module;
    private final Difficulty difficulty;

    public Level(int index, String theory, Task task, String visualAppearance, Difficulty difficulty) {
        this.index = index;
        this.theory = theory;
        this.task = task;
        this.visualAppearance = visualAppearance;
        this.difficulty = difficulty;
        this.userStars = 0;
        this.userSolution = "";
        this.hints = new ArrayList<>();
        initializeHints();
    }

    private void initializeHints() {
        hints.add(new Hint(1, "Общие указания по теме задания: " + task.getHint()));
        hints.add(new Hint(2, "Пример аналогичного кода:\n" + task.getExample()));
        hints.add(new Hint(3, "Пошаговая инструкция:\n1. " + task.getSteps().get(0) +
                "\n2. " + task.getSteps().get(1) +
                "\n3. " + task.getSteps().get(2)));
        hints.add(new Hint(4, "Готовое решение:\n" + task.getSolution()));
    }

    public Hint getHint(int level) { return hints.get(level - 1); }
    public void setModule(CourseModule module) { this.module = module; }

    // Геттеры и сеттеры
    public int getIndex() { return index; }
    public String getTheory() { return theory; }
    public int getUserStars() { return userStars; }
    public void setUserStars(int stars) { this.userStars = stars; }
    public Task getTask() { return task; }
    public String getVisualAppearance() { return visualAppearance; }
    public String getUserSolution() { return userSolution; }
    public void setUserSolution(String solution) { this.userSolution = solution; }

    public List<Hint> getHints() {return hints;}

    public CourseModule getModule() {return module;}

    public Difficulty getDifficulty() {return difficulty;}

}
