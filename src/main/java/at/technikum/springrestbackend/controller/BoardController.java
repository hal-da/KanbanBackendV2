package at.technikum.springrestbackend.controller;


import at.technikum.springrestbackend.dto.BoardDto;
import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.mapper.BoardMapper;
import at.technikum.springrestbackend.model.Board;
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

    public BoardController(BoardService boardService, BoardMapper boardMapper) {
        this.boardService = boardService;
        this.boardMapper = boardMapper;
    }


    @GetMapping
    public List<Board> getBoards(){
        return boardService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoardDto createBoard(@RequestBody @Valid BoardDto boardDto) throws EntityNotFoundException {
        return boardMapper.toDto(boardService.save(boardMapper.toBoard(boardDto)));
    }
}
