package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,String> {
    //get tasks by column id
    List<Task> findByColumnId(String columnId);
}
