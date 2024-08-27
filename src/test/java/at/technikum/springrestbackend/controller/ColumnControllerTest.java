package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.ColumnDto;
import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.mapper.ColumnMapper;
import at.technikum.springrestbackend.model.Board;
import at.technikum.springrestbackend.model.Column;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.service.BoardService;
import at.technikum.springrestbackend.service.ColumnService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class ColumnControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ColumnService columnService;

    @Mock
    private ColumnMapper columnMapper;

    @Mock
    private BoardService boardService;

    @InjectMocks
    private ColumnController columnController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(columnController).build();
    }

    @Test
    void testGetColumns() throws Exception {
        ColumnDto columnDto = new ColumnDto("column-id", "title", 1);
        when(columnService.getColumnsByBoardId(anyString())).thenReturn(Collections.singletonList(new Column("column-id", "title", 1)));
        when(columnMapper.toDtos(anyList())).thenReturn(Collections.singletonList(columnDto));

        mockMvc.perform(get("/boards/board-id/columns"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("column-id"))
                .andExpect(jsonPath("$[0].title").value("title"));
    }

    @Test
    void testGetColumn() throws Exception {
        ColumnDto columnDto = new ColumnDto("column-id", "title", 1);
        when(columnService.getColumnById(anyString())).thenReturn(new Column("column-id", "title", 1));
        when(columnMapper.toDto(any(Column.class))).thenReturn(columnDto);

        mockMvc.perform(get("/boards/board-id/columns/column-id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("column-id"))
                .andExpect(jsonPath("$.title").value("title"));
    }

    @Test
    void testUpdateColumn() throws Exception {
        ColumnDto columnDto = new ColumnDto("column-id", "title", 1);
        Column column = new Column("column-id", "title", 1);
        when(columnService.updateColumn(any(ColumnDto.class))).thenReturn(column);
        when(columnMapper.toDto(any(Column.class))).thenReturn(columnDto);

        mockMvc.perform(put("/boards/board-id/columns")
                        .contentType("application/json")
                        .content("{\"id\":\"column-id\", \"title\":\"title\", \"order\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("column-id"))
                .andExpect(jsonPath("$.title").value("title"));
    }

    @Test
    void testDeleteColumn() throws Exception {
        doNothing().when(columnService).deleteColumn(anyString());

        mockMvc.perform(delete("/boards/board-id/columns/column-id"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreateColumn() throws Exception {
        UserPrincipal userPrincipal = mock(UserPrincipal.class);
        when(userPrincipal.getUserId()).thenReturn("user-id");

        Board board = new Board("title");
        Column column = new Column("column-id", "title", 1);
        ColumnDto columnDto = new ColumnDto("column-id", "title", 1);

        when(boardService.findById(anyString())).thenReturn(board);
        when(columnService.getColumnById(anyString())).thenReturn(column);
        when(boardService.update(any(Board.class))).thenReturn(board);
        when(columnMapper.toDto(any(Column.class))).thenReturn(columnDto);

        mockMvc.perform(post("/boards/board-id/columns")
                        .contentType("application/json")
                        .content("{\"title\":\"title\", \"order\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("column-id"))
                .andExpect(jsonPath("$.title").value("title"));
    }
}
