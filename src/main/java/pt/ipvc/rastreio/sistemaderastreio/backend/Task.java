package pt.ipvc.rastreio.sistemaderastreio.backend;

import java.time.LocalTime;
import java.util.Date;

public class Task {
    private String description;
    private Date startTime;
    private TaskState state;

    public Task(String description, TaskState state){
        this.description = description;
        this.startTime = new Date();
        this.state = state;
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


