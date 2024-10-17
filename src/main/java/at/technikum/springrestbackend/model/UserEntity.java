package at.technikum.springrestbackend.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
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
    private String imageUrl;

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
        this.imageUrl = "";
    }

    public UserEntity(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.adminBoards = new ArrayList<>();
        this.memberBoards = new ArrayList<>();
        this.role = Role.MEMBER;
    }

    public UserEntity(String id, String email, String userName, String role, String imageUrl) {
        this.id = id;
        this.email = email;
        this.username = userName;
        this.role = Role.valueOf(role);
        this.imageUrl = imageUrl;
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

    // create Builder

    public static class Builder {
        private String username;
        private String password;
        private String email;
        private Role role;
        private String imageUrl;
        private List<Board> adminBoards;
        private List<Board> memberBoards;
        private Date createdAt;
        private Date lastChangeAt;

        public Builder() {
            this.imageUrl = "";
            this.adminBoards = new ArrayList<>();
            this.memberBoards = new ArrayList<>();
            this.role = Role.MEMBER;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withRole(Role role) {
            this.role = role;
            return this;
        }

        public Builder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder withAdminBoards(List<Board> adminBoards) {
            this.adminBoards = adminBoards;
            return this;
        }

        public Builder withMemberBoards(List<Board> memberBoards) {
            this.memberBoards = memberBoards;
            return this;
        }

        public Builder withCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder withLastChangeAt(Date lastChangeAt) {
            this.lastChangeAt = lastChangeAt;
            return this;
        }

        public UserEntity build() {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(this.username);
            userEntity.setPassword(this.password);
            userEntity.setEmail(this.email);
            userEntity.setRole(this.role);
            userEntity.setImageUrl(this.imageUrl);
            userEntity.setAdminBoards(this.adminBoards);
            userEntity.setMemberBoards(this.memberBoards);
            userEntity.setCreatedAt(this.createdAt);
            userEntity.setLastChangeAt(this.lastChangeAt);
            return userEntity;
        }
    }
}
