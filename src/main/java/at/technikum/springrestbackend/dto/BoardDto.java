package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
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

    public void addColumn(ColumnDto column) {
        this.columns.add(column);
    }

    public void removeColumn(ColumnDto column) {
        this.columns.remove(column);
    }

}
