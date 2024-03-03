package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;

public class ColumnDto {

    private String id;

    @NotBlank
    private String title;

    public ColumnDto(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public ColumnDto() {
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
}
