package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.model.Task;
import at.technikum.springrestbackend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import at.technikum.springrestbackend.exception.EntityNotFoundException;

import java.util.List;


@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public void delete(String id){
        taskRepository.deleteById(id);
    }

    public Task findById(String id){
        return taskRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Task save(Task task){
        Task t = taskRepository.save(task);
        return taskRepository.findById(t.getId()).orElseThrow(EntityNotFoundException::new);
    }

    public List<Task> getTasksByColumnId(String columnId){
        return taskRepository.findByColumnId(columnId);
    }




}
