package pt.ipvc.rastreio.sistemaderastreio.backend;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private int idProject = 1;
    private String name;
    private String owner;
    private String clientName;
    private float pricePerHour;
    private List<Task> tasks;
    private static int numProjects = 0;
    private List<Integer> sharedUsers;

    public Project(String name, String clientName, float pricePerHour, String owner) {
        this.owner = owner;
        this.idProject = ++numProjects;
        this.name = name;
        this.clientName = clientName;
        this.pricePerHour = pricePerHour;
        this.tasks = new ArrayList<>();
        this.sharedUsers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getClientName() {
        return clientName;
    }
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    public float getPricePerHour() {
        return pricePerHour;
    }
    public void setPricePerHour(float pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
    public List<Task> getTasks() {
        return tasks;
    }
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    public int getIdProject() {
        return idProject;
    }
    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }
    public int getNumProjects() {
        return numProjects;
    }
    public void setNumProjects(int numProjects) {
        this.numProjects = numProjects;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public List<Integer> getSharedUsers() {
        return sharedUsers;
    }
    public void setSharedUsers(List<Integer> sharedUsers) {
        this.sharedUsers = sharedUsers;
    }
}
