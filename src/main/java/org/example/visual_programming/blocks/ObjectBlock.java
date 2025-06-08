package org.example.visual_programming.blocks;

import java.util.List;

public class ObjectBlock extends AbstractBlock {
    private String objectType;
    private List<String> properties;

    public ObjectBlock(String id, String name, String description, String objectType) {
        super(id, name, description);
        this.objectType = objectType;
    }

    @Override
    public void execute() {
        // Объекты обычно не выполняются, а используются другими блоками
        System.out.println("Using object: " + getName() + " of type " + objectType);
    }

    @Override
    public String toJavaCode() {
        // Генерация кода для создания объекта
        return "// Object: " + getName() + "\n" + objectType + " " + getId() + " = new " + objectType + "();";
    }

    // Геттеры и сеттеры для специфичных полей
    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public List<String> getProperties() {
        return properties;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties;
    }
}

