package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.model.Board;
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

    public List<Column> createStandardColumns(Board board) {
        return List.of(
                new Column("Backlog", board),
                new Column("To Do", board),
                new Column("In Progress", board),
                new Column("Done", board)
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
