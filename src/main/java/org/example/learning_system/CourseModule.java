package org.example.learning_system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourseModule {
    private final String name;
    private final String description;
    private final List<Level> levels;

    public CourseModule(String name, String description) {
        this.name = name;
        this.description = description;
        this.levels = new ArrayList<>();
    }

    public void addLevel(Level level) {
        levels.add(level);
    }

    public boolean isCompleted() {
        return levels.stream().allMatch(level -> level.getUserStars() > 0);
    }

    // Геттеры
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<Level> getLevels() { return Collections.unmodifiableList(levels); }
}
