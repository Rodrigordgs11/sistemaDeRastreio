package pt.ipvc.rastreio.sistemaderastreio.backend;

import java.util.ArrayList;
import java.util.List;

import static pt.ipvc.rastreio.sistemaderastreio.Data.data.users;

public abstract class user {
    public user.typeUser tipoUser;
    private int id = 1;
    private String name;
    private String username;
    private String password;
    private int numberPhone;
    private int numOfWork;
    private List <Integer> projects;
    private List<Task> tasks;
    private static int numUsers = 0;

    public enum typeUser{
        admin,
        userManager,
        userStd
    }

    public user(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public user(String name, String username, String password, int numberPhone, int numOfWork, typeUser tipoUser) {
        this.id = ++numUsers;
        this.name = name;
        this.username = username;
        this.password = password;
        this.numberPhone = numberPhone;
        this.numOfWork = numOfWork;
        this.tipoUser = tipoUser;
        this.tasks = new ArrayList<>();
        this.projects = new ArrayList<>();
    }


    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setTipoUser(typeUser tipoUser){
        this.tipoUser = tipoUser;
    }

    public typeUser getTipoUser() {
        return tipoUser;
    }

    public int getId() {
        return id;
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

    public List<Integer> getProjects(){return this.projects;}

    public void setProjects(List<Integer> projects){this.projects = projects;}

    @Override
    public String toString() {
        return "user{" +
                "tipoUser=" + tipoUser +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", numberPhone=" + numberPhone +
                ", numOfWork=" + numOfWork +
                '}';
    }
}
