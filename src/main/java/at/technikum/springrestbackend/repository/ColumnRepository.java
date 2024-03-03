package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.model.Column;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnRepository extends JpaRepository<Column,String> {
}
