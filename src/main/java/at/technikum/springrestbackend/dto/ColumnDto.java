package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;

public class ColumnDto {

    private String id;
    @NotBlank
    private String title;
    private int order;

    public ColumnDto(String id, String title, int order) {
        this.id = id;
        this.title = title;
        this.order = order;
    }

    public ColumnDto() {
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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

    @Override
    public String toString() {
        return "ColumnDto{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", order=" + order +
                '}';
    }
}
