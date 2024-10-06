package at.technikum.springrestbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@MappedSuperclass
public abstract class BaseModel {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected String id;
    protected Date createdAt;
    protected Date lastChangeAt;
    @ManyToOne
    @JoinColumn(name = "created_by_id")
    protected UserEntity createdBy;
    @ManyToOne
    @JoinColumn(name = "last_change_by_id")
    protected UserEntity lastChangeBy;


    public BaseModel() {
        this.createdAt = new Date();
        this.lastChangeAt = new Date();
    }

    public BaseModel(String id, Date createdAt, Date lastChangeAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.lastChangeAt = lastChangeAt;
    }

    public BaseModel(String id){
        this.id = id;
    }
}
