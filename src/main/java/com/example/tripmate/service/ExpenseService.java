package com.example.tripmate.service;

import com.example.tripmate.model.Expense;
import com.example.tripmate.model.Trip;
import com.example.tripmate.repository.ExpenseRepository;
import com.example.tripmate.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    
    private final ExpenseRepository expenseRepository;
    private final TripRepository tripRepository;
    
    public ExpenseService(ExpenseRepository expenseRepository, TripRepository tripRepository) {
        this.expenseRepository = expenseRepository;
        this.tripRepository = tripRepository;
    }
    
    public List<Expense> getExpensesForTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with id: " + tripId));
        return trip.getExpenses();
    }
    
    public Expense addExpense(Long tripId, Expense expense) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with id: " + tripId));
        expense.setTrip(trip);
        return expenseRepository.save(expense);
    }
    
    public void deleteExpense(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new IllegalArgumentException("Expense not found with id: " + expenseId));
        expenseRepository.delete(expense);
    }
}
