package com.example.cs4500_sp19_noideainc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.cs4500_sp19_noideainc.models.ServiceCategory;

public interface PagedServiceCategoryRepository extends JpaRepository<ServiceCategory, Integer> {

}