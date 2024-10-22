package at.technikum.springrestbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Column extends BaseModel {

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

    private int wipLimit;


    public Column() {
    }

    public Column(
            String id,
            String title,
            int order,
            List<Task> tasks,
            int wipLimit,
            Date createdAt,
            Date lastChangeAt,
            UserEntity createdBy,
            UserEntity lastChangeBy) {
        this.id = id;
        this.title = title;
        this.order = order;
        this.tasks = tasks;
        this.wipLimit = wipLimit;
        this.createdAt = createdAt;
        this.lastChangeAt = lastChangeAt;
        this.createdBy = createdBy;
        this.lastChangeBy = lastChangeBy;
    }

    public Column(
            String title,
            Board board,
            int order,
            List<Task> tasks,
            int wipLimit,
            Date createdAt,
            Date lastChangeAt,
            UserEntity createdBy,
            UserEntity lastChangeBy) {
        this.title = title;
        this.board = board;
        this.order = order;
        this.tasks = tasks;
        this.wipLimit = wipLimit;
        this.createdAt = createdAt;
        this.lastChangeAt = lastChangeAt;
        this.createdBy = createdBy;
        this.lastChangeBy = lastChangeBy;
    }
}
