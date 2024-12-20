package com.igomarcelino.demo_oauth_security.repository;

import com.igomarcelino.demo_oauth_security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserLogin(String username);
}
