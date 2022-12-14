package pt.ipvc.rastreio.sistemaderastreio.backend;

import java.time.LocalTime;
import java.util.Date;

public class Task {
    private int idTask = 1;
    private int idUser;
    private String description;
    private Date startTime;
    private Date endTime;
    private TaskState state;
    private static int numTasks = 0;
    private String projectName;
    private float priceProject;

    private int idProject;

    private long duration;

    public Task(String description, TaskState state, int idUser){
        this.idTask = ++numTasks;
        this.description = description;
        this.startTime = new Date();
        this.endTime = new Date();
        this.state = state;
        this.idUser = idUser;
        duration = 0;
    }

    public Task(String description, TaskState state, Date startTime, Date endTime, int idUser, int idProject){
        this.idTask = ++numTasks;
        this.description = description;
        this.state = state;
        this.startTime = startTime;
        this.endTime = endTime;
        this.idUser = idUser;
        this.idProject = idProject;
        duration = 0;
    }
    public int getIdTask() {
        return idTask;
    }
    public void setIdTask(int idTask) {
        this.idTask = idTask;
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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public float getPriceProject() {
        return priceProject;
    }

    public void setPriceProject(float priceProject) {
        this.priceProject = priceProject;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    @Override
    public String toString() {
        return "Task{" +
                "idTask=" + idTask +
                ", idUser=" + idUser +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", state=" + state +
                ", projectName='" + projectName + '\'' +
                ", priceProject=" + priceProject +
                ", idProject=" + idProject +
                ", duration=" + duration +
                '}';
    }
}


