package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.model.Task;
import at.technikum.springrestbackend.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        task = new Task();
        task.setId("1");
        task.setLastChangeAt(new Date());
        // Set other properties of the task as needed
    }

    @Test
    void testDelete() {
        // Arrange
        doNothing().when(taskRepository).deleteById("1");

        // Act
        taskService.delete("1");

        // Assert
        verify(taskRepository, times(1)).deleteById("1");
    }

    @Test
    void testFindById_Success() {
        // Arrange
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));

        // Act
        Task foundTask = taskService.findById("1");

        // Assert
        assertEquals("1", foundTask.getId());
        assertEquals(task.getLastChangeAt(), foundTask.getLastChangeAt());
    }

    @Test
    void testGetTasksByColumnId_Success() {
        // Arrange
        List<Task> tasks = List.of(task);
        when(taskRepository.findByColumnId("columnId")).thenReturn(tasks);

        // Act
        List<Task> result = taskService.getTasksByColumnId("columnId");

        // Assert
        assertEquals(1, result.size());
        assertEquals(task.getId(), result.get(0).getId());
    }
}

