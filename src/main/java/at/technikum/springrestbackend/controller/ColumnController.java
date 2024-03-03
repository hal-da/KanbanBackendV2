package at.technikum.springrestbackend.controller;


import at.technikum.springrestbackend.dto.ColumnDto;
import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.mapper.ColumnMapper;
import at.technikum.springrestbackend.model.Board;
import at.technikum.springrestbackend.model.Column;
import at.technikum.springrestbackend.service.BoardService;
import at.technikum.springrestbackend.service.ColumnService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("columns")
public class ColumnController {

    private final ColumnService columnService;
    private final ColumnMapper columnMapper;
    private final BoardService boardService;
    public ColumnController(
            ColumnService columnService,
            ColumnMapper columnMapper,
            BoardService boardService) {
        this.columnService = columnService;
        this.columnMapper = columnMapper;
        this.boardService = boardService;
    }


    @GetMapping("/{id}")
    public ColumnDto getColumn(@PathVariable String id)throws EntityNotFoundException {
        return columnMapper.toDto(columnService.getColumnById(id));
    }


    @PutMapping
    public ColumnDto updateColumn(@RequestBody @Valid ColumnDto columnDto) {
        return columnMapper.toDto(columnService.updateColumn(columnDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteColumn(@PathVariable String id) {
        columnService.deleteColumn(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ColumnDto createColumn(@RequestBody @Valid ColumnDto columnDto) {
        Board board = boardService.findById(columnDto.getBoardId());
        Column column = board.addColumn(columnDto.getTitle());
        boardService.save(board);
        return columnMapper.toDto(columnService.getColumnById(column.getId()));
    }
}
