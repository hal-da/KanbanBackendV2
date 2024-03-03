package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.model.Column;
import at.technikum.springrestbackend.repository.ColumnRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColumnService {

    private final ColumnRepository columnRepository;

    public ColumnService(ColumnRepository columnRepository) {
        this.columnRepository = columnRepository;
    }

    public List<Column> createStandardColumns() {
        return List.of(
                new Column("Backlog"),
                new Column("To Do"),
                new Column("In Progress"),
                new Column("Done")
        );
    }

    public List<Column> getAllColumns() {
        return columnRepository.findAll();
    }

    public Column createColumn(Column column) {
        return columnRepository.save(column);
    }

    public void deleteColumn(String id) {
        columnRepository.deleteById(id);
    }

    public Column updateColumn(Column column) {
        return columnRepository.save(column);
    }

    public Column getColumnById(String id) {
        return columnRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
