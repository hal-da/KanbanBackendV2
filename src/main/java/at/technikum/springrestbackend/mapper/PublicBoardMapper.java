package at.technikum.springrestbackend.mapper;


import at.technikum.springrestbackend.dto.PublicBoardDto;
import at.technikum.springrestbackend.dto.PublicUserDto;
import at.technikum.springrestbackend.model.Board;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PublicBoardMapper {

    private final PublicUserMapper publicUserMapper;

    public PublicBoardMapper(PublicUserMapper publicUserMapper) {
        this.publicUserMapper = publicUserMapper;
    }

    public PublicBoardDto toPublicBoardDto(Board board) {

        List<PublicUserDto> members = publicUserMapper.toPublicUserDtos(board.getMembers());
        List<PublicUserDto> admins = publicUserMapper.toPublicUserDtos(board.getAdmins());

        return new PublicBoardDto(
                board.getId(),
                board.getTitle(),
                members,
                admins,
                board.getCreatedAt(),
                board.getLastChangeAt(),
                publicUserMapper.toPublicUserDto(board.getCreatedBy()),
                publicUserMapper.toPublicUserDto(board.getLastChangeBy())
        );
    }

    public List<PublicBoardDto> toPublicBoardDtos(List<Board> boards) {
        return boards.stream().map(this::toPublicBoardDto).toList();
    }

}
