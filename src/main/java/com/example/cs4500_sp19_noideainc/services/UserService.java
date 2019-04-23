package com.example.cs4500_sp19_noideainc.services;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.cs4500_sp19_noideainc.models.Address;
import com.example.cs4500_sp19_noideainc.models.PaymentMethod;
import com.example.cs4500_sp19_noideainc.models.Service;
import com.example.cs4500_sp19_noideainc.models.ServiceAnswer;
import com.example.cs4500_sp19_noideainc.models.User;
import com.example.cs4500_sp19_noideainc.repositories.AddressRepository;
import com.example.cs4500_sp19_noideainc.repositories.ServiceRepository;
import com.example.cs4500_sp19_noideainc.repositories.PaymentMethodRepository;
import com.example.cs4500_sp19_noideainc.repositories.ServiceAnswerRepository;
import com.example.cs4500_sp19_noideainc.repositories.UserRepository;

@RestController
@CrossOrigin(origins = "*")
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    @Autowired
    ServiceAnswerRepository serviceAnswerRepository;

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
        User savedUser = userRepository.save(user);

        ArrayList<Address> addresses = new ArrayList<Address>();

        Address home = new Address();
        home.setResident(savedUser);
        home.setAddressType(0);
        addressRepository.save(home);

        Address business = new Address();
        business.setResident(savedUser);
        business.setAddressType(1);
        addressRepository.save(business);

        return userRepository.findUserById(user.getId());
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
        user.setBusinessEmail(userUpdates.getBusinessEmail());
        user.setFacebook(userUpdates.getFacebook());
        user.setInstagram(userUpdates.getInstagram());
        user.setTwitter(userUpdates.getTwitter());
        user.setUserType(userUpdates.getUserType());

        // Update the user addresses
        for(Address newAddress : userUpdates.getAddresses()) {
            addressRepository.save(newAddress);
        }
        user.setAddresses(userUpdates.getAddresses());

        // Get rid of old payment methods and replace them with the new ones.
        List<PaymentMethod> newpaymentMethods = userUpdates.getPaymentMethods();
        if(newpaymentMethods != null && newpaymentMethods.size() > 0) {

            List<PaymentMethod> currentPaymentMethods = user.getPaymentMethods();
            if(currentPaymentMethods != null && currentPaymentMethods.size() > 0) {
                for(PaymentMethod pm : currentPaymentMethods) {
                    paymentMethodRepository.delete(pm);
                }
            }

            for(PaymentMethod pm : newpaymentMethods) {
                pm.setEstablishment(user);
                paymentMethodRepository.save(pm);
            }
            user.setPaymentMethods(newpaymentMethods);
        }
        
        //Updating service answers
        ServiceAnswerService answerSvc = new ServiceAnswerService();
        List<ServiceAnswer> serviceAnswers = user.getServiceAnswers();
        List<ServiceAnswer> serviceAnswersUpdates = userUpdates.getServiceAnswers();
        if (serviceAnswers != null) {
            for (int i = 0; i < serviceAnswers.size(); i++) {
                ServiceAnswer serviceAnswer = serviceAnswerRepository.findServiceAnswerById(serviceAnswers.get(i).getId());
                ServiceAnswer serviceAnswerUpdate = serviceAnswersUpdates.get(i);

                serviceAnswer.setChoiceAnswer(serviceAnswerUpdate.getChoiceAnswer());
                serviceAnswer.setMaxRangeAnswer(serviceAnswerUpdate.getMaxRangeAnswer());
                serviceAnswer.setMinRangeAnswer(serviceAnswerUpdate.getMinRangeAnswer());
                serviceAnswer.setTrueFalseAnswer(serviceAnswerUpdate.getTrueFalseAnswer());
                serviceAnswerRepository.save(serviceAnswer);
            }
        }
        //user.setServiceAnswers(userUpdates.getServiceAnswers());

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