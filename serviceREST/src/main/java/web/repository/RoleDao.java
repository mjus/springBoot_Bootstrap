package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.model.Role;

import java.util.List;

public interface RoleDao extends JpaRepository<Role, Long> {
    List<Role> findAll();

    Role getRoleById(long id);
}
