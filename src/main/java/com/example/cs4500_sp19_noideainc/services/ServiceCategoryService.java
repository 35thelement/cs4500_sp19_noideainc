package com.example.cs4500_sp19_noideainc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cs4500_sp19_noideainc.models.ServiceCategory;
import com.example.cs4500_sp19_noideainc.repositories.ServiceCategoryRepository;

@RestController
public class ServiceCategoryService {
	@Autowired
	ServiceCategoryRepository serviceRepository;
	@GetMapping("/api/categories")
	public List<ServiceCategory> findAllServiceCategories() {
		return serviceRepository.findAllServiceCategories();
	}
	@GetMapping("/api/categories/{serviceCategoryId}")
	public ServiceCategory findServiceCategoryById(
			@PathVariable("serviceCategoryId") Integer id) {
		return serviceRepository.findServiceCategoryById(id);
	}
	@PostMapping("/api/categories")
	public ServiceCategory createServiceCategory(@RequestBody ServiceCategory serviceCategory) {
		return serviceRepository.save(serviceCategory);
	}
	@PutMapping("/api/categories/{serviceCategoryId}")
	public ServiceCategory updateServiceCategory(
			@PathVariable("serviceCategoryId") Integer id,
			@RequestBody ServiceCategory serviceUpdates) {
		ServiceCategory serviceCategory = serviceRepository.findServiceCategoryById(id);
		serviceCategory.setTitle(serviceUpdates.getTitle());
		return serviceRepository.save(serviceCategory);
	}
	@DeleteMapping("/api/categories/{serviceCategoryId}")
	public void deleteServiceCategory(
			@PathVariable("serviceCategoryId") Integer id) {
		serviceRepository.deleteById(id);
	}
}