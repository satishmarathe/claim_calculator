package com.profectus.product.claim.calculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.profectus.product.claim.calculator.repository.User;
import com.profectus.product.claim.calculator.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public User save(User user) {
		userRepository.save(user);
		return user;
	}

}
