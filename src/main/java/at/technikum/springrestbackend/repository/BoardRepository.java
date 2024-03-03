package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,String>{
}
