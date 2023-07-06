package com.example.springboot.repositories;

import com.example.springboot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByIdUser(long id);
    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
    int countByAdmin(boolean admin);
}
