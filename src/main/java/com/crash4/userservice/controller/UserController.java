package com.crash4.userservice.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.crash4.userservice.exceptions.EmailIdAlreadyExistException;
import com.crash4.userservice.model.Cart;
import com.crash4.userservice.model.User;
import com.crash4.userservice.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService service;

	@Autowired
	private WebClient.Builder webClientBuilder;

//	@PostMapping("/add")
//	public ResponseEntity<?> addUser(@RequestBody User user) {
//		ResponseEntity<?> entity = new ResponseEntity<String>("Error in Creation of User", HttpStatus.BAD_REQUEST);
//		User userObj = service.addUser(user);
//		if (userObj != null) {
//			entity = new ResponseEntity<String>("User added Successfully", HttpStatus.CREATED);
//		}
//		return entity;
//	}

//		
	@PostMapping("/add")
	public ResponseEntity<?> addUser(@RequestBody User user) {
		ResponseEntity<?> entity =  null;
		try {
			 User userObj = service.addUser(user);
			entity = new ResponseEntity<String>("User added Successfully", HttpStatus.CREATED);
		} catch (EmailIdAlreadyExistException e) {
			 entity = new ResponseEntity<String>("Error...... "+e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

//	@PostMapping("/add")
//	public ResponseEntity<?> addUser(@RequestBody User user) throws EmailIdAlreadyExistException {
//		User userObj = service.addUser(user);
//		ResponseEntity<?> entity = new ResponseEntity<String>("User added Successfully", HttpStatus.CREATED);
//		return entity;
//	}

	@GetMapping("/users")
	public ResponseEntity<?> getAllUser() {
		List<User> userList = service.getAllUser();
		ResponseEntity<?> entity = new ResponseEntity<List<User>>(userList, HttpStatus.OK);
		return entity;
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<?> getUserByEmailId(@PathVariable("email") String email) {
		ResponseEntity<?> entity = new ResponseEntity<String>("Invalid Email Id", HttpStatus.BAD_REQUEST);
		User userObj = service.getUserByEmailId(email);
		if (userObj != null) {
			entity = new ResponseEntity<User>(userObj, HttpStatus.OK);
		}
		return entity;
	}

	@GetMapping("/username/{username}")
	public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username) {
		ResponseEntity<?> entity = new ResponseEntity<String>("Invalid Username", HttpStatus.BAD_REQUEST);
		List<User> userList = service.getUserByUsername(username);
		if (userList.isEmpty() == false) {
			entity = new ResponseEntity<List<User>>(userList, HttpStatus.OK);
		}
		return entity;
	}

	@PostMapping("/login")
	public ResponseEntity<?> validateUser(@RequestBody User user) {
		ResponseEntity<?> entity = new ResponseEntity<String>("Invalid Username/Password", HttpStatus.BAD_REQUEST);
		boolean isValid = service.validateUser(user);
		if (isValid) {
			// entity = new ResponseEntity<String>("Logged in Successfully....",
			// HttpStatus.OK);
//		 Cart  cart =  webClientBuilder.build()
//		  .get()
//		  .uri("http://localhost:8082/cart/"+user.getEmailId())
//		  .retrieve()
//		  .bodyToMono(Cart.class)
//		  .block();
//		 if (cart!=null) {
//		 entity = new ResponseEntity<Cart>(cart, HttpStatus.OK);
//		 }
//		 else {
//			 entity = new ResponseEntity<String>("No items in the cart" ,HttpStatus.OK);
//		 }
			
			String  token =getToken(user.getEmailId());
			entity = new ResponseEntity<String>(token,HttpStatus.OK);

		}
		return entity;
	}

	@DeleteMapping("/delete/{emailId}")
	public ResponseEntity<?> deleteUser(@PathVariable String emailId) {
		ResponseEntity<?> entity = new ResponseEntity<String>("Invalid  EmailId", HttpStatus.BAD_REQUEST);
		boolean isDeleted = service.deleteUserByEmailId(emailId);
		if (isDeleted) {
			entity = new ResponseEntity<String>("User with  Email Id : " + emailId + " Deleted  Successfully....",
					HttpStatus.OK);
		}
		return entity;
	}

	@PutMapping("/password")
	public ResponseEntity<?> changePassword(@RequestBody User user) {
		ResponseEntity<?> entity = new ResponseEntity<String>("Invalid  EmailId", HttpStatus.BAD_REQUEST);
		boolean isChanged = service.changePassword(user);
		if (isChanged) {
			entity = new ResponseEntity<String>(
					"User with  Email Id : " + user.getEmailId() + " password changed  Successfully....",
					HttpStatus.OK);
		}
		return entity;
	}

	@PutMapping("/profile")
	public ResponseEntity<?> changeProfile(@RequestBody User user) {
		ResponseEntity<?> entity = new ResponseEntity<String>("Invalid  EmailId or username", HttpStatus.BAD_REQUEST);
		boolean isChanged = service.changeProfile(user);
		if (isChanged) {
			entity = new ResponseEntity<String>(
					"User with  Email Id : " + user.getEmailId() + " updated  Successfully....", HttpStatus.OK);
		}
		return entity;
	}

	@ExceptionHandler(EmailIdAlreadyExistException.class)
	public ResponseEntity<?> exceptionHandler(EmailIdAlreadyExistException e) {
		ResponseEntity<?> entity = new ResponseEntity<String>("Error while creating User object.... " + e.getMessage(),
				HttpStatus.BAD_REQUEST);
		return entity;
	}

	private  String getToken(String emailId) {
	
	// long tim=  new Date().getTime() +(1000*60*10);
	long tim = System.currentTimeMillis()+1000*60*10 ;
		return Jwts.builder()
		.setIssuedAt(new  Date())
		.setSubject(emailId)
	//	.setExpiration(new  Date(tim))
		.signWith(SignatureAlgorithm.HS256, "WiproBatch4")
		 .compact();
		
	}
		

}
