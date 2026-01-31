package com.example.tripmate.controller;

import com.example.tripmate.model.Activity;
import com.example.tripmate.model.Destination;
import com.example.tripmate.model.Trip;
import com.example.tripmate.service.ActivityService;
import com.example.tripmate.service.DestinationService;
import com.example.tripmate.service.TripService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TripController {

    private final TripService tripService;
    private final DestinationService destinationService;
    private final ActivityService activityService;

    public TripController(TripService tripService, DestinationService destinationService, ActivityService activityService) {
        this.tripService = tripService;
        this.destinationService = destinationService;
        this.activityService = activityService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        String email = authentication.getName();
        List<Trip> trips = tripService.getTripsForUser(email);
        model.addAttribute("trips", trips);
        model.addAttribute("trip", new Trip());
        return "dashboard";
    }

    @PostMapping("/trips")
    public String createTrip(Authentication authentication, @ModelAttribute Trip trip, RedirectAttributes redirectAttributes) {
        String email = authentication.getName();
        try {
            tripService.createTrip(email, trip);
            redirectAttributes.addFlashAttribute("success", "Trip created successfully");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/trips/{id}/delete")
    public String deleteTrip(Authentication authentication, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        String email = authentication.getName();
        try {
            tripService.deleteTrip(id, email);
            redirectAttributes.addFlashAttribute("success", "Trip deleted successfully");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/trips/{id}")
    public String getTripDetails(Authentication authentication, @PathVariable Long id, Model model) {
        String email = authentication.getName();
        try {
            Trip trip = tripService.getTripById(id, email);
            model.addAttribute("trip", trip);
            model.addAttribute("destinations", destinationService.getDestinationsForTrip(id));
            model.addAttribute("newDestination", new Destination());
            model.addAttribute("activities", activityService.getActivitiesForTrip(id));
            model.addAttribute("newActivity", new Activity());
            return "trip-details";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/dashboard";
        }
    }
}
