package com.tms.repository;

import com.tms.models.ColoringPages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ColoringPagesRepository extends JpaRepository<ColoringPages,Integer> {
    List<ColoringPages> findByCategories(String category);
}
