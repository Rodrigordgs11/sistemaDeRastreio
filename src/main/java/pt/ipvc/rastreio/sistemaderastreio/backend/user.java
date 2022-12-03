package pt.ipvc.rastreio.sistemaderastreio.backend;

import java.util.ArrayList;
import java.util.List;

public class user {
    public static ArrayList<user> users = new ArrayList<user>();
    private int id;
    private String name;
    private String username;
    private String password;
    private int numberPhone;
    private int numOfWork;

    public user(String name, String username, String password, int numberPhone){
        this.id += 1;
        this.name = name;
        this.username = username;
        this.password = password;
        this.numberPhone = numberPhone;
    }

    public void setUsers(ArrayList<user> users) {
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(int numberPhone) {
        this.numberPhone = numberPhone;
    }

    public int getNumOfWork() {
        return numOfWork;
    }

    public void setNumOfWork(int numOfWork) {
        this.numOfWork = numOfWork;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", numberPhone=" + numberPhone +
                ", numOfWork=" + numOfWork +
                '}';
    }
}
