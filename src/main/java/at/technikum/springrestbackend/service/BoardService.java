package at.technikum.springrestbackend.service;


import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.model.Board;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ColumnService columnService;

    public Board save(Board board, UserEntity user) throws EntityNotFoundException {
        // new and empty board
        if (board.getId() == null) {
            board = new Board(board.getTitle());
            board.setColumns(columnService.createStandardColumns(board));
            board.addAdmin(user);
            board.addMember(user);
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
