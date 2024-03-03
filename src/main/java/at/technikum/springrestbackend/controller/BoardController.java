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
@RestController
@RequestMapping("boards")
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

    @GetMapping("/{id}")
    public BoardDto getBoard(@PathVariable String id) throws EntityNotFoundException {
        return boardMapper.toDto(boardService.findById(id));
    }

    @PutMapping
    public BoardDto updateBoard(@RequestBody @Valid BoardDto boardDto) {
        return boardMapper.toDto(boardService.update(boardMapper.toBoard(boardDto)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBoard(@PathVariable String id) {
        boardService.delete(id);
    }
}
