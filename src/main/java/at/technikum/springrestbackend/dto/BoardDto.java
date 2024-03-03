package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class BoardDto {
    private String id;

    @NotBlank
    private String title;

    private List<ColumnDto> columns;


    public BoardDto(String id, String title, List<ColumnDto> columns) {
        this.id = id;
        this.title = title;
        this.columns = columns;
    }

    public BoardDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ColumnDto> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnDto> columns) {
        this.columns = columns;
    }
}
