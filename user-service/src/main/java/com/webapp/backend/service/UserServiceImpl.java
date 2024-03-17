package com.webapp.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.webapp.backend.feingclient.AddressFeignClient;
import com.webapp.backend.model.User;
import com.webapp.backend.repository.UserRepository;
import com.webapp.backend.request.UserRequest;
import com.webapp.backend.responce.AddressResponce;
import com.webapp.backend.responce.UserResponce;

import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	WebClient webClient;
	
	@Autowired
	AddressFeignClient addressFeignClient;

	public UserResponce createUser(UserRequest userRequest) {

		User user = new User();
		
		user.setUsername(userRequest.getUsername());
		user.setPassword(userRequest.getPassword());
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		user.setDob(userRequest.getDob());
		user.setPhoneNumber(userRequest.getPhoneNumber());
		user.setValid(userRequest.getValid());
		user.setRole(userRequest.getRole());
		
		user.setAddressId(userRequest.getAddressId());
		
		user = userRepository.save(user);

		
		UserResponce userResponse = new UserResponce(user);
		
//		userResponse.setAddressResponse(getAddressById(user.getAddressId()));

		userResponse.setAddressResponse(addressFeignClient.getById(user.getAddressId()));

		
		return userResponse;
		
		
	}
	
	public UserResponce getById (long id) {
		User user = userRepository.findById(id).get();
		UserResponce userResponse = new UserResponce(user);
		
//		userResponse.setAddressResponse(getAddressById(user.getAddressId()));
		
		userResponse.setAddressResponse(addressFeignClient.getById(user.getAddressId()));
		
		
		return userResponse;
	}
	
	public AddressResponce getAddressById (long addressId) {
		Mono<AddressResponce> addressResponse = 
				webClient.get().uri("/getById/" + addressId)
		.retrieve().bodyToMono(AddressResponce.class);
		
		return addressResponse.block();
	}
	
	
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
		
	}


	@Override
	public User updateUser(Long id, User user) {
		if (userRepository.existsById(id)) {
			user.setId(id);
			return userRepository.save(user);
		}
		return null; // or throw an exception indicating user not found
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	 public Long getUserIdByUsername(String username) {
        return userRepository.findUserIdByUsername(username);
    }

	
	
}
