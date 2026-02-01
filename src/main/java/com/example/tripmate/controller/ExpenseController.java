package com.example.tripmate.controller;

import com.example.tripmate.model.Expense;
import com.example.tripmate.service.ExpenseService;
import com.example.tripmate.repository.ExpenseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ExpenseController {
    
    private final ExpenseService expenseService;
    private final ExpenseRepository expenseRepository;
    
    public ExpenseController(ExpenseService expenseService, ExpenseRepository expenseRepository) {
        this.expenseService = expenseService;
        this.expenseRepository = expenseRepository;
    }
    
    @PostMapping("/trips/{tripId}/expenses")
    public String addExpense(@PathVariable Long tripId, @ModelAttribute Expense expense) {
        expenseService.addExpense(tripId, expense);
        return "redirect:/trips/" + tripId;
    }
    
    @PostMapping("/expenses/{id}/delete")
    public String deleteExpense(@PathVariable Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Expense not found with id: " + id));
        Long tripId = expense.getTrip().getId();
        expenseService.deleteExpense(id);
        return "redirect:/trips/" + tripId;
    }
}
