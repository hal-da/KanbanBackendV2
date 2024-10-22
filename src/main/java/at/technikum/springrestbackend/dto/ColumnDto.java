package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class ColumnDto  extends BaseModelDto{

    private String id;
    @NotBlank
    private String title;
    private int order;
    private List<TaskDto> tasks;
    private int wipLimit;

    public ColumnDto(
            String id,
            String title,
            int order,
            List<TaskDto> tasks,
            int wipLimit,
            Date createdAt,
            Date lastChangeAt,
            PublicUserDto createdBy,
            PublicUserDto lastChangeBy) {
        super(createdAt, lastChangeAt, createdBy, lastChangeBy);
        this.id = id;
        this.title = title;
        this.order = order;
        this.tasks = tasks;
        this.wipLimit = wipLimit;
    }

    public ColumnDto() {
    }

}
