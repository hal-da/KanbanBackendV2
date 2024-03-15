package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.ColumnDto;
import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.model.Board;
import at.technikum.springrestbackend.model.Column;
import at.technikum.springrestbackend.repository.ColumnRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

enum StandardColumnTypes {
    BACKLOG,
    TODO,
    IN_PROGRESS,
    DONE
}
@Service
public class ColumnService {

    private final ColumnRepository columnRepository;

    public ColumnService(ColumnRepository columnRepository) {
        this.columnRepository = columnRepository;
    }

    public ArrayList<Column> createStandardColumns(Board board) {
        ArrayList<Column> columns = new ArrayList<>();

        for (StandardColumnTypes type : StandardColumnTypes.values()) {
            columns.add(new Column(type.toString(), board, type.ordinal()));
        }
        return columns;
    }

    public List<Column> getAllColumns() {
        return columnRepository.findAll();
    }

    public Column save(Column column) {
        if(column.getId() == null) {
            column = new Column(column);
        }
        columnRepository.save(column);
        return columnRepository.findById(column.getId()).orElseThrow(EntityNotFoundException::new);
    }

    public void deleteColumn(String id) {
        columnRepository.deleteById(id);
    }

    public Column updateColumn(ColumnDto columnDto) {
        Column column = columnRepository.findById(columnDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        column.setTitle(columnDto.getTitle());
        // Tasks will follow
        columnRepository.save(column);
        return columnRepository.findById(column.getId()).orElseThrow(EntityNotFoundException::new);
    }

    public Column getColumnById(String id) {
        return columnRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Column> getColumnsByBoardId(String boardId) {
        return columnRepository.findByBoardId(boardId);
    }
}
