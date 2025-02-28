package com.jordan.sqlJAVA.repository;

import com.jordan.sqlJAVA.db.Bdd;
import com.jordan.sqlJAVA.model.Category;
import com.jordan.sqlJAVA.model.Task;
import com.jordan.sqlJAVA.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private static final Connection connection = Bdd.getConnection();

    public static Task save(Task task) {
        Task newTask = null;
        try {
            String sql = "INSERT INTO task(title, content, status, user_id) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, task.getTitle());
            preparedStatement.setString(2, task.getContent());
            preparedStatement.setBoolean(3, task.isStatus());
            preparedStatement.setInt(4, task.getUser().getId());
            int nbrRows = preparedStatement.executeUpdate();
            if (nbrRows > 0) {
                newTask = task;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newTask;
    }

    public static boolean isExist(String title) {
        boolean exists = false;
        try {
            String sql = "SELECT id FROM task WHERE title = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            exists = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public static Task findBy(String title) {
        Task task = null;
        try {
            String sql = "SELECT id, title, content FROM task WHERE title = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                task = new Task(resultSet.getString("title"), resultSet.getString("content"), null);
                task.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }

    public static List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        try {
            String sql = "SELECT t.id, t.title, t.content, t.createAt, t.status, " +
                    "u.id AS user_id, u.firstname, u.lastname, " +
                    "GROUP_CONCAT(c.id) AS catId, " +
                    "GROUP_CONCAT(c.category_name) AS catName " +
                    "FROM task_category AS tc " +
                    "INNER JOIN task AS t ON tc.task_id = t.id " +
                    "INNER JOIN category AS c ON tc.category_id = c.id " +
                    "INNER JOIN users AS u ON t.user_id = u.id " +
                    "INNER JOIN roles AS r ON u.roles_id = r.id " +
                    "GROUP BY t.id;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("user_id"));
                user.setFirstname(resultSet.getString("firstname"));
                user.setLastname(resultSet.getString("lastname"));

                Task task = new Task(
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        user
                );
                task.setId(resultSet.getInt("id"));
                task.setCreateAt(resultSet.getDate("createAt"));
                task.setStatus(resultSet.getBoolean("status"));

                // Récupération des catégories
                String categoryNames = resultSet.getString("catName");
                if (categoryNames != null) {
                    for (String catName : categoryNames.split(",")) {
                        task.addCategory(new Category(catName));
                    }
                }
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

}
