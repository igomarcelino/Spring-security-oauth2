package com.igomarcelino.demo_oauth_security.controller;

import com.igomarcelino.demo_oauth_security.dto.CreateUserDto;
import com.igomarcelino.demo_oauth_security.entities.Roles;
import com.igomarcelino.demo_oauth_security.entities.User;
import com.igomarcelino.demo_oauth_security.repository.RolesRepository;
import com.igomarcelino.demo_oauth_security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/newuser")
    @Transactional
    public ResponseEntity<Void> newUser(@RequestBody CreateUserDto createUserDto){

        var basicRole = rolesRepository.findByRoleName(Roles.Values.BASIC.name());

        var userFromDB = userRepository.findByUserLogin(createUserDto.username());

        if (userFromDB.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var userToSave = new User();
        userToSave.setUserLogin(createUserDto.username());
        userToSave.setUserPassword(passwordEncoder.encode(createUserDto.password()));
        userToSave.setRolesSet(Set.of(basicRole));
        userRepository.save(userToSave);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')") // verificar o nome do claim scope no token controller
    public ResponseEntity<List<User>> listUsers(){

        var users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}
