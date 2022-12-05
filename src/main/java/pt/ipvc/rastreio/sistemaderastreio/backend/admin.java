package pt.ipvc.rastreio.sistemaderastreio.backend;
import pt.ipvc.rastreio.sistemaderastreio.backend.user;

public class admin extends user{
    int idAdmin;

    public admin(String name, String username, String password, int idAdmin) {
        super(name, username, password);
        this.idAdmin = idAdmin;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    @Override
    public String toString() {
        return "admin{" +
                "idAdmin=" + idAdmin +
                '}';
    }
}
