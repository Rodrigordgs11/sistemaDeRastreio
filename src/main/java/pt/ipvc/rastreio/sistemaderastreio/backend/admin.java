package pt.ipvc.rastreio.sistemaderastreio.backend;
import pt.ipvc.rastreio.sistemaderastreio.backend.user;

public class admin extends user{

    public admin(String name, String username, String password) {
        super(name, username, password);
    }

    public admin(String name, String username, String password, int numberPhone, int numOfWork, typeUser tipoUser) {
        super(name, username, password, numberPhone, numOfWork, tipoUser);
    }
}
