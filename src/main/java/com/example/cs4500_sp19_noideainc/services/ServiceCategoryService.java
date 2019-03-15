package com.example.cs4500_sp19_noideainc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cs4500_sp19_noideainc.models.ServiceCategory;
import com.example.cs4500_sp19_noideainc.repositories.ServiceCategoryRepository;
import com.example.cs4500_sp19_noideainc.repositories.PagedServiceCategoryRepository;

@RestController
@CrossOrigin(origins = "*")
public class ServiceCategoryService {
    @Autowired
    ServiceCategoryRepository serviceCategoryRepository;

    @Autowired
    PagedServiceCategoryRepository pagedServiceCategoryRepository;

    @GetMapping("/api/categories")
    public List<ServiceCategory> findAllServiceCategories() {
        return serviceCategoryRepository.findAllServiceCategories();
    }

    @GetMapping("/api/categories/{serviceCategoryId}")
    public ServiceCategory findServiceCategoryById(
            @PathVariable("serviceCategoryId") Integer id) {
        return serviceCategoryRepository.findServiceCategoryById(id);
    }

    @PostMapping("/api/categories")
    public ServiceCategory createServiceCategory(@RequestBody ServiceCategory serviceCategory) {
        return serviceCategoryRepository.save(serviceCategory);
    }

    @PutMapping("/api/categories/{serviceCategoryId}")
    public ServiceCategory updateServiceCategory(
            @PathVariable("serviceCategoryId") Integer id,
            @RequestBody ServiceCategory serviceUpdates) {
        ServiceCategory serviceCategory = serviceCategoryRepository.findServiceCategoryById(id);
        serviceCategory.setTitle(serviceUpdates.getTitle());
        serviceCategory.setServices(serviceUpdates.getServices());
        return serviceCategoryRepository.save(serviceCategory);
    }

    @DeleteMapping("/api/categories/{serviceCategoryId}")
    public void deleteServiceCategory(
            @PathVariable("serviceCategoryId") Integer id) {
        serviceCategoryRepository.deleteById(id);
    }

    @GetMapping("/api/categories/paged")
    public Page<ServiceCategory> findPagedServiceCategories(
            @RequestParam(name="page", required=false) Integer page,
            @RequestParam(name="count", required=false) Integer count) {
        if(page == null) {
            page = 0;
        }
        if(count == null) {
            count = 10;
        }
        Pageable p = PageRequest.of(page, count);
        return pagedServiceCategoryRepository.findAll(p);
    }
}