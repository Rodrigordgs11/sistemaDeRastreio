package pt.ipvc.rastreio.sistemaderastreio.backend;

import java.time.LocalTime;
import java.util.Date;

public class Task {
    private String description;
    private Date startTime;
    private TaskState state;
    private int numTasks;

    public Task(String description, TaskState state){
        this.description = description;
        this.startTime = new Date();
        this.state = state;
        this.idUser = idUser;
    }

    public Task(String description, TaskState state, Date startTime, Date endTime, int idUser){
        this.description = description;
        this.state = state;
        this.startTime = startTime;
        this.endTime = endTime;
        this.idUser = idUser;
    }

    public int getidUser() {
        return idUser;
    }

    public void setidUser(int idUser) {
        this.idUser = idUser;
    }

    public int getNumTasks() {
        return numTasks;
    }

    public void setNumTasks(int numTasks) {
        this.numTasks = numTasks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }
}


