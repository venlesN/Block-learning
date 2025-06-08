package org.example.visual_programming.blocks.conditions;

import org.example.visual_programming.blocks.ActionBlock;
import org.example.visual_programming.blocks.ObjectBlock;

import java.util.function.Predicate;

public class ObjectActionCondition implements Condition {
    private ObjectBlock object;
    private ActionBlock action;
    private Predicate<Object> completionPredicate;

    public ObjectActionCondition(ObjectBlock object, ActionBlock action) {
        this.object = object;
        this.action = action;
    }

    @Override
    public boolean evaluate() {
        // Выполняем действие и проверяем его завершение
        action.execute();
        return completionPredicate == null || completionPredicate.test(null);
    }

    @Override
    public String toJavaCode() {
        return object.toJavaCode() + "\n" + action.toJavaCode();
    }

    @Override
    public boolean isValid() {
        return object != null && action != null;
    }

    public void setCompletionPredicate(Predicate<Object> predicate) {
        this.completionPredicate = predicate;
    }
}