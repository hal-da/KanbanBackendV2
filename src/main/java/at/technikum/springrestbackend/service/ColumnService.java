package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.ColumnDto;
import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.model.Board;
import at.technikum.springrestbackend.model.Column;
import at.technikum.springrestbackend.repository.ColumnRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColumnService {

    private final ColumnRepository columnRepository;

    public ColumnService(ColumnRepository columnRepository) {
        this.columnRepository = columnRepository;
    }

    public ArrayList<Column> createStandardColumns(Board board) {
        ArrayList<Column> columns = new ArrayList<>();

        for (StandardColumnTypes type : StandardColumnTypes.values()) {
            columns.add(new Column(
                    type.toString(),
                    board,
                    type.ordinal(),
                    new ArrayList<>(),
                    2,
                    board.getCreatedAt(),
                    board.getCreatedAt(),
                    board.getCreatedBy(),
                    board.getLastChangeBy()
            ));
        }
        return columns;
    }

    public List<Column> getAllColumns() {
        return columnRepository.findAll();
    }

    public Column save(Column column) {
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
        column.setOrder(columnDto.getOrder());
        column.setWipLimit(columnDto.getWipLimit());
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
