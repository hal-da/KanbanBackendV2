package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.BoardDto;
import at.technikum.springrestbackend.dto.ColumnDto;
import at.technikum.springrestbackend.dto.PublicUserDto;
import at.technikum.springrestbackend.model.Board;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
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

        ArrayList<UserEntity> members = new ArrayList<>();
        ArrayList<UserEntity> admins = new ArrayList<>();

        for (PublicUserDto member : boardDto.getMembers()) {
            members.add(publicUserMapper.toUserEntity(member));
        }
        for (PublicUserDto admin : boardDto.getAdmins()) {
            admins.add(publicUserMapper.toUserEntity(admin));
        }

        Board board = boardService.findById(boardDto.getId());
        board.setTitle(boardDto.getTitle());
        board.setLastChangeAt(new Date());
        board.setLastChangeBy(user);
        board.setMembers(members);
        board.setAdmins(admins);
        return board;
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
                board.getLastChangeAt(),
                publicUserMapper.toPublicUserDto(board.getCreatedBy()),
                publicUserMapper.toPublicUserDto(board.getLastChangeBy())
        );
    }
}
