package org.example.visual_programming.blocks;

import org.example.visual_programming.blocks.conditions.Condition;

public class StatementBlock extends AbstractBlock {
    private Condition condition;
    private String statementType;

    public StatementBlock(String id, String name, String description,
                          String statementType, Condition condition) {
        super(id, name, description);
        this.statementType = statementType;
        this.condition = condition;
    }

    @Override
    public void execute() {
        if (condition.isValid() && condition.evaluate()) {
            getChildren().forEach(Block::execute);
        }
    }

    @Override
    public String toJavaCode() {
        StringBuilder sb = new StringBuilder();

        if ("if".equalsIgnoreCase(statementType)) {
            sb.append("if (").append(condition.toJavaCode()).append(") {\n");
            getChildren().forEach(child -> sb.append(child.toJavaCode()).append("\n"));
            sb.append("}");
        } else if ("switch".equalsIgnoreCase(statementType)) {
            sb.append("switch (").append(condition.toJavaCode()).append(") {\n");
            getChildren().forEach(child -> sb.append(child.toJavaCode()).append("\n"));
            sb.append("}");
        }

        return sb.toString();
    }

    // Геттеры и сеттеры
    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public String getStatementType() {
        return statementType;
    }

    public void setStatementType(String statementType) {
        this.statementType = statementType;
    }
}