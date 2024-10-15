package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.ColumnDto;
import at.technikum.springrestbackend.dto.PublicUserDto;
import at.technikum.springrestbackend.dto.TaskDto;
import at.technikum.springrestbackend.model.Column;
import at.technikum.springrestbackend.model.Task;
import at.technikum.springrestbackend.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@RequiredArgsConstructor
@Component
public class ColumnMapper {
    private final TaskMapper taskMapper;
    private final PublicUserMapper userMapper;

    public Column toColumn(ColumnDto columnDto, UserEntity user) {

        if(columnDto.getId() == null) {
            System.out.println("createNewColumn");
            return createNewColumn(columnDto, user);
        }

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

    private Column createNewColumn(ColumnDto columnDto, UserEntity user) {
        System.out.println("createNewColumn method" +  user.getId());
        Column column = new Column();
        column.setTitle(columnDto.getTitle());
        column.setOrder(columnDto.getOrder());
        column.setWipLimit(columnDto.getWipLimit());
        column.setCreatedAt(new Date());
        column.setLastChangeAt(new Date());
        column.setCreatedBy(user);
        column.setLastChangeBy(user);
        column.setTasks(new ArrayList<>());
        System.out.println("createNewColumn method created by " +  column.getCreatedBy().getId());
        return column;
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
