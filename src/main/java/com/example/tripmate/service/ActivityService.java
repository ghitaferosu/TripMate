package com.example.tripmate.service;

import com.example.tripmate.model.Activity;
import com.example.tripmate.model.Trip;
import com.example.tripmate.repository.ActivityRepository;
import com.example.tripmate.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {
    
    private final ActivityRepository activityRepository;
    private final TripRepository tripRepository;
    
    public ActivityService(ActivityRepository activityRepository, TripRepository tripRepository) {
        this.activityRepository = activityRepository;
        this.tripRepository = tripRepository;
    }
    
    public List<Activity> getActivitiesForTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with id: " + tripId));
        return trip.getActivities();
    }
    
    public Activity addActivity(Long tripId, Activity activity) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with id: " + tripId));
        activity.setTrip(trip);
        return activityRepository.save(activity);
    }
    
    public void deleteActivity(Long activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new IllegalArgumentException("Activity not found with id: " + activityId));
        activityRepository.delete(activity);
    }
}
