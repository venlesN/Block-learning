package org.example;

import org.example.blocks.*;
import org.example.visual_programming.blocks.ActionBlock;
import org.example.visual_programming.blocks.LoopBlock;
import org.example.visual_programming.blocks.ObjectBlock;
import org.example.visual_programming.blocks.conditions.SimpleCondition;
import org.example.data_manager.ProgressTracker;
import org.example.data_manager.ProjectManager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    //TODO:
    /* 1. Реализовать класс интерфейса. У меня будет несколько экранов - главное меню с переходом к уровням, песочнице и
        найстройкам, Экран выбора уровней, сами уровни, настройки и песочница
        2. Класс который интерпретирует уровень из баз данных
        3. База данных с информацией об уровнях
        4. И ЕЩЁ ДОХУЯ ВСЕГО
    * */
    public static void main(String[] args) {
        ObjectBlock hero = new ObjectBlock("hero", "Герой", "Главный герой", "Character");
        ObjectBlock enemy = new ObjectBlock("enemy", "Враг", "Злой монстр", "Enemy");

        // Создаем действия с исполнителями
        ActionBlock heroMove = ActionBlock.createMoveRight(hero, 3);
        ActionBlock enemyAttack = ActionBlock.createAttack(enemy, 15);

        // Создаем условие для цикла
        SimpleCondition condition = new SimpleCondition("heroIsAlive");

        // Создаем цикл while
        LoopBlock loop = new LoopBlock("mainLoop", "Основной цикл",
                "Цикл действий персонажей", "while", condition);
        loop.setChildren(List.of(heroMove, enemyAttack));

        // Выполняем
        System.out.println("=== Выполнение ===");
        loop.execute();

        // Генерируем код
        System.out.println("\n=== Генерация кода ===");
        System.out.println(hero.toJavaCode());
        System.out.println(enemy.toJavaCode());
        System.out.println(loop.toJavaCode());


        ProjectManager projectManager = new ProjectManager();
        ProjectManager.ProjectData project = new ProjectManager.ProjectData();

        // Добавляем блоки и соединения в проект
        project.getBlocks().put("move_right", heroMove);

        try {
            projectManager.saveProject("my_project", project);
            ProjectManager.ProjectData loaded = projectManager.loadProject("my_project");
            System.out.println("Project loaded with " + loaded.getBlocks().size() + " blocks");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Пример работы с ProgressTracker
        ProgressTracker progressTracker = new ProgressTracker();
        progressTracker.recordLevelCompletion(1, 1, 3, 1, 120, 0);
        progressTracker.unlockAchievement(1, "quick_learner");

        Map<String, Object> progress = progressTracker.getUserProgress(1);
        System.out.println("User progress: " + progress);

        progressTracker.close();
    }
}