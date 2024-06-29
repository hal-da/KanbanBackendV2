package at.technikum.springrestbackend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Board extends BaseModel {

//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private String id;

    private String title;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "board")
    private List<Column> columns;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "members_boards",
            joinColumns = @JoinColumn(name = "boards_id"),
            inverseJoinColumns = @JoinColumn(name = "user_entity_id")
    )
    private List<UserEntity> members;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
            Date lastChangeAt) {
        this.id = id;
        this.title = title;
        this.columns = columns;
        this.members = members;
        this.admins = admins;
        this.createdAt = createdAt;
        this.lastChangeAt = lastChangeAt;
    }

    public Board(String title) {
        super();
        this.title = title;
        this.columns = new ArrayList<>();
        this.members = new ArrayList<>();
        this.admins = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columnEntities) {
        this.columns = columnEntities;
    }

    public List<UserEntity> getMembers() {
        return members;
    }


    public void setMembers(List<UserEntity> members) {
        this.members = members;
    }

    public List<UserEntity> getAdmins() {
        return admins;
    }

    public void setAdmins(List<UserEntity> admins) {
        this.admins = admins;
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
