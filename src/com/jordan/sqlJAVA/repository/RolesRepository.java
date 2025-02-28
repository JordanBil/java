package com.jordan.sqlJAVA.repository;

import com.jordan.sqlJAVA.db.Bdd;
import com.jordan.sqlJAVA.model.Roles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RolesRepository {
    private static final Connection connection = Bdd.getConnection();

    public static Roles save(Roles role) {
        Roles newRole = null;
        try {
            String sql = "INSERT INTO roles(roles_name) VALUE(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, role.getRolesName());
            int nbrRows = preparedStatement.executeUpdate();
            if (nbrRows > 0) {
                newRole = role;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newRole;
    }

    public static boolean isExist(String roleName) {
        boolean exists = false;
        try {
            String sql = "SELECT id FROM roles WHERE roles_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, roleName);
            ResultSet resultSet = preparedStatement.executeQuery();
            exists = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public static Roles findBy(String roleName) {
        Roles role = null;
        try {
            String sql = "SELECT id, roles_name FROM roles WHERE roles_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, roleName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                role = new Roles(resultSet.getString("roles_name"));
                role.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

    public static List<Roles> findAll() {
        List<Roles> roles = new ArrayList<>();
        try {
            String sql = "SELECT id, roles_name FROM roles";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Roles role = new Roles(resultSet.getString("roles_name"));
                role.setId(resultSet.getInt("id"));
                roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }
}
