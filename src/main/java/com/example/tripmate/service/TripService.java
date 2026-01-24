package com.example.tripmate.service;

import com.example.tripmate.model.Trip;
import com.example.tripmate.model.User;
import com.example.tripmate.repository.TripRepository;
import com.example.tripmate.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    public TripService(TripRepository tripRepository, UserRepository userRepository) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
    }

    public List<Trip> getTripsForUser(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
        return tripRepository.findByUser(user);
    }

    public Trip createTrip(String email, Trip trip) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
        trip.setUser(user);
        return tripRepository.save(trip);
    }

    public void deleteTrip(Long tripId, String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
        
        Trip trip = tripRepository.findById(tripId)
            .orElseThrow(() -> new IllegalArgumentException("Trip not found with id: " + tripId));
        
        if (!trip.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Trip does not belong to the user");
        }
        
        tripRepository.delete(trip);
    }

    public Trip getTripById(Long tripId, String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
        
        Trip trip = tripRepository.findById(tripId)
            .orElseThrow(() -> new IllegalArgumentException("Trip not found with id: " + tripId));
        
        if (!trip.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Trip does not belong to the user");
        }
        
        return trip;
    }
}
