package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.BoardDto;
import at.technikum.springrestbackend.dto.ColumnDto;
import at.technikum.springrestbackend.dto.PublicUserDto;
import at.technikum.springrestbackend.model.Board;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BoardMapper {

    private final ColumnMapper columnMapper;
    private final PublicUserMapper publicUserMapper;
    private final BoardService boardService;


    public Board toBoard(BoardDto boardDto, UserEntity user) {

        if(boardDto.getId() == null) {
            return new Board(boardDto.getTitle(), user);
        }


        // sollte eigentlich nicht vorkommen, wird in eigener update funktion abgebildet
        // eventuell hier eine exception werfen

        Board board = boardService.findById(boardDto.getId());
        board.setTitle(boardDto.getTitle());
        System.out.println("board im mapper: " + board.getTitle());
//        board.setLastChangeAt(new Date());
//        board.setLastChangeBy(user);
        return board;
    }

    public BoardDto toDto(Board board) {

        System.out.println("board im mapper: " + board.getTitle());

        List<ColumnDto> columnDtos = columnMapper.toDtos(board.getColumns());
        System.out.println("columns: " + columnDtos);
        List<PublicUserDto> members = publicUserMapper.toPublicUserDtos(board.getMembers());
        System.out.println("members: " + members);
        List<PublicUserDto> admins = publicUserMapper.toPublicUserDtos(board.getAdmins());
        System.out.println("admins: " + admins);

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
