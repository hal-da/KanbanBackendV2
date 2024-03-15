package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.model.Column;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColumnRepository extends JpaRepository<Column,String> {
    //get columns by board id
    List<Column> findByBoardId(String boardId);
}
