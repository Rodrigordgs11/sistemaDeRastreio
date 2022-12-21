package pt.ipvc.rastreio.sistemaderastreio.backend;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private String name;
    private String clientName;
    private float pricePerHour;
    private List<Task> tasks;

    public Project(String name, String clientName, float pricePerHour) {
        this.name = name;
        this.clientName = clientName;
        this.pricePerHour = pricePerHour;
        this.tasks = new ArrayList<>();
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
}
