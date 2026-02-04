package com.example.tripmate.service;

import com.example.tripmate.model.ChecklistItem;
import com.example.tripmate.model.Trip;
import com.example.tripmate.repository.ChecklistItemRepository;
import com.example.tripmate.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChecklistItemService {
    private final ChecklistItemRepository checklistItemRepository;
    private final TripRepository tripRepository;

    public ChecklistItemService(ChecklistItemRepository checklistItemRepository, TripRepository tripRepository) {
        this.checklistItemRepository = checklistItemRepository;
        this.tripRepository = tripRepository;
    }

    public List<ChecklistItem> getChecklistItemsForTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with id: " + tripId));
        return trip.getChecklistItems();
    }

    public ChecklistItem addChecklistItem(Long tripId, ChecklistItem item) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with id: " + tripId));
        item.setTrip(trip);
        // For primitive boolean, default is false, but if using Boolean, check null. Here, always set to false if not completed.
        if (!item.isCompleted()) {
            item.setCompleted(false);
        }
        return checklistItemRepository.save(item);
    }

    public void deleteChecklistItem(Long itemId) {
        ChecklistItem item = checklistItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Checklist item not found with id: " + itemId));
        checklistItemRepository.delete(item);
    }

    public void toggleCompleted(Long itemId) {
        ChecklistItem item = checklistItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Checklist item not found with id: " + itemId));
        item.setCompleted(!item.isCompleted());
        checklistItemRepository.save(item);
    }
}
