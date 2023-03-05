package com.maybank.assessment.repo;

import com.maybank.assessment.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Page<Client> findByFirstNameContaining(String firstName, Pageable pageable);
    Optional<Client> findByEmail(String email);
}