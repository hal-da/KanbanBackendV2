package at.technikum.springrestbackend.controller;


import at.technikum.springrestbackend.dto.BoardDto;
import at.technikum.springrestbackend.dto.PublicBoardDto;
import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.mapper.BoardMapper;
import at.technikum.springrestbackend.mapper.PublicBoardMapper;
import at.technikum.springrestbackend.service.BoardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController("/boards")
public class BoardController {

    private final BoardService boardService;
    private final BoardMapper boardMapper;

    private final PublicBoardMapper publicBoardMapper;

    public BoardController(
            BoardService boardService,
            BoardMapper boardMapper,
            PublicBoardMapper publicBoardMapper) {
        this.boardService = boardService;
        this.boardMapper = boardMapper;
        this.publicBoardMapper = publicBoardMapper;
    }


    @GetMapping
    public List<PublicBoardDto> getBoards(){
        return publicBoardMapper.toPublicBoardDtos(boardService.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoardDto createBoard(@RequestBody @Valid BoardDto boardDto)
            throws EntityNotFoundException {
        return boardMapper.toDto(boardService.save(boardMapper.toBoard(boardDto)));
    }

    @GetMapping("/{boardId}")
    public BoardDto getBoard(@PathVariable String boardId) throws EntityNotFoundException {
        return boardMapper.toDto(boardService.findById(boardId));
    }
}
