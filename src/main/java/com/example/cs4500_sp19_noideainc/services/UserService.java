package com.example.cs4500_sp19_noideainc.services;

import java.util.List;

import com.example.cs4500_sp19_noideainc.models.Service;
import com.example.cs4500_sp19_noideainc.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.cs4500_sp19_noideainc.models.User;
import com.example.cs4500_sp19_noideainc.repositories.UserRepository;

@RestController
@CrossOrigin(origins = "*")
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @GetMapping("/api/users")
    public List<User> findAllUser() {
        return userRepository.findAllUsers();
    }

    @GetMapping("/api/users/{userId}")
    public User findUserById(@PathVariable("userId") Integer id) {
        return userRepository.findUserById(id);
    }

    @GetMapping("/api/users/{userId}/services")
    public List<Service> findAllServicesForUser(@PathVariable("userId") Integer userId) {
        return userRepository.findUserById(userId).getServices();
    }

    @PostMapping("/api/users")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("/api/users/{userId}/services/{serviceId}")
    public User createServiceForUser(@PathVariable("userId") Integer userId,
                                     @PathVariable("serviceId") Integer serviceId) {
        Service service = serviceRepository.findServiceById(serviceId);
        User user = userRepository.findUserById(userId);
        List<Service> services = user.getServices();
        services.add(service);
        user.setServices(services);
        return userRepository.save(user);
    }

    @PutMapping("/api/users/{userId}")
    public User updateUser(@PathVariable("userId") Integer id, @RequestBody User userUpdates) {
        User user = userRepository.findUserById(id);
        user.setUsername(userUpdates.getUsername());
        return userRepository.save(user);
    }

    @DeleteMapping("/api/users/{userId}")
    public void deleteUser(@PathVariable("userId") Integer id) {
        userRepository.deleteById(id);
    }

    @DeleteMapping("/api/users/{userId}/services/{serviceId}")
    public User deleteServiceFromUser(@PathVariable("userId") Integer userId,
                                     @PathVariable("serviceId") Integer serviceId) {
        Service service = serviceRepository.findServiceById(serviceId);
        User user = userRepository.findUserById(userId);
        List<Service> services = user.getServices();
        services.remove(service);
        user.setServices(services);
        return userRepository.save(user);
    }
}