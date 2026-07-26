package com.example.nto.repository;

import com.example.nto.entity.Booking;
import com.example.nto.entity.Employee;
import com.example.nto.entity.Place;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByDateBetween(LocalDate start, LocalDate end);
    @EntityGraph(attributePaths = {"place", "place.account"})
    Optional<Booking> findByDateAndPlace(LocalDate date, Place place);
    @EntityGraph(attributePaths = {"employee"})
    Optional<Booking> findByDateAndEmployee(LocalDate date, Employee employee);
}
