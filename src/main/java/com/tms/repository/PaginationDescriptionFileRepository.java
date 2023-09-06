package com.tms.repository;

import com.tms.models.DescriptionFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface PaginationDescriptionFileRepository extends PagingAndSortingRepository<DescriptionFile,Integer> {
    @Override
    Page<DescriptionFile> findAll(@NonNull Pageable pageable);
}
