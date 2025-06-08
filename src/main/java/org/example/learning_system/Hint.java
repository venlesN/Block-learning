package org.example.learning_system;

public class Hint {
    private int level;
    private String content;

    public Hint(int level, String content) {
        this.level = level;
        this.content = content;
    }

    // Геттеры
    public int getLevel() { return level; }
    public String getContent() { return content; }
}
