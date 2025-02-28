package com.jordan.sqlJAVA.repository;

import com.jordan.sqlJAVA.db.Bdd;
import com.jordan.sqlJAVA.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    private static final Connection connection = Bdd.getConnection();

    public static Category save(Category category) {
        Category newCategory = null;
        try {
            String sql = "INSERT INTO category(category_name) VALUE(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, category.getCategoryName());
            int nbrRows = preparedStatement.executeUpdate();
            if (nbrRows > 0) {
                newCategory = category;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newCategory;
    }

    public static boolean isExist(String categoryName) {
        boolean exists = false;
        try {
            String sql = "SELECT id FROM category WHERE category_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();
            exists = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public static Category findBy(String categoryName) {
        Category category = null;
        try {
            String sql = "SELECT id, category_name FROM category WHERE category_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                category = new Category(resultSet.getString("category_name"));
                category.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    public static List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try {
            String sql = "SELECT id, category_name FROM category";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category(resultSet.getString("category_name"));
                category.setId(resultSet.getInt("id"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
