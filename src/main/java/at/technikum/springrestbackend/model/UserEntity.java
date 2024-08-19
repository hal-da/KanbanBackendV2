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
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;

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
