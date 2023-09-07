package com.tms.repository;

import com.tms.models.Categories;
import com.tms.models.DescriptionFile;
import com.tms.models.Genres;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DescriptionFileRepository extends JpaRepository<DescriptionFile, Integer> {

    List<DescriptionFile> findByCategories(Categories categories);

    List<DescriptionFile> findByGenres(Genres genres);

    @Override
    Page<DescriptionFile> findAll(@NonNull Pageable pageable);

    void deleteById(@NonNull Integer id);
}
