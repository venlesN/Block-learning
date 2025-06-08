package org.example.visual_programming.blocks;

import org.example.visual_programming.blocks.conditions.Condition;

public class LoopBlock extends AbstractBlock {
    private String loopType;
    private Condition condition;
    private String initialization;
    private String increment;

    public LoopBlock(String id, String name, String description,
                     String loopType, Condition condition) {
        super(id, name, description);
        this.loopType = loopType;
        this.condition = condition;
    }

    @Override
    public void execute() {
        if ("while".equals(loopType)) {
            while (condition.isValid() && condition.evaluate()) {
                getChildren().forEach(Block::execute);
            }
        } else if ("for".equals(loopType)) {
            // Здесь должна быть логика инициализации и инкремента
            for (; condition.isValid() && condition.evaluate(); ) {
                getChildren().forEach(Block::execute);
            }
        }
    }

    @Override
    public String toJavaCode() {
        StringBuilder sb = new StringBuilder();

        if ("while".equals(loopType)) {
            sb.append("while (").append(condition.toJavaCode()).append(") {\n");
            getChildren().forEach(child -> sb.append(child.toJavaCode()).append("\n"));
            sb.append("}");
        } else if ("for".equals(loopType)) {
            sb.append("for (").append(initialization != null ? initialization : "")
                    .append("; ").append(condition.toJavaCode())
                    .append("; ").append(increment != null ? increment : "")
                    .append(") {\n");
            getChildren().forEach(child -> sb.append(child.toJavaCode()).append("\n"));
            sb.append("}");
        }

        return sb.toString();
    }

    // Геттеры и сеттеры
    public String getLoopType() {
        return loopType;
    }

    public void setLoopType(String loopType) {
        this.loopType = loopType;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public String getInitialization() {
        return initialization;
    }

    public void setInitialization(String initialization) {
        this.initialization = initialization;
    }

    public String getIncrement() {
        return increment;
    }

    public void setIncrement(String increment) {
        this.increment = increment;
    }
}