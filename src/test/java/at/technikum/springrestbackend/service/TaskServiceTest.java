package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.model.Task;
import at.technikum.springrestbackend.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDelete() {
        // Arrange
        String taskId = "123";

        // Act
        taskService.delete(taskId);

        // Assert
        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    void testFindById_Success() {
        // Arrange
        String taskId = "123";
        Task task = new Task();
        task.setId(taskId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // Act
        Task result = taskService.findById(taskId);

        // Assert
        assertNotNull(result);
        assertEquals(taskId, result.getId());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void testFindById_EntityNotFoundException() {
        // Arrange
        String taskId = "123";
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> taskService.findById(taskId));
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void testSave_Success() {
        // Arrange
        Task task = new Task();
        task.setId(UUID.randomUUID().toString());

        Task savedTask = new Task();
        savedTask.setId(task.getId());

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);
        when(taskRepository.findById(savedTask.getId())).thenReturn(Optional.of(savedTask));

        // Act
        Task result = taskService.save(task);

        // Assert
        assertNotNull(result);
        assertEquals(savedTask.getId(), result.getId());
        assertNotNull(task.getLastChangeAt());  // Ensure lastChangeAt is set
        verify(taskRepository, times(1)).save(task);
        verify(taskRepository, times(1)).findById(savedTask.getId());
    }

    @Test
    void testSave_EntityNotFoundException() {
        // Arrange
        Task task = new Task();
        task.setId(UUID.randomUUID().toString());

        Task savedTask = new Task();
        savedTask.setId(task.getId());

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);
        when(taskRepository.findById(savedTask.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> taskService.save(task));
        verify(taskRepository, times(1)).save(task);
        verify(taskRepository, times(1)).findById(savedTask.getId());
    }

    @Test
    void testGetTasksByColumnId_Success() {
        // Arrange
        String columnId = "col-123";
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());

        when(taskRepository.findByColumnId(columnId)).thenReturn(tasks);

        // Act
        List<Task> result = taskService.getTasksByColumnId(columnId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findByColumnId(columnId);
    }
}

