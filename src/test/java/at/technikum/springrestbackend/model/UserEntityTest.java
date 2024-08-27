package at.technikum.springrestbackend.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;



public class UserEntityTest {

    private UserEntity userEntity;
    private Board board1;
    private Board board2;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity("testUser", "testPassword", "test@example.com");
        board1 = new Board();
        board2 = new Board();
    }

    @Test
    void testConstructor() {
        assertThat(userEntity.getUsername()).isEqualTo("testUser");
        assertThat(userEntity.getPassword()).isEqualTo("testPassword");
        assertThat(userEntity.getEmail()).isEqualTo("test@example.com");
        assertThat(userEntity.getAdminBoards()).isEmpty();
        assertThat(userEntity.getMemberBoards()).isEmpty();
    }

    @Test
    void testAddAdminBoard() {
        userEntity.addAdminBoard(board1);
        assertThat(userEntity.getAdminBoards()).containsExactly(board1);

        userEntity.addAdminBoard(board2);
        assertThat(userEntity.getAdminBoards()).containsExactly(board1, board2);
    }

    @Test
    void testAddMemberBoard() {
        userEntity.addMemberBoard(board1);
        assertThat(userEntity.getMemberBoards()).containsExactly(board1);

        userEntity.addMemberBoard(board2);
        assertThat(userEntity.getMemberBoards()).containsExactly(board1, board2);
    }

    @Test
    void testRemoveAdminBoard() {
        userEntity.addAdminBoard(board1);
        userEntity.addAdminBoard(board2);
        assertThat(userEntity.getAdminBoards()).containsExactly(board1, board2);

        userEntity.removeAdminBoard(board1);
        assertThat(userEntity.getAdminBoards()).containsExactly(board2);
    }

    @Test
    void testRemoveMemberBoard() {
        userEntity.addMemberBoard(board1);
        userEntity.addMemberBoard(board2);
        assertThat(userEntity.getMemberBoards()).containsExactly(board1, board2);

        userEntity.removeMemberBoard(board1);
        assertThat(userEntity.getMemberBoards()).containsExactly(board2);
    }

    @Test
    void testToString(){
        userEntity.addAdminBoard(board1);
        userEntity.addMemberBoard(board2);

        String expected = "UserEntity [id=null,username=testUser, password=testPassword, email=test@example.com, " +
                "adminBoards=" + userEntity.getAdminBoards() + ", " +
                "memberBoards=" + userEntity.getMemberBoards() + "]";
        assertThat(userEntity.toString()).isEqualTo(expected);
    }
}
