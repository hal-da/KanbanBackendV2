package at.technikum.springrestbackend.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class UserEntity extends BaseModel{

    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private Role role;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "admins_boards",
            joinColumns = @JoinColumn(name = "user_entity_id"),
            inverseJoinColumns = @JoinColumn(name = "boards_id")
    )
    private List<Board> adminBoards;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "members_boards",
            joinColumns = @JoinColumn(name = "user_entity_id"),
            inverseJoinColumns = @JoinColumn(name = "boards_id")
    )
    private List<Board> memberBoards;

    public UserEntity() {
    }

    public UserEntity(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.adminBoards = new ArrayList<>();
        this.memberBoards = new ArrayList<>();
        this.role = Role.MEMBER;
    }

    public UserEntity(String id, String email, String userName, String role) {
        this.id = id;
        this.email = email;
        this.username = userName;
        this.role = Role.valueOf(role);
    }


    public void addAdminBoard(Board board) {
        this.adminBoards.add(board);
    }

    public void addMemberBoard(Board board) {
        this.memberBoards.add(board);
    }

    public void removeAdminBoard(Board board) {
        this.adminBoards.remove(board);
    }

    public void removeMemberBoard(Board board) {
        this.memberBoards.remove(board);
    }

    public String toString() {
        return "UserEntity [" +
                "id=" + id + "," +
                "username=" + username + ", " +
                "password=" + password + ", " +
                "email=" + email + ", " +
                "adminBoards=" + adminBoards + ", " +
                "memberBoards=" + memberBoards + "]";
    }
}
