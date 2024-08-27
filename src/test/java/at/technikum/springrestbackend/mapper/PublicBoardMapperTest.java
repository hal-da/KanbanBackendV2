package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.PublicBoardDto;
import at.technikum.springrestbackend.dto.PublicUserDto;
import at.technikum.springrestbackend.model.Board;
import at.technikum.springrestbackend.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class PublicBoardMapperTest {

    @InjectMocks
    private PublicBoardMapper publicBoardMapper;

    @Mock
    private PublicUserMapper publicUserMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToPublicBoardDto() {
        // Given
        Board board = new Board("1", "Test Board", null, null, null, null, null);
        UserEntity member = new UserEntity("member", "password", "member@example.com");
        UserEntity admin = new UserEntity("admin", "password", "admin@example.com");
        board.addMember(member);
        board.addAdmin(admin);

        PublicUserDto memberDto = new PublicUserDto("member", "member@example.com");
        PublicUserDto adminDto = new PublicUserDto("admin", "admin@example.com");

        when(publicUserMapper.toPublicUserDtos(board.getMembers())).thenReturn(Arrays.asList(memberDto));
        when(publicUserMapper.toPublicUserDtos(board.getAdmins())).thenReturn(Arrays.asList(adminDto));

        // When
        PublicBoardDto publicBoardDto = publicBoardMapper.toPublicBoardDto(board);

        // Then
        assertThat(publicBoardDto).isNotNull();
        assertThat(publicBoardDto.getId()).isEqualTo("1");
        assertThat(publicBoardDto.getTitle()).isEqualTo("Test Board");
        assertThat(publicBoardDto.getMembers()).containsExactly(memberDto);
        assertThat(publicBoardDto.getAdmins()).containsExactly(adminDto);
    }

    @Test
    void testToPublicBoardDtos() {
        // Given
        Board board1 = new Board("1", "Board 1", null, null, null, null, null);
        Board board2 = new Board("2", "Board 2", null, null, null, null, null);
        List<Board> boards = Arrays.asList(board1, board2);

        PublicBoardDto dto1 = new PublicBoardDto("1", "Board 1", null, null);
        PublicBoardDto dto2 = new PublicBoardDto("2", "Board 2", null, null);

        when(publicBoardMapper.toPublicBoardDto(board1)).thenReturn(dto1);
        when(publicBoardMapper.toPublicBoardDto(board2)).thenReturn(dto2);

        // When
        List<PublicBoardDto> publicBoardDtos = publicBoardMapper.toPublicBoardDtos(boards);

        // Then
        assertThat(publicBoardDtos).hasSize(2);
        assertThat(publicBoardDtos.get(0)).isEqualTo(dto1);
        assertThat(publicBoardDtos.get(1)).isEqualTo(dto2);
    }
}
