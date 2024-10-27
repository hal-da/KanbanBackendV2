package at.technikum.springrestbackend.service;


import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.exception.UserNotEnoughPrivileges;
import at.technikum.springrestbackend.model.Board;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.repository.BoardRepository;
import at.technikum.springrestbackend.repository.UserRepository;
import at.technikum.springrestbackend.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public Board save(Board board, UserEntity user) throws EntityNotFoundException {
        if (board.getId() == null) {
            board = new Board(board.getTitle(), user);
            board.addAdmin(user);
            board.addMember(user);
        }
        boardRepository.save(board);
        return boardRepository.findById(board.getId()).orElseThrow(EntityNotFoundException::new);
    }

    public Board findById(String id){
        return boardRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void delete(String id, UserPrincipal userPrincipal)
            throws EntityNotFoundException, UserNotEnoughPrivileges {
        Board board = boardRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        board.getAdmins().stream().filter(u -> u.getId().equals(userPrincipal.getUserId())).findFirst()
                .orElseThrow(UserNotEnoughPrivileges::new);

        // Entferne das Board aus den 'memberBoards' aller zugehörigen Benutzer
        List<UserEntity> members = board.getMembers();
        for (UserEntity member : members) {
            member.getMemberBoards().remove(board);
            userRepository.save(member);
        }

        // Entferne das Board aus den 'adminBoards' aller zugehörigen Benutzer
        List<UserEntity> admins = board.getAdmins();
        for (UserEntity admin : admins) {
            admin.getAdminBoards().remove(board);
            userRepository.save(admin);
        }

        try {
            boardRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting the board: " + e.getMessage(), e);
        }
    }

    public List<Board> findAll(){
        return boardRepository.findAll();
    }

    public Board update(Board board, UserEntity user) throws EntityNotFoundException {
        if (board.getId() == null) {
            throw new EntityNotFoundException();
        }
        if(!board.getAdmins().contains(user)){
            throw new UserNotEnoughPrivileges();
        }
        boardRepository.save(board);
        return boardRepository.findById(board.getId()).orElseThrow(EntityNotFoundException::new);
    }

    public List<Board> findAllByUserId(String id) {
        List<Board> allBoards = boardRepository.findAll();
        // filter boards for member and admins within the board
        return allBoards.stream()
                .filter(board -> board.getMembers().stream().anyMatch(user -> user.getId().equals(id))
                        || board.getAdmins().stream().anyMatch(user -> user.getId().equals(id)))
                .toList();


    }
}
