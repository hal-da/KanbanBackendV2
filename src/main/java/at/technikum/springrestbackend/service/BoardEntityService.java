package at.technikum.springrestbackend.service;


import at.technikum.springrestbackend.model.BoardEntity;
import at.technikum.springrestbackend.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardEntityService {

    private final BoardRepository boardRepository;

    public BoardEntityService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public BoardEntity save(BoardEntity boardEntity){
        return boardRepository.save(boardEntity);
    }

    public BoardEntity findById(String id){
        return boardRepository.findById(id).orElse(null);
    }

    public void deleteById(String id){
        boardRepository.deleteById(id);
    }

    public List<BoardEntity> findAll(){
        return boardRepository.findAll();
    }
}
