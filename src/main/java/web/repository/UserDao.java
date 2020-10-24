package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Long>, JpaRepository<User, Long> {
    User findByEmail(String email);
}
