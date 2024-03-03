package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.model.ColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnRepository extends JpaRepository<ColumnEntity,String> {
}
