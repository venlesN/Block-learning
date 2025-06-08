package org.example.visual_programming.blocks.conditions;

//Простое условие(переданы числа или строки)

public class SimpleCondition implements Condition {
    private String expression;

    public SimpleCondition(String expression) {
        this.expression = expression;
    }

    @Override
    public boolean evaluate() {
        // Здесь должна быть более сложная логика парсинга выражения
        // Для примера просто проверяем на равенство "true"
        return "true".equalsIgnoreCase(expression);
    }

    @Override
    public String toJavaCode() {
        return expression;
    }

    @Override
    public boolean isValid() {
        // Простая валидация - выражение не должно быть пустым
        return expression != null && !expression.trim().isEmpty();
    }
}

