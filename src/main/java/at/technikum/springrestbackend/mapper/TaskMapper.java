package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.PublicUserDto;
import at.technikum.springrestbackend.dto.TaskDto;
import at.technikum.springrestbackend.model.Column;
import at.technikum.springrestbackend.model.Task;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.service.ColumnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final ColumnService columnService;
    private final PublicUserMapper publicUserMapper;

    public Task toTask(TaskDto taskDto, UserEntity user) {

        Column column = columnService.getColumnById(taskDto.getColumnId());

        if(column == null) {
            throw new IllegalArgumentException("Column with id " + taskDto.getColumnId() + " not found");
        }

        if(taskDto.getId() == null) {
            return new Task(
                    taskDto.getTitle(),
                    taskDto.getDescription(),
                    column,
                    user
            );
        }

        return new Task(
                taskDto.getId(),
                taskDto.getTitle(),
                taskDto.getDescription(),
                taskDto.isDone(),
                column,
                new Date(),
                user
        );
    }

    public TaskDto toDto(Task task) {
        PublicUserDto createdBy = publicUserMapper.toPublicUserDto(task.getCreatedBy());
        PublicUserDto lastChangeBy = publicUserMapper.toPublicUserDto(task.getLastChangeBy());


        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCreatedAt(),
                task.getLastChangeAt(),
                createdBy,
                lastChangeBy,
                task.isDone(),
                task.getColumn().getId()
        );
    }

    public List<Task> toTasks(List<TaskDto> taskDtos, UserEntity user) {
        return taskDtos.stream().map(taskDto -> toTask(taskDto, user)).toList();
    }

    public List<TaskDto> toDtos(List<Task> tasks) {
        return tasks.stream().map(this::toDto).toList();
    }

}
