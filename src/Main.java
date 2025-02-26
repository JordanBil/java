import com.jordan.sqlJAVA.db.Bdd;
import com.jordan.sqlJAVA.model.User;
import com.jordan.sqlJAVA.repository.UserRepository;

public class Main {
    public static void main(String[] args) {
        User newUser = new User(
                "Mathieu",
                "Mithridate",
                "mathieu1@gmail.com",
                "123456"
        );
        boolean exist = UserRepository.isExist("mathieu1@gmail.com");
        if(exist) {
            System.out.println("Le compte existe");
        }
        else {
            System.out.println("Le compte n'existe pas");
        }
    }
}