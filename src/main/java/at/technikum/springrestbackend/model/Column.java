package at.technikum.springrestbackend.model;

import jakarta.persistence.*;

@Entity
public class Column {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;


    public Column() {
    }

    public Column(String title) {
        this.title = title;
    }

    public Column(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public Column(String title, Board board) {
        this.title = title;
        this.board = board;
    }

    public Column(String id, String title, Board board) {
        this.id = id;
        this.title = title;
        this.board = board;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return "Column{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", board=" + board +
                '}';
    }
}
