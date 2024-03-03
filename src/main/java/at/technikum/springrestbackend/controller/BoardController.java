package at.technikum.springrestbackend.controller;


import at.technikum.springrestbackend.dto.BoardDto;
import at.technikum.springrestbackend.mapper.BoardMapper;
import at.technikum.springrestbackend.model.BoardEntity;
import at.technikum.springrestbackend.service.BoardEntityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController("/boards")
public class BoardController {

    private final BoardEntityService boardService;
    private final BoardMapper boardMapper;

    public BoardController(BoardEntityService boardService, BoardMapper boardMapper) {
        this.boardService = boardService;
        this.boardMapper = boardMapper;
    }


    @GetMapping
    public List<BoardEntity> getBoards(){
        return boardService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoardDto createBoard(@RequestBody @Valid BoardDto boardDto){
        BoardEntity newBoard = new BoardEntity(boardDto.getTitle());
        newBoard = boardService.save(newBoard);
        return boardMapper.toDto(newBoard);
    }
}
