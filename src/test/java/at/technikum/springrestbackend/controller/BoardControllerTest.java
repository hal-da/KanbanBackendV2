package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.BoardDto;
import at.technikum.springrestbackend.dto.PublicBoardDto;
import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.mapper.BoardMapper;
import at.technikum.springrestbackend.mapper.PublicBoardMapper;
import at.technikum.springrestbackend.model.Board;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.service.BoardService;
import at.technikum.springrestbackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

class BoardControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BoardService boardService;

    @Mock
    private BoardMapper boardMapper;

    @Mock
    private PublicBoardMapper publicBoardMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private BoardController boardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
    }

    @Test
    void testGetBoards() throws Exception {
        PublicBoardDto publicBoardDto = new PublicBoardDto("board-id", "title", Collections.emptyList(), Collections.emptyList());
        when(publicBoardMapper.toPublicBoardDtos(any())).thenReturn(Collections.singletonList(publicBoardDto));
        when(boardService.findAll()).thenReturn(Collections.singletonList(new Board("title")));

        mockMvc.perform(get("/boards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("board-id"))
                .andExpect(jsonPath("$[0].title").value("title"));
    }

    @Test
    void testCreateBoard() throws Exception {
        BoardDto boardDto = new BoardDto("board-id", "title", Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), null, null);
        UserEntity user = new UserEntity("username", "password", "user@example.com");
        Board board = new Board("title");
        when(userService.findByEmail(anyString())).thenReturn(user);
        when(boardMapper.toBoard(any(BoardDto.class))).thenReturn(board);
        when(boardService.save(any(Board.class), any(UserEntity.class))).thenReturn(board);
        when(boardMapper.toDto(any(Board.class))).thenReturn(boardDto);

        mockMvc.perform(post("/boards")
                        .contentType("application/json")
                        .content("{\"title\":\"title\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("board-id"))
                .andExpect(jsonPath("$.title").value("title"));
    }

    @Test
    void testGetBoard() throws Exception {
        BoardDto boardDto = new BoardDto("board-id", "title", Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), null, null);
        when(boardService.findById(anyString())).thenReturn(new Board("title"));
        when(boardMapper.toDto(any(Board.class))).thenReturn(boardDto);

        mockMvc.perform(get("/board-id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("board-id"))
                .andExpect(jsonPath("$.title").value("title"));
    }

    @Test
    void testUpdateBoard() throws Exception {
        BoardDto boardDto = new BoardDto("board-id", "title", Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), null, null);
        Board board = new Board("title");
        when(boardMapper.toBoard(any(BoardDto.class))).thenReturn(board);
        when(boardService.update(any(Board.class))).thenReturn(board);
        when(boardMapper.toDto(any(Board.class))).thenReturn(boardDto);

        mockMvc.perform(put("/boards")
                        .contentType("application/json")
                        .content("{\"id\":\"board-id\", \"title\":\"title\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("board-id"))
                .andExpect(jsonPath("$.title").value("title"));
    }

    @Test
    void testDeleteBoard() throws Exception {
        doNothing().when(boardService).delete(anyString());

        mockMvc.perform(delete("/board-id"))
                .andExpect(status().isNoContent());
    }
}
