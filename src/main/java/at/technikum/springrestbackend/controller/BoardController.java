package at.technikum.springrestbackend.controller;


import at.technikum.springrestbackend.dto.BoardDto;
import at.technikum.springrestbackend.dto.PublicBoardDto;
import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.mapper.BoardMapper;
import at.technikum.springrestbackend.mapper.PublicBoardMapper;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.service.BoardService;
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
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final BoardMapper boardMapper;
    private final PublicBoardMapper publicBoardMapper;
    private final UserService userService;


    @GetMapping()
    public List<PublicBoardDto> getBoards(){
        System.out.println("getBoards");
        return publicBoardMapper.toPublicBoardDtos(boardService.findAll());
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public BoardDto createBoard(
            @RequestBody @Valid BoardDto boardDto,
            @AuthenticationPrincipal UserPrincipal userPrincipal)
            throws EntityNotFoundException {
        UserEntity user = userService.findByEmail(userPrincipal.getEmail());

        return boardMapper.toDto(boardService.save(boardMapper.toBoard(boardDto, user), user));
    }

    @GetMapping("/{id}")
    public BoardDto getBoard(@PathVariable String id) throws EntityNotFoundException {
        return boardMapper.toDto(boardService.findById(id));
    }

    @PutMapping
    public BoardDto updateBoard(@RequestBody @Valid BoardDto boardDto,
                                @AuthenticationPrincipal UserPrincipal userPrincipal)
            throws EntityNotFoundException {
        UserEntity user = userService.findByEmail(userPrincipal.getEmail());
        return boardMapper.toDto(boardService.save(boardMapper.toBoard(boardDto, user), user));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBoard(@PathVariable String id) {
        boardService.delete(id);
    }
}
