package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.UserDto;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserMapper {
    private final UserRepository userRepository;

    // maps a UserEntity to a UserDto
    public UserDto toUserDto(UserEntity user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getRole().toString(),
                user.getUsername(),
                user.getImageUrl(),
                user.getCreatedAt(),
                user.getLastChangeAt());
    }

    // maps a UserDto to a UserEntity
    public UserEntity toUserEntity(UserDto userDto) {

        return new UserEntity(
                userDto.getId(),
                userDto.getEmail(),
                userDto.getUserName(),
                userDto.getRole(),
                userDto.getImageUrl());
    }

    // maps a List of UserEntity to a List of UserDto
    public List<UserDto> toUserDtos(List<UserEntity> users) {
        return users.stream().map(this::toUserDto).toList();
    }

    // maps a List of UserDto to a List of UserEntity
    public List<UserEntity> toUserEntities(List<UserDto> userDtos) {
        return userDtos.stream().map(this::toUserEntity).toList();
    }

}
