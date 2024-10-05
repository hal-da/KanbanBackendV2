package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.ColumnDto;
import at.technikum.springrestbackend.dto.TaskDto;
import at.technikum.springrestbackend.model.Column;
import at.technikum.springrestbackend.model.Task;
import at.technikum.springrestbackend.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@RequiredArgsConstructor
@Component
public class ColumnMapper {
    private final TaskMapper taskMapper;

    public Column toColumn(ColumnDto columnDto, UserEntity user) {

        List<Task> tasks = taskMapper.toTasks(columnDto.getTasks(), user);

        return new Column(
                columnDto.getId(),
                columnDto.getTitle(),
                columnDto.getOrder(),
                tasks,
                columnDto.getWipLimit()
        );
    }

    public ColumnDto toDto(Column column) {

        List<TaskDto> taskDtos = taskMapper.toDtos(column.getTasks());

        return new ColumnDto(
                column.getId(),
                column.getTitle(),
                column.getOrder(),
                taskDtos,
                column.getWipLimit()
        );
    }

    public List<ColumnDto> toDtos(List<Column> columnEntities) {
        return columnEntities
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<Column> toColumns(List<ColumnDto> columnDtos, UserEntity user) {
        return columnDtos
                .stream()
                .map(columnDto -> toColumn(columnDto, user))
                .toList();
    }


}
