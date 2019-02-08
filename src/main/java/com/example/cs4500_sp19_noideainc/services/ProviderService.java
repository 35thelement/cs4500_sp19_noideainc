package com.example.cs4500_sp19_noideainc.services;

import java.util.List;

import com.example.cs4500_sp19_noideainc.models.Provider;
import com.example.cs4500_sp19_noideainc.models.Service;
import com.example.cs4500_sp19_noideainc.models.ServiceProviderAssociation;
import com.example.cs4500_sp19_noideainc.repositories.ProviderRepository;
import com.example.cs4500_sp19_noideainc.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

public class ProviderService {
    @Autowired
    ProviderRepository providerRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @GetMapping("/api/users/{userId}/services")
    public List<ServiceProviderAssociation> findAllService(@PathVariable("userId") Integer uid) {
        return providerRepository.findProviderById(uid).getServices();
    }

    @GetMapping("/api/users/{userId}/services/{serviceId}")
    public Service findServiceById(@PathVariable("userId") Integer uid, @PathVariable("serviceId") Integer sid) {
        return serviceRepository.findServiceById(sid);
    }

    @PostMapping("/api/users/{userId}/services")
    public Provider createService(@PathVariable("userId") Integer uid, @RequestBody Service service) {
        Provider provider = providerRepository.findProviderById(uid);
        List<ServiceProviderAssociation> associations = provider.getServices();
        associations.add(new ServiceProviderAssociation(provider, service));
        providerRepository.findProviderById(uid).setServices(associations);
        return providerRepository.findProviderById(uid);
    }

    @PostMapping("/api/users/{userId}/services/{serviceId}")
    public Provider createServiceById(@PathVariable("userId") Integer uid, @PathVariable("serviceId") Integer sid) {
        Service service = serviceRepository.findServiceById(sid);
        return createService(uid, service);
    }

    @DeleteMapping("/api/users/{userId}/services/{serviceId}")
    public void deleteService(@PathVariable("userId") Integer uid, @PathVariable("serviceId") Integer sid) {
        Provider provider = providerRepository.findProviderById(uid);
        List<ServiceProviderAssociation> associations = provider.getServices();
        Service service = serviceRepository.findServiceById(sid);
        associations.remove(new ServiceProviderAssociation(provider, service));
        providerRepository.findProviderById(uid).setServices(associations);
    }
}
