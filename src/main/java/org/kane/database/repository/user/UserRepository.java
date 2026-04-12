package org.kane.database.repository.user;

import org.kane.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.Principal;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository{


    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);



}
