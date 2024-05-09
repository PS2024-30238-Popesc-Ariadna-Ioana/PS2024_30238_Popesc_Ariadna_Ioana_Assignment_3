package com.example.proiecta3.repositories;

import com.example.proiecta3.entity.EntitateEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<EntitateEmail, String> {

}
