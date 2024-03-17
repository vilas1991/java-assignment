package com.webapp.backend.service;

import java.util.List;
import java.util.Optional;

import com.webapp.backend.model.User;
import com.webapp.backend.request.UserRequest;
import com.webapp.backend.responce.UserResponce;

public interface UserService {
	public UserResponce createUser(UserRequest userRequest);
	public UserResponce getById (long id);
	
	public List<User> getAllUsers();
    public Optional<User> getUserById(Long id);
    public User updateUser(Long id, User user);
    public void deleteUser(Long id);
    public Long getUserIdByUsername(String username);

}
