package at.technikum.springrestbackend.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
public class CoulmnTest {

    private Column column;
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board("Project Board");
        column = new Column("To Do", board, 1);
    }

    @Test
    void testConstructorWithoutArgs() {
        Column emptyColumn = new Column();
        assertThat(emptyColumn.getId()).isNull();
        assertThat(emptyColumn.getTitle()).isNull();
        assertThat(emptyColumn.getBoard()).isNull();
        assertThat(emptyColumn.getOrder()).isZero();
    }

    @Test
    void testConstructorWithTitle() {
        Column titleColumn = new Column("In Progress");
        assertThat(titleColumn.getTitle()).isEqualTo("In Progress");
        assertThat(titleColumn.getBoard()).isNull();
        assertThat(titleColumn.getOrder()).isZero();
    }

    @Test
    void testConstructorWithIdAndTitleAndOrder() {
        Column specificColumn = new Column("column-id", "Done", 2);
        assertThat(specificColumn.getId()).isEqualTo("column-id");
        assertThat(specificColumn.getTitle()).isEqualTo("Done");
        assertThat(specificColumn.getOrder()).isEqualTo(2);
    }

    @Test
    void testConstructorWithIdTitleBoardAndOrder() {
        Column completeColumn = new Column("column-id", "Done", board, 2);
        assertThat(completeColumn.getId()).isEqualTo("column-id");
        assertThat(completeColumn.getTitle()).isEqualTo("Done");
        assertThat(completeColumn.getBoard()).isEqualTo(board);
        assertThat(completeColumn.getOrder()).isEqualTo(2);
    }

    @Test
    void testGettersAndSetters() {
        column.setId("column-id");
        column.setTitle("In Progress");
        column.setOrder(2);
        column.setBoard(board);

        assertThat(column.getId()).isEqualTo("column-id");
        assertThat(column.getTitle()).isEqualTo("In Progress");
        assertThat(column.getOrder()).isEqualTo(2);
        assertThat(column.getBoard()).isEqualTo(board);
    }

    @Test
    void testToString() {
        column.setId("column-id");
        String expected = "Column{id='column-id', title='To Do', board=" + board.toString() + '}';
        assertThat(column.toString()).isEqualTo(expected);
    }


}
