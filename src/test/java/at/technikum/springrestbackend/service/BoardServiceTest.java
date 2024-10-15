package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.exception.UserNotEnoughPrivileges;
import at.technikum.springrestbackend.model.Board;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    private Board board;
    private UserEntity user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create a sample user
        user = new UserEntity();
        user.setId("user1");
        user.setUsername("testuser");

        // Create a sample board
        board = new Board("Test Board", user);
        board.setId("board1");
        board.addAdmin(user);
        board.addMember(user);
    }

    @Test
    void testSave_NewBoard_Success() {
        // Arrange
        when(boardRepository.save(any(Board.class))).thenReturn(board);
        when(boardRepository.findById("board1")).thenReturn(Optional.of(board));

        // Act
        Board savedBoard = boardService.save(board, user);

        // Assert
        assertEquals("board1", savedBoard.getId());
        assertEquals("Test Board", savedBoard.getTitle());
        assertTrue(savedBoard.getAdmins().contains(user));
        verify(boardRepository, times(1)).save(board);
    }

    @Test
    void testSave_ExistingBoard_Success() {
        // Arrange
        board.setId("board1"); // simulate an existing board
        when(boardRepository.save(any(Board.class))).thenReturn(board);
        when(boardRepository.findById("board1")).thenReturn(Optional.of(board));

        // Act
        Board savedBoard = boardService.save(board, user);

        // Assert
        assertEquals("board1", savedBoard.getId());
        verify(boardRepository, times(1)).save(board);
    }

    @Test
    void testFindById_Success() {
        // Arrange
        when(boardRepository.findById("board1")).thenReturn(Optional.of(board));

        // Act
        Board foundBoard = boardService.findById("board1");

        // Assert
        assertEquals("board1", foundBoard.getId());
        verify(boardRepository, times(1)).findById("board1");
    }

    @Test
    void testDelete() {
        // Arrange
        doNothing().when(boardRepository).deleteById("board1");

        // Act
        boardService.delete("board1");

        // Assert
        verify(boardRepository, times(1)).deleteById("board1");
    }


    @Test
    void testUpdate_Success() throws EntityNotFoundException {
        // Arrange
        when(boardRepository.findById("board1")).thenReturn(Optional.of(board));
        when(boardRepository.save(any(Board.class))).thenReturn(board);

        // Act
        Board updatedBoard = boardService.update(board, user);

        // Assert
        assertEquals("board1", updatedBoard.getId());
        verify(boardRepository, times(1)).save(board);
    }

}
