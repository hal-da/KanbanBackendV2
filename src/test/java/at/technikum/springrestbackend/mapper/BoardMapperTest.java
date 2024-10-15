package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.BoardDto;
import at.technikum.springrestbackend.dto.ColumnDto;
import at.technikum.springrestbackend.dto.PublicUserDto;
import at.technikum.springrestbackend.model.Board;
import at.technikum.springrestbackend.model.Column;
import at.technikum.springrestbackend.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class BoardMapperTest {

    @Mock
    private ColumnMapper columnMapper;

    @Mock
    private PublicUserMapper publicUserMapper;

    @InjectMocks
    private BoardMapper boardMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToBoard_withNullId() {
        // Given
        BoardDto boardDto = new BoardDto(null, "Test Board", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new Date(), new Date());
        // When
        UserEntity testUser = new UserEntity("Testuser", "Testpassword", "Testemail@test");
        Board board = boardMapper.toBoard(boardDto, testUser);

        // Then
        assertThat(board.getId()).isNull();
        assertThat(board.getTitle()).isEqualTo("Test Board");
        assertThat(board.getColumns()).isEmpty();
    }

    @Test
    void testToBoard_withNonNullId() {
        // Given
        List<ColumnDto> columnDtos = new ArrayList<>();
        List<Column> columns = new ArrayList<>();


        BoardDto boardDto = new BoardDto("1", "Test Board", columnDtos, new ArrayList<>(), new ArrayList<>(), new Date(), new Date());
        UserEntity userEntity = new UserEntity("Test Board", "Password test", "testemail@test.at");
        when(columnMapper.mapToColumns(columnDtos)).thenReturn(columns);

        // When
        Board board = boardMapper.toBoard(boardDto, userEntity);

        // Then
        assertThat(board.getId()).isEqualTo("1");
        assertThat(board.getTitle()).isEqualTo("Test Board");
        assertThat(board.getColumns()).isEqualTo(columns);
        assertThat(board.getMembers()).isEmpty();
        assertThat(board.getAdmins()).isEmpty();
    }

    @Test
    void testToDto() {
        // Given
        List<Column> columns = new ArrayList<>();
        List<ColumnDto> columnDtos = new ArrayList<>();
        List<UserEntity> members = new ArrayList<>();
        List<PublicUserDto> memberDtos = new ArrayList<>();
        List<UserEntity> admins = new ArrayList<>();
        List<PublicUserDto> adminDtos = new ArrayList<>();
        Board board = new Board("1", "Test Board", columns, members, admins, new Date(), new Date());

        when(columnMapper.toDtos(columns)).thenReturn(columnDtos);
        when(publicUserMapper.toPublicUserDtos(members)).thenReturn(memberDtos);
        when(publicUserMapper.toPublicUserDtos(admins)).thenReturn(adminDtos);

        // When
        BoardDto boardDto = boardMapper.toDto(board);

        // Then
        assertThat(boardDto.getId()).isEqualTo("1");
        assertThat(boardDto.getTitle()).isEqualTo("Test Board");
        assertThat(boardDto.getColumns()).isEqualTo(columnDtos);
        assertThat(boardDto.getMembers()).isEqualTo(memberDtos);
        assertThat(boardDto.getAdmins()).isEqualTo(adminDtos);
    }
}
