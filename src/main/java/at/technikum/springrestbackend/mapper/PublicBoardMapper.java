package at.technikum.springrestbackend.mapper;


import at.technikum.springrestbackend.dto.PublicBoardDto;
import at.technikum.springrestbackend.model.Board;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PublicBoardMapper {

    public PublicBoardDto toPublicBoardDto(Board board) {
        return new PublicBoardDto(board.getId(), board.getTitle());
    }

    public List<PublicBoardDto> toPublicBoardDtos(List<Board> boards) {
        return boards.stream().map(this::toPublicBoardDto).toList();
    }

}
