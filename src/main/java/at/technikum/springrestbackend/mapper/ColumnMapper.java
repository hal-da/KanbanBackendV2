package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.ColumnDto;
import at.technikum.springrestbackend.dto.PublicUserDto;
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
    private final PublicUserMapper userMapper;

    public Column toColumn(ColumnDto columnDto, UserEntity user) {

        List<Task> tasks = taskMapper.toTasks(columnDto.getTasks(), user);

        UserEntity createdBy = userMapper.toUserEntity(columnDto.getCreatedBy());
        UserEntity lastChangeBy = userMapper.toUserEntity(columnDto.getLastChangeBy());

        return new Column(
                columnDto.getId(),
                columnDto.getTitle(),
                columnDto.getOrder(),
                tasks,
                columnDto.getWipLimit(),
                columnDto.getCreatedAt(),
                columnDto.getLastChangeAt(),
                createdBy,
                lastChangeBy

        );
    }

    public ColumnDto toDto(Column column) {

        List<TaskDto> taskDtos = taskMapper.toDtos(column.getTasks());
        PublicUserDto createdBy = userMapper.toPublicUserDto(column.getCreatedBy());
        PublicUserDto lastChangeBy = userMapper.toPublicUserDto(column.getLastChangeBy());


        return new ColumnDto(
                column.getId(),
                column.getTitle(),
                column.getOrder(),
                taskDtos,
                column.getWipLimit(),
                column.getCreatedAt(),
                column.getLastChangeAt(),
                createdBy,
                lastChangeBy
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
