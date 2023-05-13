package com.crash4.userservice.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crash4.userservice.model.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, String> {
	
	public List<User>  findAllByUsername(String username);
	
	public boolean  existsByEmailIdAndPassword(String emailId,String password);

}
