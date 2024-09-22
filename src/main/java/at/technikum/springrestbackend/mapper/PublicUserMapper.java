package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.PublicUserDto;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class PublicUserMapper {
    private final UserRepository userRepository;

    // maps a UserEntity to a PublicUserDto
    public PublicUserDto toPublicUserDto(UserEntity user) {
        return new PublicUserDto(
                user.getId(),
                user.getUsername());
    }

    // maps a list of UserEntity to a list of PublicUserDto
    public List<PublicUserDto> toPublicUserDtos(List<UserEntity> users) {
        return users.stream().map(this::toPublicUserDto).toList();
    }

    // maps a PublicUserDto to a UserEntity
    public UserEntity toUserEntity(PublicUserDto publicUserDto) {
        return userRepository.findById(publicUserDto.getId()).orElseThrow();
    }
}
