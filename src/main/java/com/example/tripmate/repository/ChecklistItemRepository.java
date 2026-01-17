package com.example.tripmate.repository;

import com.example.tripmate.model.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Long> {
}
