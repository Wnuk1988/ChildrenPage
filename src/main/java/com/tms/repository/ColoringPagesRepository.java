package com.tms.repository;

import com.tms.models.Categories;
import com.tms.models.ColoringPages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ColoringPagesRepository extends JpaRepository<ColoringPages,Integer> {
    List<ColoringPages> findByCategories(Categories category);

    @Query("INSERT INTO audio_fairy_tales in to path_to_file values")
    void update();
}
