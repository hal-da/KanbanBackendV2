package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.exception.UserNotEnoughPrivileges;
import at.technikum.springrestbackend.model.Board;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveExistingBoard() throws EntityNotFoundException {
        // Arrange
        UserEntity user = new UserEntity();
        user.setId("userId");

        Board board = new Board();
        board.setId("boardId");
        board.setTitle("Existing Board");

        when(boardRepository.save(any(Board.class))).thenReturn(board);
        when(boardRepository.findById(anyString())).thenReturn(Optional.of(board));

        // Act
        Board savedBoard = boardService.save(board, user);

        // Assert
        assertEquals("Existing Board", savedBoard.getTitle());
        verify(boardRepository).save(board);
    }

    @Test
    void testFindById() {
        // Arrange
        Board board = new Board();
        board.setId("boardId");

        when(boardRepository.findById("boardId")).thenReturn(Optional.of(board));

        // Act
        Board foundBoard = boardService.findById("boardId");

        // Assert
        assertNotNull(foundBoard);
        assertEquals("boardId", foundBoard.getId());
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange
        when(boardRepository.findById("boardId")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> boardService.findById("boardId"));
    }

    @Test
    void testDelete() {
        // Act
        boardService.delete("boardId");

        // Assert
        verify(boardRepository).deleteById("boardId");
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Board> boards = new ArrayList<>();
        Board board1 = new Board();
        board1.setId("board1");
        Board board2 = new Board();
        board2.setId("board2");
        boards.add(board1);
        boards.add(board2);

        when(boardRepository.findAll()).thenReturn(boards);

        // Act
        List<Board> foundBoards = boardService.findAll();

        // Assert
        assertEquals(2, foundBoards.size());
        assertEquals("board1", foundBoards.get(0).getId());
        assertEquals("board2", foundBoards.get(1).getId());
    }

    @Test
    void testUpdateBoardNotFound() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setId("userId");

        Board board = new Board();

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> boardService.update(board, user));
    }
}
