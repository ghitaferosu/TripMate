package com.example.tripmate.controller;

import com.example.tripmate.model.Destination;
import com.example.tripmate.repository.DestinationRepository;
import com.example.tripmate.service.DestinationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DestinationController {

    private final DestinationService destinationService;
    private final DestinationRepository destinationRepository;

    public DestinationController(DestinationService destinationService, DestinationRepository destinationRepository) {
        this.destinationService = destinationService;
        this.destinationRepository = destinationRepository;
    }

    @PostMapping("/trips/{tripId}/destinations")
    public String addDestination(@PathVariable Long tripId, @ModelAttribute Destination destination, RedirectAttributes redirectAttributes) {
        try {
            destinationService.addDestination(tripId, destination);
            redirectAttributes.addFlashAttribute("success", "Destination added successfully");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/trips/" + tripId;
    }

    @PostMapping("/destinations/{id}/delete")
    public String deleteDestination(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Destination not found with id: " + id));
            Long tripId = destination.getTrip().getId();
            destinationService.deleteDestination(id);
            redirectAttributes.addFlashAttribute("success", "Destination deleted successfully");
            return "redirect:/trips/" + tripId;
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/dashboard";
        }
    }
}
