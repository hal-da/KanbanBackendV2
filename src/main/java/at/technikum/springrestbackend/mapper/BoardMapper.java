package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.BoardDto;
import at.technikum.springrestbackend.dto.ColumnDto;
import at.technikum.springrestbackend.model.BoardEntity;
import at.technikum.springrestbackend.model.ColumnEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BoardMapper {

    private final ColumnMapper columnMapper;

    public BoardMapper(ColumnMapper columnMapper) {
        this.columnMapper = columnMapper;
    }

    public BoardEntity mapToBoard(BoardDto boardDto) {

        if(boardDto.getId() == null) {
            return new BoardEntity(boardDto.getTitle());
        }

        List<ColumnEntity> columnEntityDtos = columnMapper.mapToColumns(boardDto.getColumns());


        return new BoardEntity(
                boardDto.getId(),
                boardDto.getTitle(),
                columnEntityDtos
        );

    }

    public BoardDto toDto(BoardEntity board) {

        List<ColumnDto> columnDtos = columnMapper.toDtos(board.getColumns());

        return new BoardDto(
                board.getId(),
                board.getTitle(),
                columnDtos
        );
    }
}
