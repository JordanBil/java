package com.jordan.sqlJAVA.repository;

import com.jordan.sqlJAVA.db.Bdd;
import com.jordan.sqlJAVA.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserRepository {
    /*
     * Attributs
     * */

    private static final Connection connection = Bdd.getConnection();

    /*
     * Méthodes (CRUD)
     * */
    //Méthode pour ajouter
    public static User save(User addUser) {
        //Créer un objet user
        User newUser = null;
        try {
            //Requête
            String sql = "INSERT INTO users(firstname, lastname, email, password) VALUE(?,?,?,?)";
            //Préparer la requête
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //Bind les paramètres
            preparedStatement.setString(1, addUser.getFirstname());
            preparedStatement.setString(2, addUser.getLastname());
            preparedStatement.setString(3, addUser.getEmail());
            preparedStatement.setString(4, addUser.getPassword());

            //Exécuter la requête
            int nbrRows = preparedStatement.executeUpdate();

            //vérifier si la requête est bien passé
            if(nbrRows > 0){
                newUser = new User(
                        addUser.getFirstname(),
                        addUser.getLastname(),
                        addUser.getEmail(),
                        addUser.getPassword()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newUser;
    }

    public static boolean isExist(String email) {
        boolean getUser = false;
        try {
            String sql = "SELECT id FROM users WHERE email = ?";
            //préparer la requête
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //Bind le paramètre
            preparedStatement.setString(1, email);
            //récupérer le resultat de la requête
            ResultSet resultSet = preparedStatement.executeQuery();

            //Vérification du résultat
            while(resultSet.next()){
                getUser = true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return getUser;
    }

    public static User findByEmail(String email) {
        User foundUser = null;
        try {
            // Requête SQL pour récupérer l'utilisateur avec cet email
            String sql = "SELECT id, firstname, lastname, email FROM users WHERE email = ?";
            // Préparer la requête
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email); // Bind du paramètre email

            // Exécuter la requête
            ResultSet resultSet = preparedStatement.executeQuery();

            // Vérification des résultats
            if (resultSet.next()) {
                // Récupération des données de l'utilisateur
                int id = resultSet.getInt("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String userEmail = resultSet.getString("email");

                // Création d'un objet User avec les valeurs récupérées
                foundUser = new User(id, firstname, lastname, userEmail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return foundUser; //
    }
}