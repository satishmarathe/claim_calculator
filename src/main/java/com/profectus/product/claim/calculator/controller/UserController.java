package com.profectus.product.claim.calculator.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.profectus.product.claim.calculator.repository.User;
import com.profectus.product.claim.calculator.service.UserService;

import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping(path="/user")
public class UserController {
	
	private static final Logger logger = LogManager.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@CrossOrigin
	@PostMapping(path="/add")
	public ResponseEntity<?> getFestivals() {
		try {
			System.out.println("<<<< in a good condition >>>>");
			User user = new User();
			user.setEmail("a@a.com");
			user.setId(100);
			user.setName("abc");
			return ResponseEntity.status(200).body(userService.save(user));
		}catch(Exception e) {
			
		}
		return null;
		
	}
}















