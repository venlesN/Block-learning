package org.example.learning_system;

import java.util.List;

public class Task {
    private final String description;
    private final BlockSolution solution;
    private final Hint hint;
    private final String example;
    private final List<String> steps;
    private final Difficulty difficulty;

    public Task(String description, BlockSolution solution, Hint hint, String example,
                List<String> steps, Difficulty difficulty) {
        this.description = description;
        this.solution = solution;
        this.hint = hint;
        this.example = example;
        this.steps = steps;
        this.difficulty = difficulty;
    }

    // Геттеры
    public String getDescription() { return description; }
    public BlockSolution getSolution() { return solution; }
    public Difficulty getDifficulty() { return difficulty; }

    public Hint getHint() {
        return hint;
    }

    public String getExample() {
        return example;
    }

    public List<String> getSteps() {
        return steps;
    }
}
