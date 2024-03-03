package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.model.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity,String>{
}
