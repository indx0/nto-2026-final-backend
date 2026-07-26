package com.example.nto.repository;

import com.example.nto.entity.Authority;
import com.example.nto.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
