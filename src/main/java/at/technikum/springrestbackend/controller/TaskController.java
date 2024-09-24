package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.TaskDto;
import at.technikum.springrestbackend.mapper.TaskMapper;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.service.TaskService;
import at.technikum.springrestbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("boards/{boardId}/columns/{columnId}/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final UserService userService;

    @GetMapping
    public List<TaskDto> getTasks(@PathVariable String columnId) {
        List<TaskDto> taskDtos = taskMapper
                .toDtos(taskService.getTasksByColumnId(columnId));
        System.out.println(taskDtos);
        return taskDtos;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(@RequestBody TaskDto taskDto,
                              @AuthenticationPrincipal UserPrincipal userPrincipal) {
        UserEntity user = userService.findById(userPrincipal.getUserId());
        return taskMapper.toDto(taskService.save(taskMapper.toTask(taskDto, user)));
    }

    @PutMapping
    public TaskDto updateTask(@RequestBody TaskDto taskDto,
                              @AuthenticationPrincipal UserPrincipal userPrincipal) {
        UserEntity user = userService.findById(userPrincipal.getUserId());
        return taskMapper.toDto(taskService.save(taskMapper.toTask(taskDto, user)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable String id) {
        taskService.delete(id);
    }





}
