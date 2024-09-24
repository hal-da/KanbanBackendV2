package at.technikum.springrestbackend.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThat;



public class BoardTest {

    private Board board;
    private UserEntity user1;
    private UserEntity user2;
    private Column column1;
    private Column column2;

    @BeforeEach
    void setUp() {
        board = new Board();
        user1 = new UserEntity("user1", "password1", "user1@example.com");
        user2 = new UserEntity("user2", "password2", "user2@example.com");
        column1 = new Column("To Do", board, 1);
        column2 = new Column("In Progress", board, 2);
    }

    @Test
    void testConstructor() {
        assertThat(board.getTitle()).isEqualTo("Project Board");
        assertThat(board.getColumns()).isEmpty();
        assertThat(board.getMembers()).isEmpty();
        assertThat(board.getAdmins()).isEmpty();
    }

    @Test
    void testAddColumn() {
        board.addColumn(column1);
        assertThat(board.getColumns()).containsExactly(column1);

        board.addColumn(column2);
        assertThat(board.getColumns()).containsExactly(column1, column2);
    }

    @Test
    void testRemoveColumn() {
        board.addColumn(column1);
        board.addColumn(column2);
        assertThat(board.getColumns()).containsExactly(column1, column2);

        board.removeColumn(column1);
        assertThat(board.getColumns()).containsExactly(column2);
    }

    @Test
    void testAddMember() {
        board.addMember(user1);
        assertThat(board.getMembers()).containsExactly(user1);

        board.addMember(user2);
        assertThat(board.getMembers()).containsExactly(user1, user2);
    }

    @Test
    void testRemoveMember() {
        board.addMember(user1);
        board.addMember(user2);
        assertThat(board.getMembers()).containsExactly(user1, user2);

        board.removeMember(user1);
        assertThat(board.getMembers()).containsExactly(user2);
    }

    @Test
    void testAddAdmin() {
        board.addAdmin(user1);
        assertThat(board.getAdmins()).containsExactly(user1);

        board.addAdmin(user2);
        assertThat(board.getAdmins()).containsExactly(user1, user2);
    }

    @Test
    void testRemoveAdmin() {
        board.addAdmin(user1);
        board.addAdmin(user2);
        assertThat(board.getAdmins()).containsExactly(user1, user2);

        board.removeAdmin(user1);
        assertThat(board.getAdmins()).containsExactly(user2);
    }

    @Test
    void testIdInAdmins() {
        board.addAdmin(user1);
        assertThat(board.idInAdmins(user1.getId())).isTrue();
        assertThat(board.idInAdmins(user2.getId())).isFalse();
    }

    @Test
    void testIdInMembers() {
        board.addMember(user1);
        assertThat(board.idInMembers(user1.getId())).isTrue();
        assertThat(board.idInMembers(user2.getId())).isFalse();
    }

    @Test
    void testIdInAdminsOrMembers() {
        board.addAdmin(user1);
        board.addMember(user2);
        assertThat(board.idInAdminsOrMembers(user1.getId())).isTrue();
        assertThat(board.idInAdminsOrMembers(user2.getId())).isTrue();
    }

    @Test
    void testToString() {
        board.addAdmin(user1);
        board.addMember(user2);
        board.addColumn(column1);

        String expected = "Board{id='" + board.getId() + '\'' +
                ", title='Project Board'" +
                ", columns=" + board.getColumns() +
                ", members=" + board.getMembers() +
                ", admins=" + board.getAdmins() +
                '}';

        assertThat(board.toString()).isEqualTo(expected);
    }
}
