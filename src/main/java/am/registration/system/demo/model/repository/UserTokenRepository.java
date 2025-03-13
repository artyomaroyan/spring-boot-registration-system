package am.registration.system.demo.model.repository;

import am.registration.system.demo.model.entity.UserToken;
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
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    Optional<UserToken> findByToken(@Param("token") String token);

    @Modifying
    @Query("update UserToken ut set ut.tokenState = 'FORCIBLY_EXPIRED' where ut.tokenState = 'PENDING' and ut.expireDate < CURRENT_TIMESTAMP")
    int markExpiredTokens();
}