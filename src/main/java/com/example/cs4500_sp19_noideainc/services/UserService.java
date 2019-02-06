package com.example.cs4500_sp19_noideainc.services;

import java.util.List;

import com.example.cs4500_sp19_noideainc.models.Service;
import com.example.cs4500_sp19_noideainc.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.cs4500_sp19_noideainc.models.User;
import com.example.cs4500_sp19_noideainc.repositories.UserRepository;

@RestController
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
    public User findUserById(
            @PathVariable("userId") Integer id) {
        return userRepository.findUserById(id);
    }

    @GetMapping("/api/users/{userId}/services")
    public List<Service> findAllService(@PathVariable("userId") Integer uid) {
        return userRepository.findUserById(uid).getServices();
    }

    @GetMapping("/api/users/{userId}/services/{serviceId}")
    public Service findServiceById(@PathVariable("userId") Integer uid, @PathVariable("serviceId") Integer sid) {
        return userRepository.findUserById(uid).getServiceById(sid);
    }

    @PostMapping("/api/users")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("/api/users/{userId}/services")
    public User createService(@PathVariable("userId") Integer uid, @RequestBody Service service) {
        List<Service> services = userRepository.findUserById(uid).getServices();
        services.add(service);
        userRepository.findUserById(uid).setServices(services);
        return userRepository.findUserById(uid);
    }

    @PostMapping("/api/users/{userId}/services/{serviceId}")
    public User createServiceById(@PathVariable("userId") Integer uid, @PathVariable("serviceId") Integer sid) {
        Service service = serviceRepository.findServiceById(sid);
        return createService(uid, service);
    }

    @PutMapping("/api/users/{userId}")
    public User updateUser(@PathVariable("userId") Integer id, @RequestBody User userUpdates) {
        User user = userRepository.findUserById(id);
        user.setRole(userUpdates.getRole());
        return userRepository.save(user);
    }

    @DeleteMapping("/api/users/{userId}")
    public void deleteUser(@PathVariable("userId") Integer id) {
        userRepository.deleteById(id);
    }

    @DeleteMapping("/api/users/{userId}/services/{serviceId}")
    public void deleteService(@PathVariable("userId") Integer uid, @PathVariable("serviceId") Integer sid) {
        User user = userRepository.findUserById(uid);
        List<Service> services = user.getServices();
        Service service = user.getServiceById(sid);
        services.remove(service);
        userRepository.findUserById(uid).setServices(services);
    }
}