package com.course.graphql.datasource.problemz.repository;

import com.course.graphql.datasource.problemz.entity.Userz;
import com.course.graphql.generated.types.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserzRepository extends CrudRepository<Userz, String> {

    Optional<Userz> findByUsernameIgnoreCase(String username);

    @Query(nativeQuery = true, value = "select u.* from userz u " +
            "inner join userz_token ut on u.id = ut.user_id " +
            "where ut.auth_token = ? " +
            "and ut.expiry_timestamp > current_timestamp")
    Optional<Userz> findUserByToken(String token);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
        value = "update userz set active = :isActive where upper(username) = upper(:username)"
    )
    void activateUser(@Param("username") String username, @Param("isActive") boolean isActive);

}
