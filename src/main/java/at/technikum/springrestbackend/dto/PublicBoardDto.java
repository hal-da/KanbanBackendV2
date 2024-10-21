package at.technikum.springrestbackend.dto;



import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class PublicBoardDto {

    private String id;
    private String title;
    private List<PublicUserDto> members;
    private List<PublicUserDto> admins;
    private Date createdAt;
    private Date lastChangeAt;
    private PublicUserDto createdBy;
    private PublicUserDto lastChangeBy;

    public PublicBoardDto(){}

    public PublicBoardDto(
            String id,
            String title,
            List<PublicUserDto> members,
            List<PublicUserDto> admins,
            Date createdAt,
            Date lastChangeAt,
            PublicUserDto createdBy,
            PublicUserDto lastChangeBy) {
        this.id = id;
        this.title = title;
        this.members = members;
        this.admins = admins;
        this.createdAt = createdAt;
        this.lastChangeAt = lastChangeAt;
        this.createdBy = createdBy;
        this.lastChangeBy = lastChangeBy;
    }
}
