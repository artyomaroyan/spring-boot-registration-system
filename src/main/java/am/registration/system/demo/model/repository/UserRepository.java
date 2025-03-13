package am.registration.system.demo.model.repository;

import am.registration.system.demo.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Author: Artyom Aroyan
 * Date: 17.02.25
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUsersByEmail(String email);

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByFullName(String fullName);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Modifying
    @Query("update User u set u.userState = 'ACTIVE' where u.userState = 'PENDING' and u.id = :id")
    void updateUserState(@Param("id") Integer id);
}