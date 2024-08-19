package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,String>{

    UserEntity findByEmail(String email);
}
