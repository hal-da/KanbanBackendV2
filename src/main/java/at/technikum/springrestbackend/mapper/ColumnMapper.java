package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.ColumnDto;
import at.technikum.springrestbackend.model.ColumnEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ColumnMapper {


    public ColumnEntity mapToColumn(ColumnDto columnDto) {
        return new ColumnEntity(
                columnDto.getId(),
                columnDto.getTitle()
        );

    }

    public ColumnDto toDto(ColumnEntity columnEntity) {
        return new ColumnDto(
                columnEntity.getId(),
                columnEntity.getTitle()
        );
    }

    public List<ColumnDto> toDtos(List<ColumnEntity> columnEntities) {
        return columnEntities
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<ColumnEntity> mapToColumns(List<ColumnDto> columnDtos) {
        return columnDtos
                .stream()
                .map(this::mapToColumn)
                .toList();
    }


}
