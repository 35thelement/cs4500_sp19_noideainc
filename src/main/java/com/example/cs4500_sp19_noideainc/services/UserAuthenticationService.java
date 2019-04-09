package com.example.cs4500_sp19_noideainc.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cs4500_sp19_noideainc.models.User;
import com.example.cs4500_sp19_noideainc.repositories.UserRepository;

@RestController
@CrossOrigin(origins = "*")
public class UserAuthenticationService {
	@Autowired
    UserRepository userRepository;
	
	@PostMapping("/api/login")
	public User login(@RequestBody User credentials, HttpSession session) {
		User findUser = userRepository.findByUserEmail(credentials.getEmail());
		if (findUser == null) {
			return null;
		}
		if (credentials.getPassword().equals(findUser.getPassword())) {
			session.setAttribute("currentUser", findUser);
			return findUser;
		} else {
			return null;
		}
	}

}