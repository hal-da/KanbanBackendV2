package at.technikum.springrestbackend.model;

import jakarta.persistence.*;

import java.util.Date;

@MappedSuperclass
public abstract class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected String id;
    protected Date createdAt;
//    private UserEntity createdBy;
    protected Date lastChangeAt;
//    private UserEntity lastChangeBy;

//
//    /**
//     * Constructor for creating a new ChangeLog
//     * @param createdBy the user who created the ChangeLog
//     */
//    public BaseModel(UserEntity createdBy) {
//        this.createdBy = createdBy;
//        this.createdAt = new Date();
//        this.lastChangeAt = new Date();
//        this.lastChangeBy = createdBy;
//    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }


    public Date getLastChangeAt() {
        return lastChangeAt;
    }

    public void setLastChangeAt(Date lastChangeAt) {
        this.lastChangeAt = lastChangeAt;
    }
}
