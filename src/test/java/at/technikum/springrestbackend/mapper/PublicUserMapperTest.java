package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.PublicUserDto;
import at.technikum.springrestbackend.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PublicUserMapperTest {

    private PublicUserMapper publicUserMapper;

    @BeforeEach
    void setUp() {
        publicUserMapper = new PublicUserMapper(null);  // Repository is not used in this mapper
    }

    @Test
    void testToPublicUserDto() {
        // Given
        UserEntity user = new UserEntity("username", "password", "user@example.com");
        user.setId("user-id");

        // When
        PublicUserDto publicUserDto = publicUserMapper.toPublicUserDto(user);

        // Then
        assertThat(publicUserDto).isNotNull();
        assertThat(publicUserDto.getId()).isEqualTo("user-id");
        assertThat(publicUserDto.getUsername()).isEqualTo("username");
    }

    @Test
    void testToPublicUserDtos() {
        // Given
        UserEntity user1 = new UserEntity("user1", "password1", "user1@example.com");
        user1.setId("id1");
        UserEntity user2 = new UserEntity("user2", "password2", "user2@example.com");
        user2.setId("id2");

        List<UserEntity> users = Arrays.asList(user1, user2);

        // When
        List<PublicUserDto> publicUserDtos = publicUserMapper.toPublicUserDtos(users);

        // Then
        assertThat(publicUserDtos).hasSize(2);
        assertThat(publicUserDtos.get(0).getId()).isEqualTo("id1");
        assertThat(publicUserDtos.get(0).getUsername()).isEqualTo("user1");
        assertThat(publicUserDtos.get(1).getId()).isEqualTo("id2");
        assertThat(publicUserDtos.get(1).getUsername()).isEqualTo("user2");
    }
}
