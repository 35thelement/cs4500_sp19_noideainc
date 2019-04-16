package com.example.cs4500_sp19_noideainc.services;

import java.util.List;

import com.example.cs4500_sp19_noideainc.models.Address;
import com.example.cs4500_sp19_noideainc.models.Service;
import com.example.cs4500_sp19_noideainc.repositories.AddressRepository;
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
    
    AddressRepository addressRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @GetMapping("/api/users")
    public List<User> findAllUser() {
        return userRepository.findAllUsers();
    }
    
    @GetMapping("/api/user_address/{Id}")
    public Address findAddressByUserId(@PathVariable("Id") Integer id) {
        return addressRepository.findAddressByUserId(id);
    }
    
    @GetMapping("/api/addresses/{Id}")
    public Address findAddressById(@PathVariable("Id") Integer id) {
        return addressRepository.findAddressById(id);
    }
    
    @GetMapping("/api/addresses")
    public List<Address> findAllAddress() {
        return addressRepository.findAllAddresses();
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
        user.setFirstName(userUpdates.getFirstName());
        user.setLastName(userUpdates.getLastName());
        user.setPassword(userUpdates.getPassword());
        user.setServices(userUpdates.getServices());
        user.setFrequentlyAskedAnswers(user.getFrequentlyAskedAnswers());
        user.setBusinessName(userUpdates.getBusinessName());
        user.setYearFounded(userUpdates.getYearFounded());
        user.setNumOfEmployees(userUpdates.getNumOfEmployees());
        user.setAddresses(userUpdates.getAddresses());
        user.setBusinessEmail(userUpdates.getBusinessEmail());
        user.setPaymentMethods(userUpdates.getPaymentMethods());
        user.setFacebook(userUpdates.getFacebook());
        user.setInstagram(userUpdates.getInstagram());
        user.setTwitter(userUpdates.getTwitter());
        user.setUserType(userUpdates.getUserType());
        return userRepository.save(user);
    }
    
    @PutMapping("/api/profile/{userId}")
	public User updateProfile(@PathVariable("userId") Integer id, @RequestBody User userUpdates) {
    	User user = userRepository.findUserById(id);
        user.setFirstName(userUpdates.getFirstName());
        user.setLastName(userUpdates.getLastName());
    	user.setBirthday(userUpdates.getBirthday());
    	user.setAddresses(userUpdates.getAddresses());
    	
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