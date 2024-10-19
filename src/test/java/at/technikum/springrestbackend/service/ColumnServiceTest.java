package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.ColumnDto;
import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.model.Board;
import at.technikum.springrestbackend.model.Column;
import at.technikum.springrestbackend.repository.ColumnRepository;
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

class ColumnServiceTest {

    @Mock
    private ColumnRepository columnRepository;

    @InjectMocks
    private ColumnService columnService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateStandardColumns() {
        // Arrange
        Board board = new Board();
        board.setId("boardId");

        // Act
        ArrayList<Column> columns = columnService.createStandardColumns(board);

        // Assert
        assertEquals(StandardColumnTypes.values().length, columns.size());
        for (int i = 0; i < columns.size(); i++) {
            assertEquals(StandardColumnTypes.values()[i].toString(), columns.get(i).getTitle());
            assertEquals(i, columns.get(i).getOrder());
            assertEquals(board, columns.get(i).getBoard());
        }
    }

    @Test
    void testGetAllColumns() {
        // Arrange
        List<Column> columns = new ArrayList<>();
        columns.add(new Column());
        when(columnRepository.findAll()).thenReturn(columns);

        // Act
        List<Column> result = columnService.getAllColumns();

        // Assert
        assertEquals(1, result.size());
        verify(columnRepository).findAll();
    }

    @Test
    void testSave() {
        // Arrange
        Column column = new Column();
        column.setId("columnId");

        when(columnRepository.save(any(Column.class))).thenReturn(column);
        when(columnRepository.findById("columnId")).thenReturn(Optional.of(column));

        // Act
        Column savedColumn = columnService.save(column);

        // Assert
        assertNotNull(savedColumn);
        assertEquals("columnId", savedColumn.getId());
        verify(columnRepository).save(column);
    }

    @Test
    void testSaveThrowsEntityNotFoundException() {
        // Arrange
        Column column = new Column();
        column.setId("columnId");

        when(columnRepository.save(any(Column.class))).thenReturn(column);
        when(columnRepository.findById("columnId")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> columnService.save(column));
    }

    @Test
    void testDeleteColumn() {
        // Act
        columnService.deleteColumn("columnId");

        // Assert
        verify(columnRepository).deleteById("columnId");
    }

    @Test
    void testUpdateColumn() {
        // Arrange
        ColumnDto columnDto = new ColumnDto();
        columnDto.setId("columnId");
        columnDto.setTitle("Updated Title");
        columnDto.setOrder(2);
        columnDto.setWipLimit(5);

        Column column = new Column();
        column.setId("columnId");

        when(columnRepository.findById("columnId")).thenReturn(Optional.of(column));

        // Act
        Column updatedColumn = columnService.updateColumn(columnDto);

        // Assert
        assertEquals("Updated Title", updatedColumn.getTitle());
        assertEquals(2, updatedColumn.getOrder());
        assertEquals(5, updatedColumn.getWipLimit());
        verify(columnRepository).save(column);
    }

    @Test
    void testUpdateColumnThrowsEntityNotFoundException() {
        // Arrange
        ColumnDto columnDto = new ColumnDto();
        columnDto.setId("nonExistentId");

        when(columnRepository.findById("nonExistentId")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> columnService.updateColumn(columnDto));
    }

    @Test
    void testGetColumnById() {
        // Arrange
        Column column = new Column();
        column.setId("columnId");

        when(columnRepository.findById("columnId")).thenReturn(Optional.of(column));

        // Act
        Column foundColumn = columnService.getColumnById("columnId");

        // Assert
        assertNotNull(foundColumn);
        assertEquals("columnId", foundColumn.getId());
    }

    @Test
    void testGetColumnByIdThrowsEntityNotFoundException() {
        // Arrange
        when(columnRepository.findById("nonExistentId")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> columnService.getColumnById("nonExistentId"));
    }

    @Test
    void testGetColumnsByBoardId() {
        // Arrange
        List<Column> columns = new ArrayList<>();
        columns.add(new Column());
        when(columnRepository.findByBoardId("boardId")).thenReturn(columns);

        // Act
        List<Column> result = columnService.getColumnsByBoardId("boardId");

        // Assert
        assertEquals(1, result.size());
        verify(columnRepository).findByBoardId("boardId");
    }
}

