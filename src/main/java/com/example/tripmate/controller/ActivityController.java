package com.example.tripmate.controller;

import com.example.tripmate.model.Activity;
import com.example.tripmate.service.ActivityService;
import com.example.tripmate.repository.ActivityRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ActivityController {
    
    private final ActivityService activityService;
    private final ActivityRepository activityRepository;
    
    public ActivityController(ActivityService activityService, ActivityRepository activityRepository) {
        this.activityService = activityService;
        this.activityRepository = activityRepository;
    }
    
    @PostMapping("/trips/{tripId}/activities")
    public String addActivity(@PathVariable Long tripId, @ModelAttribute Activity activity) {
        activityService.addActivity(tripId, activity);
        return "redirect:/trips/" + tripId;
    }
    
    @PostMapping("/activities/{id}/delete")
    public String deleteActivity(@PathVariable Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Activity not found with id: " + id));
        Long tripId = activity.getTrip().getId();
        activityService.deleteActivity(id);
        return "redirect:/trips/" + tripId;
    }
}
