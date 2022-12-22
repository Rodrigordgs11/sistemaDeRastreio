package pt.ipvc.rastreio.sistemaderastreio.backend;

public class userManager extends user{
    public userManager(String name, String username, String password) {
        super(name, username, password);
    }

    public userManager(String name, String username, String password, int numberPhone, int numOfWork, typeUser tipoUser) {
        super(name, username, password, numberPhone, numOfWork, tipoUser);
    }

}
