package at.technikum.springrestbackend.controller;


import at.technikum.springrestbackend.dto.ColumnDto;
import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.mapper.ColumnMapper;
import at.technikum.springrestbackend.model.Board;
import at.technikum.springrestbackend.model.Column;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.service.BoardService;
import at.technikum.springrestbackend.service.ColumnService;
import at.technikum.springrestbackend.service.UserService;
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
    private final UserService userService;

    @GetMapping
    public List<ColumnDto> getColumns(@PathVariable String boardId) {
        List<ColumnDto> columnDtos = columnMapper
                .toDtos(columnService.getColumnsByBoardId(boardId));
        System.out.println("getColumns");
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


    @PutMapping("/{columnId}")
    public ColumnDto updateColumn(
            @RequestBody @Valid ColumnDto columnDto, @PathVariable String columnId) {
        columnDto.setId(columnId);
        System.out.println("updateColumn");
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
        System.out.println("createColumn " + columnDto);
        Board board = boardService.findById(boardId);
        System.out.println("createColumn board id   " + boardId);
        if(!board.idInAdminsOrMembers(userPrincipal.getUserId())){
            throw new RuntimeException("User is not allowed to create a column");
        }
        System.out.println("get user " + userPrincipal.getUserId());
        UserEntity user = userService.findById(userPrincipal.getUserId());
        System.out.println("createColumn " + user.getId());
        System.out.println("userId " + user.getId());
        Column column = columnMapper.toColumn(columnDto, user);
        column.setBoard(board);
        Column c = columnService.save(column);
        System.out.println("createColumn before seinding ");
        return columnMapper.toDto(c);
    }
}
