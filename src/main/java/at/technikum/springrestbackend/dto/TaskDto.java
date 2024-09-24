package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskDto {

    private String id;
    @NotBlank
    private String title;
    private String description;
    private Date createdAt;
    private Date lastChangeAt;
    private PublicUserDto createdBy;
    private PublicUserDto lastChangeBy;
    private Boolean done;
    private String columnId;

    public TaskDto() {
    }

    public TaskDto(String title) {
        this.title = title;
    }


    public TaskDto(
            String id,
            String title,
            String description,
            Date createdAt,
            Date lastChangeAt,
            PublicUserDto createdBy,
            PublicUserDto lastChangeBy,
            Boolean done,
            String columnId
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.lastChangeAt = lastChangeAt;
        this.createdBy = createdBy;
        this.lastChangeBy = lastChangeBy;
        this.done = done;
        this.columnId = columnId;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
