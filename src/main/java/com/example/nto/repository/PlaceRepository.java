package com.example.nto.repository;

import com.example.nto.entity.Place;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    @EntityGraph(attributePaths = {"account"})
    Place findByAccount_Username(String username);
}
