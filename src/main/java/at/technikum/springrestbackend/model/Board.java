package at.technikum.springrestbackend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Column> columnEntities;


    public Board() {
    }
    public Board(String id, String title, List<Column> columnEntities) {
        this.id = id;
        this.title = title;
        this.columnEntities = columnEntities;
    }

    public Board(String title) {
        this.title = title;
        Column column = new Column("Backlog");
        this.columnEntities = new ArrayList<>();
        this.columnEntities.add(column);
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

    public List<Column> getColumns() {
        return columnEntities;
    }

    public void setColumns(List<Column> columnEntities) {
        this.columnEntities = columnEntities;
    }
}
