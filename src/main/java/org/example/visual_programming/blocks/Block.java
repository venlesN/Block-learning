package org.example.visual_programming.blocks;

import java.util.List;

public interface Block {
        // Базовые переменные, которые могут быть у всех блоков
        String getId();
        void setId(String id);
        String getName();
        void setName(String name);
        String getDescription();
        void setDescription(String description);

        // Основной метод выполнения блока
        void execute();

        // Метод для интерпретации блока в Java-код
        String toJavaCode();

        List<Block> getChildren();
        void setChildren(List<Block> children);
}
