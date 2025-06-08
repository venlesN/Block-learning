package org.example.visual_programming.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Блок действия с исполнителем и параметрами
 */
public class ActionBlock extends AbstractBlock {
    private final Map<String, Object> parameters = new HashMap<>();
    private BiConsumer<ObjectBlock, Map<String, Object>> executionLogic;
    private ObjectBlock executor; // Исполнитель действия

    public ActionBlock(String id, String name, String description, ObjectBlock executor) {
        super(id, name, description);
        this.executor = executor;
    }

    // Fluent-интерфейс для настройки
    public ActionBlock withParameter(String name, Object value) {
        parameters.put(name, value);
        return this;
    }

    public ActionBlock withExecutor(ObjectBlock executor) {
        this.executor = executor;
        return this;
    }

    public ActionBlock withExecutionLogic(BiConsumer<ObjectBlock, Map<String, Object>> logic) {
        this.executionLogic = logic;
        return this;
    }

    @Override
    public void execute() {
        if (executionLogic != null && executor != null) {
            executionLogic.accept(executor, parameters);
        }
    }

    @Override
    public String toJavaCode() {
        StringBuilder sb = new StringBuilder();
        sb.append("// Действие: ").append(getName()).append("\n");

        if (executor != null) {
            sb.append(executor.getId()).append(".");
        }

        // Генерация кода в зависимости от типа действия
        if (getName().contains("Перемещение")) {
            int steps = (int) parameters.getOrDefault("steps", 1);
            sb.append("moveRight(").append(steps).append(");");
        }
        else if (getName().contains("Атака")) {
            int damage = (int) parameters.getOrDefault("damage", 10);
            sb.append("attack(").append(damage).append(");");
        }
        else {
            sb.append("// Неизвестное действие");
        }

        return sb.toString();
    }

    // Фабричные методы
    public static ActionBlock createMoveRight(ObjectBlock executor, int steps) {
        return new ActionBlock("move_right", "Перемещение вправо",
                "Перемещение на " + steps + " шагов", executor)
                .withParameter("steps", steps)
                .withExecutionLogic((character, params) -> {
                    int s = (int) params.get("steps");
                    System.out.println(character.getName() + " перемещается вправо на " + s + " шагов");
                });
    }

    public static ActionBlock createAttack(ObjectBlock executor, int damage) {
        return new ActionBlock("attack", "Атака",
                "Атака с уроном " + damage, executor)
                .withParameter("damage", damage)
                .withExecutionLogic((character, params) -> {
                    int dmg = (int) params.get("damage");
                    System.out.println(character.getName() + " атакует с уроном " + dmg);
                });
    }
}
