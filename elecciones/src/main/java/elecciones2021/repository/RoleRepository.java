package elecciones2021.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import elecciones2021.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
