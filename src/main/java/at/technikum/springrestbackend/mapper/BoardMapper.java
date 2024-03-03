package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.BoardDto;
import at.technikum.springrestbackend.dto.ColumnDto;
import at.technikum.springrestbackend.model.Board;
import at.technikum.springrestbackend.model.Column;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BoardMapper {

    private final ColumnMapper columnMapper;

    public BoardMapper(ColumnMapper columnMapper) {
        this.columnMapper = columnMapper;
    }

    public Board toBoard(BoardDto boardDto) {

        if(boardDto.getId() == null) {
            return new Board(boardDto.getTitle());
        }

        List<Column> columnEntityDtos = columnMapper.mapToColumns(boardDto.getColumns());


        return new Board(
                boardDto.getId(),
                boardDto.getTitle(),
                columnEntityDtos
        );

    }

    public BoardDto toDto(Board board) {

        List<ColumnDto> columnDtos = columnMapper.toDtos(board.getColumns());

        return new BoardDto(
                board.getId(),
                board.getTitle(),
                columnDtos
        );
    }
}
