package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.BoardDto;
import at.technikum.springrestbackend.dto.ColumnDto;
import at.technikum.springrestbackend.dto.PublicUserDto;
import at.technikum.springrestbackend.model.Board;
import at.technikum.springrestbackend.model.Column;
import at.technikum.springrestbackend.model.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BoardMapper {

    private final ColumnMapper columnMapper;
    private final PublicUserMapper publicUserMapper;

    public BoardMapper(ColumnMapper columnMapper, PublicUserMapper publicUserMapper) {
        this.columnMapper = columnMapper;
        this.publicUserMapper = publicUserMapper;
    }

    public Board toBoard(BoardDto boardDto, UserEntity user) {

        if(boardDto.getId() == null) {
            return new Board(boardDto.getTitle(), user);
        }


        // sollte eigentlich nicht vorkommen, wird in eigener update funktion abgebildet
        // eventuell hier eine exception werfen

        List<Column> columnDtos = columnMapper.mapToColumns(boardDto.getColumns());
        List<UserEntity> members = new ArrayList<>();
        List<UserEntity> admins = new ArrayList<>();


        return new Board(
                boardDto.getId(),
                boardDto.getTitle(),
                columnDtos,
                members,
                admins,
                boardDto.getCreatedAt(),
                boardDto.getLastChangeAt()
        );

    }

    public BoardDto toDto(Board board) {

        List<ColumnDto> columnDtos = columnMapper.toDtos(board.getColumns());
        List<PublicUserDto> members = publicUserMapper.toPublicUserDtos(board.getMembers());
        List<PublicUserDto> admins = publicUserMapper.toPublicUserDtos(board.getAdmins());

        return new BoardDto(
                board.getId(),
                board.getTitle(),
                columnDtos,
                members,
                admins,
                board.getCreatedAt(),
                board.getLastChangeAt()
        );
    }
}
