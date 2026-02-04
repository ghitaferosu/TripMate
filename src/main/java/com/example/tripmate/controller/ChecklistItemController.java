package com.example.tripmate.controller;

import com.example.tripmate.model.ChecklistItem;
import com.example.tripmate.repository.ChecklistItemRepository;
import com.example.tripmate.service.ChecklistItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChecklistItemController {
    private final ChecklistItemService checklistItemService;
    private final ChecklistItemRepository checklistItemRepository;

    public ChecklistItemController(ChecklistItemService checklistItemService, ChecklistItemRepository checklistItemRepository) {
        this.checklistItemService = checklistItemService;
        this.checklistItemRepository = checklistItemRepository;
    }

    @PostMapping("/trips/{tripId}/checklist")
    public String addChecklistItem(@PathVariable Long tripId, @ModelAttribute ChecklistItem item) {
        checklistItemService.addChecklistItem(tripId, item);
        return "redirect:/trips/" + tripId;
    }

    @PostMapping("/checklist/{id}/delete")
    public String deleteChecklistItem(@PathVariable Long id) {
        ChecklistItem item = checklistItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Checklist item not found with id: " + id));
        Long tripId = item.getTrip().getId();
        checklistItemService.deleteChecklistItem(id);
        return "redirect:/trips/" + tripId;
    }

    @PostMapping("/checklist/{id}/toggle")
    public String toggleChecklistItem(@PathVariable Long id) {
        ChecklistItem item = checklistItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Checklist item not found with id: " + id));
        Long tripId = item.getTrip().getId();
        checklistItemService.toggleCompleted(id);
        return "redirect:/trips/" + tripId;
    }
}
