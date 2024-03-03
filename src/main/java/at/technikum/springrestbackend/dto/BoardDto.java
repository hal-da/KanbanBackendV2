package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class BoardDto {
    private String id;
    @NotBlank
    private String title;
    private List<ColumnDto> columns;

    private List<PublicUserDto> members;
    private List<PublicUserDto> admins;


    public BoardDto(
            String id,
            String title,
            List<ColumnDto> columns,
            List<PublicUserDto> members,
            List<PublicUserDto> admins) {
        this.id = id;
        this.title = title;
        this.columns = columns;
        this.members = members;
        this.admins = admins;
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

    public void addColumn(ColumnDto column) {
        this.columns.add(column);
    }

    public void removeColumn(ColumnDto column) {
        this.columns.remove(column);
    }

    public List<PublicUserDto> getMembers() {
        return members;
    }

    public void setMembers(List<PublicUserDto> members) {
        this.members = members;
    }

    public List<PublicUserDto> getAdmins() {
        return admins;
    }

    public void setAdmins(List<PublicUserDto> admins) {
        this.admins = admins;
    }
}
