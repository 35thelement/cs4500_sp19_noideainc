package com.example.cs4500_sp19_noideainc.services;

import java.util.List;

import com.example.cs4500_sp19_noideainc.models.ServiceCategory;
import com.example.cs4500_sp19_noideainc.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cs4500_sp19_noideainc.models.Service;
import com.example.cs4500_sp19_noideainc.repositories.ServiceRepository;
import com.example.cs4500_sp19_noideainc.repositories.UserRepository;

@RestController
@CrossOrigin(origins = "*")
public class ServiceService {
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/services")
    public List<Service> findAllService() {
        return serviceRepository.findAllServices();
    }

    @GetMapping("/api/services/{serviceId}")
    public Service findServiceById(
            @PathVariable("serviceId") Integer id) {
        return serviceRepository.findServiceById(id);
    }

    @GetMapping("/api/services/{serviceId}/users")
    public List<User> findAllUsersForService(@PathVariable("serviceId") Integer serviceId) {
        return serviceRepository.findServiceById(serviceId).getProviders();
    }

    @GetMapping("/api/services/{serviceId}/categories")
    public List<ServiceCategory> findAllCategoriesForService(@PathVariable("serviceId") Integer serviceId) {
        return serviceRepository.findServiceById(serviceId).getServiceCategories();
    }

    @PostMapping("/api/services")
    public Service createService(@RequestBody Service service) {
        return serviceRepository.save(service);
    }

    @PostMapping("/api/services/{serviceId}/users/{userId}")
    public Service createServiceForUser(@PathVariable("userId") Integer userId,
                                        @PathVariable("serviceId") Integer serviceId) {
        Service service = serviceRepository.findServiceById(serviceId);
        User user = userRepository.findUserById(userId);
        List<User> providers = service.getProviders();
        providers.add(user);
        service.setProviders(providers);
        return serviceRepository.save(service);
    }

    @PutMapping("/api/services/{serviceId}")
    public Service updateService(
            @PathVariable("serviceId") Integer id,
            @RequestBody Service serviceUpdates) {
        Service service = serviceRepository.findServiceById(id);
        service.setTitle(serviceUpdates.getTitle());
        service.setProviders(serviceUpdates.getProviders());
        service.setServiceCategories(serviceUpdates.getServiceCategories());
        return serviceRepository.save(service);
    }

    @DeleteMapping("/api/services/{serviceId}")
    public void deleteService(
            @PathVariable("serviceId") Integer id) {
        serviceRepository.deleteById(id);
    }

    @DeleteMapping("/api/services/{serviceId}/users/{userId}")
    public Service deleteUserFromService(@PathVariable("userId") Integer userId,
                                         @PathVariable("serviceId") Integer serviceId) {
        Service service = serviceRepository.findServiceById(serviceId);
        User user = userRepository.findUserById(userId);
        List<User> providers = service.getProviders();
        providers.remove(user);
        service.setProviders(providers);
        return serviceRepository.save(service);
    }
    
    // update service score
    @PutMapping("/api/services/score/{serviceId}")
    public Service updateServiceScore(
            @PathVariable("serviceId") Integer id,
            @RequestBody Service serviceUpdates) {
        Service service = serviceRepository.findServiceById(id);
        service.setScore(serviceUpdates.getScore());
        return serviceRepository.save(service);
    }
}