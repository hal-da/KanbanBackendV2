package at.technikum.springrestbackend.service;


import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.model.Board;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ColumnService columnService;

    public BoardService(BoardRepository boardRepository, ColumnService columnService) {
        this.boardRepository = boardRepository;
        this.columnService = columnService;
    }

    public Board save(Board board) throws EntityNotFoundException {
        // new and empty board
        if (board.getId() == null) {
            board = new Board(board.getTitle());
            board.setColumns(columnService.createStandardColumns(board));
            // TODO: remove this test user
            UserEntity testUser = new UserEntity(
                    "testName",
                    "testPassword",
                    "testEmail");
            board.addAdmin(testUser);
            board.addMember(testUser);
        }
        boardRepository.save(board);
        return boardRepository.findById(board.getId()).orElseThrow(EntityNotFoundException::new);
    }

    public Board findById(String id){
        return boardRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void delete(String id){
        boardRepository.deleteById(id);
    }

    public List<Board> findAll(){
        return boardRepository.findAll();
    }

    public Board update(Board board){
        return boardRepository.save(board);
    }
}
