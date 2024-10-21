package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.LoginRequestDto;
import at.technikum.springrestbackend.dto.LoginResponseDto;
import at.technikum.springrestbackend.dto.RegisterDto;
import at.technikum.springrestbackend.dto.UpdateUserDto;
import at.technikum.springrestbackend.exception.*;
import at.technikum.springrestbackend.model.Board;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.repository.BoardRepository;
import at.technikum.springrestbackend.repository.UserRepository;
import at.technikum.springrestbackend.security.JWTIssuer;
import at.technikum.springrestbackend.security.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final JWTIssuer jwtIssuer;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BoardService boardService;
    private final BoardRepository boardRepository;

    public void delete(String id, UserPrincipal userPrincipal)
            throws EntityNotFoundException, UserNotEnoughPrivileges {
        if (!userPrincipal.isAdmin()) {
            throw new UserNotEnoughPrivileges("You are not authorized to access this resource");
        }
        UserEntity userToBeDeleted = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found"));
        List<Board> boards = boardService.findAllByUserId(id);
        for (Board board : boards) {
            userToBeDeleted.removeAdminBoard(board);
            userToBeDeleted.removeMemberBoard(board);
            board.removeAdmin(userToBeDeleted);
            board.removeMember(userToBeDeleted);
            userRepository.save(userToBeDeleted);
            if(board.getAdmins().isEmpty()){
                boardRepository.delete(board);
            } else {
                UserEntity system = userRepository.findByEmail("SYSTEM@SYSTEM");
                boardService.save(board, system);
            }
        }
        userRepository.deleteById(id);
    }

    public UserEntity findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    public UserEntity findById(String id, UserEntity user) {
        if (!user.getId().equals(id) && !user.isAdmin()) {
            throw new UserNotEnoughPrivileges("You are not authorized to access this resource");
        }
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    public UserEntity findByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User with email " + email + " not found");
        }
        return user;
    }

    public List<UserEntity> findAll() {
        // dont send user with id SYSTEM
        return userRepository.findAll().stream().filter(user -> !user.getUsername().equals("SYSTEM")).toList();
    }

    public List<UserEntity> findAll(UserPrincipal userPrincipal) {
        if (!userPrincipal.isAdmin()) {
            throw new UserNotEnoughPrivileges("You are not authorized to access this resource");
        }
        return userRepository.findAll().stream().filter(user -> !user.getUsername().equals("SYSTEM")).toList();
    }

    public LoginResponseDto loginUser(@Valid LoginRequestDto loginDto, UserEntity user) {
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new PasswordOrEmailWrongException("Email or password is not correct");
        }
        String token = jwtIssuer.issueToken(user.getId(), loginDto.getEmail(), user.getRole());
        return LoginResponseDto.builder().token(token).build();
    }

    public UserEntity registerUser(RegisterDto registerDto) {
        if (!registerDto.getPassword1().equals(registerDto.getPassword2())) {
            throw new PasswordsNotSimilarException("Passwords are not similar");
        }
        UserEntity userEntity = userRepository.findByEmail(registerDto.getEmail());
        if (userEntity != null) {
            throw new EmailExistsException("User with email " + registerDto.getEmail() + " already exists");
        }
        String encodedPW = passwordEncoder.encode(registerDto.getPassword1());
        Date now = new Date();
        UserEntity system = userRepository.findByEmail("SYSTEM@SYSTEM");
        UserEntity user = new UserEntity
                .Builder()
                .withEmail(registerDto.getEmail())
                .withPassword(encodedPW)
                .withUsername(registerDto.getUserName())
                .withCreatedAt(now)
                .withCreatedBy(system)
                .withLastChangeBy(system)
                .withLastChangeAt(now)
                .withCca3(registerDto.getCca3())
                .build();

        return userRepository.save(user);
    }

    public UserEntity update(String id, UpdateUserDto updateUserDto, UserEntity requestUser) {
        if (!id.equals(requestUser.getId()) && !requestUser.isAdmin()) {
            throw new UserNotEnoughPrivileges("You are not authorized to access this resource");
        }

        UserEntity userWithSameEmail = userRepository.findByEmail(updateUserDto.getEmail());
        if (userWithSameEmail != null && !userWithSameEmail.getId().equals(id)) {
            throw new EmailExistsException("User with email " + updateUserDto.getEmail() + " already exists");
        }


        UserEntity userToChange = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        userToChange.setLastChangeAt(new Date());
        userToChange.setLastChangeBy(requestUser);
        userToChange.setEmail(updateUserDto.getEmail());
        userToChange.setImageUrl(updateUserDto.getImageUrl());
        userToChange.setUsername(updateUserDto.getUserName());
        userToChange.setCca3(updateUserDto.getCca3());

        return userRepository.save(userToChange);
    }

    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }
}
