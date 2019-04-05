package com.example.cs4500_sp19_noideainc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.cs4500_sp19_noideainc.models.Service;
import com.example.cs4500_sp19_noideainc.models.ServiceCategory;

public interface ServiceCategoryRepository extends CrudRepository<ServiceCategory, Integer> {
	@Query(value="SELECT serviceCategory FROM ServiceCategory serviceCategory")
	public List<ServiceCategory> findAllServiceCategories();
	@Query(value="SELECT serviceCategory FROM ServiceCategory serviceCategory WHERE serviceCategory.id=:id")
	public ServiceCategory findServiceCategoryById(@Param("id") Integer id);
	@Query(value="SELECT serviceCategory FROM ServiceCategory serviceCategory WHERE serviceCategory.title=:name")
	public ServiceCategory findAllServicseByCategoryName(@Param("name") String name);
}