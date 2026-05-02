package com.psychology.demo.repo;

import com.psychology.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail (String email);


    boolean existsByEmail(String email);

    Optional<User> findByPassword (String password);
    Optional<User> findByFullName (String fullName);



}
