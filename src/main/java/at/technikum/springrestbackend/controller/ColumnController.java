package at.technikum.springrestbackend.controller;


import at.technikum.springrestbackend.dto.ColumnDto;
import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.mapper.ColumnMapper;
import at.technikum.springrestbackend.model.Board;
import at.technikum.springrestbackend.model.Column;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.service.BoardService;
import at.technikum.springrestbackend.service.ColumnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("boards/{boardId}/columns")
public class ColumnController {

    private final ColumnService columnService;
    private final ColumnMapper columnMapper;
    private final BoardService boardService;

    @GetMapping
    public List<ColumnDto> getColumns(@PathVariable String boardId) {
        List<ColumnDto> columnDtos = columnMapper
                .toDtos(columnService.getColumnsByBoardId(boardId));
        System.out.println(columnDtos);
        return columnDtos;
    }


    @GetMapping("/{columnId}")
    public ColumnDto getColumn(
            @PathVariable String columnId,
            @PathVariable String boardId)
            throws EntityNotFoundException {
        return columnMapper.toDto(columnService.getColumnById(columnId));
    }


    @PutMapping
    public ColumnDto updateColumn(
            @RequestBody @Valid ColumnDto columnDto,
            @PathVariable String boardId) {
        return columnMapper.toDto(columnService.updateColumn(columnDto));
    }

    @DeleteMapping("/{columnId}")
    public void deleteColumn(@PathVariable String columnId, @PathVariable String boardId) {
        columnService.deleteColumn(columnId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ColumnDto createColumn(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable String boardId,
            @RequestBody @Valid ColumnDto columnDto) {
        Board board = boardService.findById(boardId);
        if(!board.idInAdminsOrMembers(userPrincipal.getUserId())){
            throw new RuntimeException("User is not allowed to create a column");
        }
        Column column = board.addColumn(columnDto.getTitle());
        boardService.update(board);
        return columnMapper.toDto(columnService.getColumnById(column.getId()));
    }
}
