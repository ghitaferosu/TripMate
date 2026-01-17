package com.example.tripmate.repository;

import com.example.tripmate.model.Trip;
import com.example.tripmate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByUser(User user);
}
