package org.example.data_manager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.visual_programming.blocks.Block;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ProjectManager {
    private static final String PROJECTS_DIR = "projects";
    private static final String CACHE_DIR = "cache";
    private final Gson gson;

    public ProjectManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        initDirectories();
    }

    private void initDirectories() {
        new File(PROJECTS_DIR).mkdirs();
        new File(CACHE_DIR).mkdirs();
    }

    public void saveProject(String projectId, ProjectData project) throws IOException {
        String json = gson.toJson(project);
        Files.write(Paths.get(PROJECTS_DIR, projectId + ".json"), json.getBytes());
    }

    public ProjectData loadProject(String projectId) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(PROJECTS_DIR, projectId + ".json")));
        return gson.fromJson(content, ProjectData.class);
    }

    public void deleteProject(String projectId) {
        File file = new File(PROJECTS_DIR, projectId + ".json");
        if (file.exists()) {
            file.delete();
        }
    }

    public void saveExecutionCache(String cacheId, byte[] data) throws IOException {
        Files.write(Paths.get(CACHE_DIR, cacheId + ".bin"), data);
    }

    public byte[] loadExecutionCache(String cacheId) throws IOException {
        return Files.readAllBytes(Paths.get(CACHE_DIR, cacheId + ".bin"));
    }

    // Внутренний класс для хранения данных проекта
    public static class ProjectData {
        private Map<String, Block> blocks;
        private Map<String, Connection> connections;
        private Map<String, String> metadata;

        public ProjectData() {
            this.blocks = new HashMap<>();
            this.connections = new HashMap<>();
            this.metadata = new HashMap<>();
        }

        // Геттеры и сеттеры
        public Map<String, Block> getBlocks() { return blocks; }
        public Map<String, Connection> getConnections() { return connections; }
        public Map<String, String> getMetadata() { return metadata; }
    }

    // Классы для структуры проекта

}
