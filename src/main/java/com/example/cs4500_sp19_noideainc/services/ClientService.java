package com.example.cs4500_sp19_noideainc.services;

import java.util.List;

import com.example.cs4500_sp19_noideainc.models.Provider;
import com.example.cs4500_sp19_noideainc.models.Service;
import com.example.cs4500_sp19_noideainc.models.ServiceClientAssociation;
import com.example.cs4500_sp19_noideainc.repositories.ProviderRepository;
import com.example.cs4500_sp19_noideainc.repositories.ServiceRepository;
import com.example.cs4500_sp19_noideainc.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

public class ClientService {
    @Autowired
    ClientRepository ClientRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @GetMapping("/api/users/{userId}/services")
    public List<ServiceClientAssociation> findAllService(@PathVariable("userId") Integer uid) {
        return clientRepository.findProviderById(uid).getServices();
    }

    @GetMapping("/api/users/{userId}/services/{serviceId}")
    public Service findServiceById(@PathVariable("userId") Integer uid, @PathVariable("serviceId") Integer sid) {
        return serviceRepository.findServiceById(sid);
    }

    @PostMapping("/api/users/{userId}/services")
    public Provider createService(@PathVariable("userId") Integer uid, @RequestBody Service service) {
        Client client = ClientRepository.findClientById(uid);
        List<ServiceClientAssociation> associations = client.getServices();
        associations.add(new ServiceClientAssociation(client, service));
        clientRepository.findClientById(uid).setServices(associations);
        return clientRepository.findClientById(uid);
    }

    @PostMapping("/api/users/{userId}/services/{serviceId}")
    public Client createServiceById(@PathVariable("userId") Integer uid, @PathVariable("serviceId") Integer sid) {
        Service service = serviceRepository.findServiceById(sid);
        return createService(uid, service);
    }

    @DeleteMapping("/api/users/{userId}/services/{serviceId}")
    public void deleteService(@PathVariable("userId") Integer uid, @PathVariable("serviceId") Integer sid) {
        Client client = clientRepository.findClientById(uid);
        List<ServiceClientAssociation> associations = client.getServices();
        Service service = serviceRepository.findServiceById(sid);
        associations.remove(new ServiceClientAssociation(client, service));
        clientRepository.findClientById(uid).setServices(associations);
    }
}