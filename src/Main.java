import com.jordan.sqlJAVA.db.Bdd;
import com.jordan.sqlJAVA.model.User;
import com.jordan.sqlJAVA.repository.UserRepository;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        User newUser = new User("Thomas", "Shelbyte", "toto@gmail.com", "123");
        User newUser2 = new User("Viann", "Calambour", "Vianne@gmail.com", "123456");

        System.out.println(newUser);
        boolean exist = UserRepository.isExist("toto@gmail.com");
        if(exist) {
            System.out.println("Le compte existe");
        }
        else {
            System.out.println("Le compte n'existe pas");
        }

        User findEmail = UserRepository.findByEmail("toto@gmail.com");
        if(!findEmail.getEmail().equals("")){
            System.out.println("L'utilisateur existe déjà en bdd !");
        }else {
            System.out.println("L'utilisateur n'existe pas");
        }

        // findAll()
        List<User> findAll = UserRepository.findAll();
        System.out.println(findAll);

    }
}