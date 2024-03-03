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

    public Column(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public Column(String title) {
        this.title = title;


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

    @Override
    public String toString() {
        return "Column{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", board=" + board +
                '}';
    }
}
