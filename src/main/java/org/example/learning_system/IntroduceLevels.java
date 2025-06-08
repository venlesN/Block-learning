package org.example.learning_system;

import java.util.ArrayList;
import java.util.List;

public class IntroduceLevels {
    public static List<Level> createInitialLevels() {
        List<Level> levels = new ArrayList<>();

        // Уровень 1: Переменные
        BlockSolution solutionlvl1 = new BlockSolution();
        Hint hint1 = new Hint(1, "Выберите блок Объект");
        List<String> step1 = new ArrayList<>();
        step1.add("Найдите оранжевый модуль - объекты");
        step1.add("Выберите элемент object");
        Task task1 = new Task(
                "Объявите переменную типа int с именем 'count' и присвойте ей значение 10",
                solutionlvl1,
                hint1,
                "Пример",
                step1,
                Difficulty.EASY
        );
        levels.add(new Level(1,
                "Теория о переменных в Java: переменные хранят данные в памяти. " +
                        "Для объявления используется синтаксис: тип имя = значение;",
                task1,
                "Герой стоит перед дверью с надписью 'Переменные'",
                Difficulty.EASY)
        );

        // Уровень 2: Условные операторы


        return levels;
    }
}
