package at.technikum.springrestbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
public class Task extends BaseModel {
    private String title;
    private String description;
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "column_id")
    private Column column;

    public Task() {
    }

    public Task(Task t){
        super(t.getId());
        this.title = t.getTitle();
        this.description = t.getDescription();
        this.done = t.isDone();
        this.column = t.getColumn();
        this.lastChangeBy = t.getLastChangeBy();
        this.lastChangeAt = t.getLastChangeAt();
        this.createdBy = t.getCreatedBy();
        this.createdAt = t.getCreatedAt();
    }

    public Task(String id, String title, String description, boolean done, Column column) {
        super(id);
        this.title = title;
        this.description = description;
        this.done = done;
        this.column = column;
    }

    /**
     * Constructor for creating a new Task
     * @param title the title of the task
     * @param description the description of the task
     * @param column the column the task belongs to
     * @param user the user who created the task
     */
    public Task(String title, String description, Column column, UserEntity user) {
        this.title = title;
        this.description = description;
        this.column = column;
        this.createdAt = new Date();
        this.createdBy = user;
        this.lastChangeAt = new Date();
        this.lastChangeBy = user;
        this.done = false;
    }


    public Task(
            String id,
            String title,
            String description,
            boolean done,
            Column column,
            Date date,
            UserEntity user) {
        super(id);
        this.title = title;
        this.description = description;
        this.done = done;
        this.column = column;
        this.createdAt = date;
        this.createdBy = user;
        this.lastChangeAt = date;
        this.lastChangeBy = user;
    }


    @Override
    public String toString() {
        return "Task{" +
                "id=" + getId() +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", done=" + done +
                '}';
    }
}
