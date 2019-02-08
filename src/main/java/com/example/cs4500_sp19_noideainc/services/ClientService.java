package com.example.cs4500_sp19_noideainc.services;

import java.util.List;

import com.example.cs4500_sp19_noideainc.models.Service;
import com.example.cs4500_sp19_noideainc.models.ServiceClientAssociation;
import com.example.cs4500_sp19_noideainc.repositories.ServiceRepository;
import com.example.cs4500_sp19_noideainc.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @GetMapping("/api/users/{userId}/services")
    public List<ServiceClientAssociation> findAllService(@PathVariable("userId") Integer uid) {
        return clientRepository.findClientById(uid).getPreviousServices();
    }

    @GetMapping("/api/users/{userId}/services/{serviceId}")
    public Service findServiceById(@PathVariable("userId") Integer uid, @PathVariable("serviceId") Integer sid) {
        return serviceRepository.findServiceById(sid);
    }

}