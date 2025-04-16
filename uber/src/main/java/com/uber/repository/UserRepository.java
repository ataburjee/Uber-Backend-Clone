package com.uber.repository;

import com.uber.model.Role;
import com.uber.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findById(String userId);

    boolean existsByEmail(String username);

    boolean existsById(String id);

    void deleteById(String id);

    long countByRole(Role role);


}
