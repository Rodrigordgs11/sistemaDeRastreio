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
}


