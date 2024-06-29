package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.List;

public class BoardDto {
    private String id;
    @NotBlank
    private String title;
    private List<ColumnDto> columns;

    private List<PublicUserDto> members;
    private List<PublicUserDto> admins;
    
    private Date createdAt;
    private Date lastChangeAt;


    public BoardDto(
            String id,
            String title,
            List<ColumnDto> columns,
            List<PublicUserDto> members,
            List<PublicUserDto> admins,
            Date createdAt,
            Date lastChangeAt) {
        this.id = id;
        this.title = title;
        this.columns = columns;
        this.members = members;
        this.admins = admins;
        this.createdAt = createdAt;
        this.lastChangeAt = lastChangeAt;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastChangeAt() {
        return lastChangeAt;
    }

    public void setLastChangeAt(Date lastChangeAt) {
        this.lastChangeAt = lastChangeAt;
    }
}
