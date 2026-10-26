package com.hgo_soft.device_for_all.users.repositories;

import com.hgo_soft.device_for_all.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT DISTINCT u\n" +
            "        FROM User u\n" +
            "        LEFT JOIN FETCH u.roles r\n" +
            "        LEFT JOIN FETCH r.permissions\n" +
            "        WHERE u.username = :username")
    Optional<User> findRolesAndPermissionsByUsername(@Param("username") String username);
}
