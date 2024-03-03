package at.technikum.springrestbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Column {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;


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


}
