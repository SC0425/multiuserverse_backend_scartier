package edu.uscb.csci470sp25.multiuserverse_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.uscb.csci470sp25.multiuserverse_backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
