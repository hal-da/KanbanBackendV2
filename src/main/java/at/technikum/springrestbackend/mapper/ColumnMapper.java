package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.ColumnDto;
import at.technikum.springrestbackend.model.Column;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ColumnMapper {


    public Column toColumn(ColumnDto columnDto) {
        return new Column(
                columnDto.getId(),
                columnDto.getTitle(),
                columnDto.getOrder()
        );
    }

    public ColumnDto toDto(Column column) {
        return new ColumnDto(
                column.getId(),
                column.getTitle(),
                column.getOrder()
        );
    }

    public List<ColumnDto> toDtos(List<Column> columnEntities) {
        return columnEntities
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<Column> mapToColumns(List<ColumnDto> columnDtos) {
        return columnDtos
                .stream()
                .map(this::toColumn)
                .toList();
    }


}
