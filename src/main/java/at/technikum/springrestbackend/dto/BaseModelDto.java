package at.technikum.springrestbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BaseModelDto {
    protected Date createdAt;
    protected Date lastChangeAt;
    protected PublicUserDto createdBy;
    protected PublicUserDto lastChangeBy;

    public BaseModelDto() {
    }

    public BaseModelDto(Date createdAt, Date lastChangeAt, PublicUserDto createdBy, PublicUserDto lastChangeBy) {
        this.createdAt = createdAt;
        this.lastChangeAt = lastChangeAt;
        this.createdBy = createdBy;
        this.lastChangeBy = lastChangeBy;
    }
}
