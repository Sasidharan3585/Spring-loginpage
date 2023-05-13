package com.crash4.userservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crash4.userservice.exceptions.EmailIdAlreadyExistException;
import com.crash4.userservice.model.User;
import com.crash4.userservice.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	public List<User> getAllUser() {
		List<User> userList = userRepo.findAll();
		return userList;
	}

//	public  User   addUser(User  user) {
//		User  userObj=null;
//		Optional<User> op= userRepo.findById(user.getEmailId());
//		if (op.isEmpty()) {
//			userObj =userRepo.save(user);
//		}
//		return  userObj;
//	}

	public User addUser(User user) throws EmailIdAlreadyExistException {
		Optional<User> op = userRepo.findById(user.getEmailId());
		if (op.isPresent()) {
			throw new EmailIdAlreadyExistException("Email Id Already Exist.");
		}
		User userObj = userRepo.save(user);
		return userObj;
	}

	public User getUserByEmailId(String email) {
		Optional<User> opt = userRepo.findById(email);
		User userObj = opt.isPresent() ? opt.get() : null;
		return userObj;
	}

	public List<User> getUserByUsername(String username) {
		List<User> userList = userRepo.findAllByUsername(username);
		return userList;
	}

	public boolean deleteUserByEmailId(String email) {
		Optional<User> opt = userRepo.findById(email);
		boolean isDeleted = false;
		if (opt.isPresent()) {
			userRepo.deleteById(email);
			isDeleted = true;
		}
		return isDeleted;
	}

//	public boolean validateUser(User user) {
//		Optional<User> opt = userRepo.findById(user.getEmailId());
//		boolean isValid = false;
//		if (opt.isPresent()) {
//			User userObj = opt.get();
//			if (user.getPassword().equals(userObj.getPassword())) {
//				isValid = true;
//			}
//		}
//
//		return isValid;
//	}

	public boolean validateUser(User user) {
		boolean isValid = userRepo.existsByEmailIdAndPassword(user.getEmailId(), user.getPassword());
		return isValid;
	}

	public boolean changePassword(User user) {
		boolean isValid = false;
		if (user.getEmailId() != null) {
			Optional<User> op = userRepo.findById(user.getEmailId());
			if (op.isPresent()) {
				User userObj = op.get();
				userObj.setPassword(user.getPassword());
				isValid = true;
				userRepo.save(userObj);

			}
		}
		return isValid;
	}

	public boolean changeProfile(User user) {
		boolean isValid = false;
		if (user.getEmailId() != null && user.getUsername() != null && 
				user.getUsername().trim().isEmpty() == false) {
			Optional<User> op = userRepo.findById(user.getEmailId());
			if (op.isPresent()) {
				User userObj = op.get();
				userObj.setUsername(user.getUsername());
				if (user.getContactNumber() != null) {
					userObj.setContactNumber(user.getContactNumber());
				}
				isValid = true;
				userRepo.save(userObj);
			}
		}
		return isValid;
	}

}
