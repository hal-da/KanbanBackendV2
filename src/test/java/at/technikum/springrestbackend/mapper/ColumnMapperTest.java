package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.ColumnDto;
import at.technikum.springrestbackend.model.Column;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnMapperTest {

    @InjectMocks
    private ColumnMapper columnMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToColumn() {
        // Given
        ColumnDto columnDto = new ColumnDto("1", "Test Column", 1);

        // When
        Column column = columnMapper.toColumn(columnDto);

        // Then
        assertThat(column.getId()).isEqualTo("1");
        assertThat(column.getTitle()).isEqualTo("Test Column");
        assertThat(column.getOrder()).isEqualTo(1);
    }

    @Test
    void testToDto() {
        // Given
        Column column = new Column("1", "Test Column", 1);

        // When
        ColumnDto columnDto = columnMapper.toDto(column);

        // Then
        assertThat(columnDto.getId()).isEqualTo("1");
        assertThat(columnDto.getTitle()).isEqualTo("Test Column");
        assertThat(columnDto.getOrder()).isEqualTo(1);
    }

    @Test
    void testToDtos() {
        // Given
        Column column1 = new Column("1", "Column 1", 1);
        Column column2 = new Column("2", "Column 2", 2);
        List<Column> columns = Arrays.asList(column1, column2);

        // When
        List<ColumnDto> columnDtos = columnMapper.toDtos(columns);

        // Then
        assertThat(columnDtos).hasSize(2);
        assertThat(columnDtos.get(0).getId()).isEqualTo("1");
        assertThat(columnDtos.get(0).getTitle()).isEqualTo("Column 1");
        assertThat(columnDtos.get(0).getOrder()).isEqualTo(1);
        assertThat(columnDtos.get(1).getId()).isEqualTo("2");
        assertThat(columnDtos.get(1).getTitle()).isEqualTo("Column 2");
        assertThat(columnDtos.get(1).getOrder()).isEqualTo(2);
    }

    @Test
    void testMapToColumns() {
        // Given
        ColumnDto columnDto1 = new ColumnDto("1", "Column 1", 1);
        ColumnDto columnDto2 = new ColumnDto("2", "Column 2", 2);
        List<ColumnDto> columnDtos = Arrays.asList(columnDto1, columnDto2);

        // When
        List<Column> columns = columnMapper.mapToColumns(columnDtos);

        // Then
        assertThat(columns).hasSize(2);
        assertThat(columns.get(0).getId()).isEqualTo("1");
        assertThat(columns.get(0).getTitle()).isEqualTo("Column 1");
        assertThat(columns.get(0).getOrder()).isEqualTo(1);
        assertThat(columns.get(1).getId()).isEqualTo("2");
        assertThat(columns.get(1).getTitle()).isEqualTo("Column 2");
        assertThat(columns.get(1).getOrder()).isEqualTo(2);
    }
}
