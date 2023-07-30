package elecciones2021.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import elecciones2021.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    
	@Query("select u from User u where id != ?1")
	List<User> findAllUserAndExcludeId(long excludeiduser);
}
