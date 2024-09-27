package com.ispan.eeit188_final.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ispan.eeit188_final.model.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);

    Boolean existsByEmail(String email);
    
    @Query("select distinct u from House h left join h.user u ")
    List<User> findAllHost();
}
