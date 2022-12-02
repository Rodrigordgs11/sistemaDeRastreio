package pt.ipvc.rastreio.sistemaderastreio.backend;

public class user {
    private String name;
    private String username;
    private String password;
    private int numberPhone;
    private int numOfWork;

    public user(){}

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNumberPhone(int numberPhone) {
        this.numberPhone = numberPhone;
    }

    public void setNumOfWork(int numOfWork) {
        this.numOfWork = numOfWork;
    }
}
