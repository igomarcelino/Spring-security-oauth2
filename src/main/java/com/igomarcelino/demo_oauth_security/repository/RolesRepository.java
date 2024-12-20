package com.igomarcelino.demo_oauth_security.repository;

import com.igomarcelino.demo_oauth_security.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
}
