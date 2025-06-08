package org.example.visual_programming.blocks.conditions;
import java.util.function.Predicate;
public interface Condition {
    boolean evaluate();
    String toJavaCode();
    boolean isValid();
}

