package at.technikum.springrestbackend.controller;


import at.technikum.springrestbackend.dto.ColumnDto;
import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.mapper.ColumnMapper;
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
    public ColumnController(ColumnService columnService, ColumnMapper columnMapper) {
        this.columnService = columnService;
        this.columnMapper = columnMapper;
    }


    @GetMapping("/{id}")
    public ColumnDto getColumn(@PathVariable String id)throws EntityNotFoundException {
        return columnMapper.toDto(columnService.getColumnById(id));
    }


    @PutMapping
    public ColumnDto updateColumn(@RequestBody @Valid ColumnDto columnDto) {
        return columnMapper.toDto(columnService.updateColumn(columnMapper.toColumn(columnDto)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteColumn(@PathVariable String id) {
        columnService.deleteColumn(id);
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public ColumnDto createColumn(@RequestBody @Valid ColumnDto columnDto) {
//        return columnMapper.toDto(columnService.saveColumn(columnMapper.toColumn(columnDto)));
//    }
}
