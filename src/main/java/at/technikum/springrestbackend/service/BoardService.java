package at.technikum.springrestbackend.service;


import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.model.Board;
import at.technikum.springrestbackend.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Board save(Board board) throws EntityNotFoundException {
        if (board.getId() == null) board = new Board(board.getTitle());
        boardRepository.save(board);
        return boardRepository.findById(board.getId()).orElseThrow(EntityNotFoundException::new);

    }

    public Board findById(String id){
        return boardRepository.findById(id).orElse(null);
    }

    public void deleteById(String id){
        boardRepository.deleteById(id);
    }

    public List<Board> findAll(){
        return boardRepository.findAll();
    }
}
