package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Set;

public interface RoleDao extends JpaRepository<Role, Long> {
    List<Role> findAll();

    Role getRoleById(long id);

//    @Query("SELECT Role FROM Role r where r.role = :name")
//    Set<Role> getRoleByName(@Param("name") String name);
}
