package pt.ipvc.rastreio.sistemaderastreio.backend;

public class userStd extends user{

    public userStd(String name, String username, String password) {
        super(name, username, password);
    }

    public userStd(String name, String username, String password, int numberPhone, int numOfWork, typeUser tipoUser) {
        super(name, username, password, numberPhone, numOfWork, tipoUser);
    }

}
