package at.technikum.springrestbackend.dto;

import lombok.Builder;

@Builder
public record LoginResponseDto(String token) {
}
