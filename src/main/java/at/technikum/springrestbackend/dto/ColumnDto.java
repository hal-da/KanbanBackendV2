package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ColumnDto {

    private String id;
    @NotBlank
    private String title;
    private int order;
    private List<TaskDto> tasks;

    public ColumnDto(String id, String title, int order, List<TaskDto> tasks) {
        this.id = id;
        this.title = title;
        this.order = order;
        this.tasks = tasks;
    }

    public ColumnDto() {
    }

    @Override
    public String toString() {
        return "ColumnDto{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", order=" + order +
                '}';
    }
}
