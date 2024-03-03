package at.technikum.springrestbackend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ColumnEntity> columnEntities;


    public BoardEntity() {
    }
    public BoardEntity(String id, String title, List<ColumnEntity> columnEntities) {
        this.id = id;
        this.title = title;
        this.columnEntities = columnEntities;
    }

    public BoardEntity(String title) {
        this.title = title;
        ColumnEntity column = new ColumnEntity("Backlog");
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

    public List<ColumnEntity> getColumns() {
        return columnEntities;
    }

    public void setColumns(List<ColumnEntity> columnEntities) {
        this.columnEntities = columnEntities;
    }
}
