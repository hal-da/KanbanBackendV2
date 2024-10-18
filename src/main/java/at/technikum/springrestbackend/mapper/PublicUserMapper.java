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
        System.out.println("user in publicusermapper: " + user.getCca3());
        PublicUserDto p =  new PublicUserDto(
                user.getId(),
                user.getUsername(),
                user.getRole().toString(),
                user.getImageUrl(),
                user.getCreatedAt(),
                user.getLastChangeAt(),
                user.getCca3());

        System.out.println("publicuserdto in publicusermapper: " + p.getCca3());
        return p;
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
