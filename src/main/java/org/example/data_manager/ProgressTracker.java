package org.example.data_manager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ProgressTracker {
    private static final String DB_NAME = "course_project.db";
    private Connection connection;

    public ProgressTracker() {
        connect();
        initDatabase();
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    private void initDatabase() {
        String sqlUsers = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE NOT NULL, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

        String sqlProgress = "CREATE TABLE IF NOT EXISTS progress (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER NOT NULL, " +
                "level_id INTEGER NOT NULL, " +
                "stars INTEGER NOT NULL, " +
                "attempts INTEGER NOT NULL, " +
                "time_spent INTEGER, " +
                "hints_used INTEGER, " +
                "completed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY(user_id) REFERENCES users(id))";

        String sqlAchievements = "CREATE TABLE IF NOT EXISTS achievements (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER NOT NULL, " +
                "achievement_id TEXT NOT NULL, " +
                "unlocked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY(user_id) REFERENCES users(id))";

        String sqlStatistics = "CREATE TABLE IF NOT EXISTS statistics (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER NOT NULL, " +
                "total_levels_completed INTEGER DEFAULT 0, " +
                "total_stars INTEGER DEFAULT 0, " +
                "total_time_spent INTEGER DEFAULT 0, " +
                "last_activity TIMESTAMP, " +
                "FOREIGN KEY(user_id) REFERENCES users(id))";

        executeUpdate(sqlUsers);
        executeUpdate(sqlProgress);
        executeUpdate(sqlAchievements);
        executeUpdate(sqlStatistics);
    }

    private void executeUpdate(String sql) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute SQL: " + sql, e);
        }
    }

    public void recordLevelCompletion(int userId, int levelId, int stars, int attempts,
                                      long timeSpent, int hintsUsed) {
        String sql = "INSERT INTO progress (user_id, level_id, stars, attempts, time_spent, hints_used) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, levelId);
            pstmt.setInt(3, stars);
            pstmt.setInt(4, attempts);
            pstmt.setLong(5, timeSpent);
            pstmt.setInt(6, hintsUsed);
            pstmt.executeUpdate();

            updateUserStatistics(userId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to record level completion", e);
        }
    }

    private void updateUserStatistics(int userId) {
        String sql = "UPDATE statistics SET " +
                "total_levels_completed = (SELECT COUNT(*) FROM progress WHERE user_id = ?), " +
                "total_stars = (SELECT SUM(stars) FROM progress WHERE user_id = ?), " +
                "total_time_spent = (SELECT SUM(time_spent) FROM progress WHERE user_id = ?), " +
                "last_activity = CURRENT_TIMESTAMP " +
                "WHERE user_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (int i = 1; i <= 4; i++) {
                pstmt.setInt(i, userId);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update statistics", e);
        }
    }

    public void unlockAchievement(int userId, String achievementId) {
        String sql = "INSERT OR IGNORE INTO achievements (user_id, achievement_id) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, achievementId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to unlock achievement", e);
        }
    }

    public Map<String, Object> getUserProgress(int userId) {
        Map<String, Object> progress = new HashMap<>();
        String sql = "SELECT level_id, stars, attempts, time_spent, hints_used " +
                "FROM progress WHERE user_id = ? ORDER BY completed_at";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> levelProgress = new HashMap<>();
                levelProgress.put("stars", rs.getInt("stars"));
                levelProgress.put("attempts", rs.getInt("attempts"));
                levelProgress.put("timeSpent", rs.getLong("time_spent"));
                levelProgress.put("hintsUsed", rs.getInt("hints_used"));

                progress.put("level_" + rs.getInt("level_id"), levelProgress);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get user progress", e);
        }

        return progress;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Failed to close database connection: " + e.getMessage());
        }
    }
}
