package at.technikum.springrestbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
public class Board extends BaseModel {

//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private String id;

    private String title;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "board")
    private List<Column> columns;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "members_boards",
            joinColumns = @JoinColumn(name = "boards_id"),
            inverseJoinColumns = @JoinColumn(name = "user_entity_id")
    )
    private List<UserEntity> members;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "admins_boards",
            joinColumns = @JoinColumn(name = "boards_id"),
            inverseJoinColumns = @JoinColumn(name = "user_entity_id")
    )
    private List<UserEntity> admins;


    public Board() {
    }
    public Board(
            String id,
            String title,
            List<Column> columns,
            List<UserEntity> members,
            List<UserEntity> admins,
            Date createdAt,
            Date lastChangeAt,
            UserEntity createdBy,
            UserEntity lastChangeBy) {
        this.id = id;
        this.title = title;
        this.columns = columns;
        this.members = members;
        this.admins = admins;
        this.createdAt = createdAt;
        this.lastChangeAt = lastChangeAt;
        this.createdBy = createdBy;
        this.lastChangeBy = lastChangeBy;
    }

//    empty new board
    public Board(String title, UserEntity user) {
        super();
        this.title = title;
        this.columns = new ArrayList<>();
        this.members = new ArrayList<>();
        this.admins = new ArrayList<>();
        Date now = new Date();
        this.createdAt = now;
        this.lastChangeAt = now;
        this.createdBy = user;
        this.lastChangeBy = user;
    }


    public void addColumn(Column column) {
        this.columns.add(column);
    }

    public Column addColumn(String title) {
        Column column = new Column(title, this, this.columns.getLast().getOrder() + 1);
        this.columns.add(column);
        return column;
    }

    public void addMember(UserEntity user) {
        this.members.add(user);
    }

    public void addAdmin(UserEntity user) {
        this.admins.add(user);
    }

    public void removeColumn(Column column) {
        this.columns.remove(column);
    }

    public void removeMember(UserEntity user) {
        this.members.remove(user);
    }

    public void removeAdmin(UserEntity user) {
        this.admins.remove(user);
    }

    public boolean idInAdmins(String id) {
        for (UserEntity user : this.admins) {
            if (user.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean idInMembers(String id) {
        for (UserEntity user : this.members) {
            if (user.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean idInAdminsOrMembers(String id) {
        return idInAdmins(id) || idInMembers(id);
    }

    @Override
    public String toString() {
        return "Board{" +
                "id='" + this.getId() + '\'' +
                ", title='" + title + '\'' +
                ", columns=" + columns +
                ", members=" + members +
                ", admins=" + admins +
                '}';
    }
}
