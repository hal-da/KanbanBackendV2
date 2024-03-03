package at.technikum.springrestbackend.model;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String username;
    private String password;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "admins_boards",
            joinColumns = @JoinColumn(name = "user_entity_id"),
            inverseJoinColumns = @JoinColumn(name = "boards_id")
    )
    private List<Board> adminBoards;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Board> getAdminBoards() {
        return adminBoards;
    }

    public void setAdminBoards(List<Board> adminBoards) {
        this.adminBoards = adminBoards;
    }

    public List<Board> getMemberBoards() {
        return memberBoards;
    }

    public void setMemberBoards(List<Board> memberBoards) {
        this.memberBoards = memberBoards;
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
}
