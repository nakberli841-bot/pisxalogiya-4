package com.psychology.demo.repo;

import com.psychology.demo.entity.Psychologist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PsychologistRepository extends JpaRepository<Psychologist, Long> {
    List<Psychologist> findBySpecialtyAndLanguage(String specialty, String language);
}