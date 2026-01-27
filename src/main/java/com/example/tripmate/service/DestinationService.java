package com.example.tripmate.service;

import com.example.tripmate.model.Destination;
import com.example.tripmate.model.Trip;
import com.example.tripmate.repository.DestinationRepository;
import com.example.tripmate.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;
    private final TripRepository tripRepository;

    public DestinationService(DestinationRepository destinationRepository, TripRepository tripRepository) {
        this.destinationRepository = destinationRepository;
        this.tripRepository = tripRepository;
    }

    public List<Destination> getDestinationsForTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
            .orElseThrow(() -> new IllegalArgumentException("Trip not found with id: " + tripId));
        return trip.getDestinations();
    }

    public Destination addDestination(Long tripId, Destination destination) {
        Trip trip = tripRepository.findById(tripId)
            .orElseThrow(() -> new IllegalArgumentException("Trip not found with id: " + tripId));
        destination.setTrip(trip);
        return destinationRepository.save(destination);
    }

    public void deleteDestination(Long destinationId) {
        Destination destination = destinationRepository.findById(destinationId)
            .orElseThrow(() -> new IllegalArgumentException("Destination not found with id: " + destinationId));
        destinationRepository.delete(destination);
    }
}
