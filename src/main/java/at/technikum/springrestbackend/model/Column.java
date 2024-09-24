package at.technikum.springrestbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Column {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Getter
    private int order;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "column")
    private List<Task> tasks;


    public Column() {
    }

    public Column(Column column) {
        this.id = column.getId();
        this.title = column.getTitle();
        this.board = column.getBoard();
        this.order = column.getOrder();
        this.tasks = column.getTasks();
    }

    public Column(String title) {
        this.title = title;
        this.tasks = new ArrayList<>();
    }

    public Column(String id, String title, int order, List<Task> tasks) {
        this.id = id;
        this.title = title;
        this.order = order;
        this.tasks = tasks;
    }

    public Column(String title, Board board, int order, List<Task> tasks) {
        this.title = title;
        this.board = board;
        this.order = order;
        this.tasks = tasks;
    }

    public Column(String id, String title, Board board, int order, List<Task> tasks) {
        this.id = id;
        this.title = title;
        this.board = board;
        this.order = order;
        this.tasks = tasks;
    }

    public Column(String string, Board board, int ordinal) {
        this.title = string;
        this.board = board;
        this.order = ordinal;
        this.tasks = new ArrayList<>();
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
