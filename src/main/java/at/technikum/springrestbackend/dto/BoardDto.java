package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class BoardDto extends BaseModelDto {
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
            List<PublicUserDto> admins,
            Date createdAt,
            Date lastChangeAt,
            PublicUserDto createdBy,
            PublicUserDto lastChangeBy) {

        super(createdAt, lastChangeAt, createdBy, lastChangeBy);
        this.id = id;
        this.title = title;
        this.columns = columns;
        this.members = members;
        this.admins = admins;
    }

    @Override
    public String toString() {
        return "BoardDto{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", columns=" + columns +
                ", members=" + members +
                ", admins=" + admins +
                ", createdAt=" + createdAt +
                ", lastChangeAt=" + lastChangeAt +
                '}';
    }

}
