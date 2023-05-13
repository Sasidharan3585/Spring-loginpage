package com.crash4.userservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Getter
//@Setter
@Data
//@ToString
@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode

@Entity
public class User {
	@Id
	@Column(length = 30)
	private  String emailId;    //login
	@Column(length = 30)
	private  String  username;
	@Column(length = 30)
	private String password;
	@Column(length = 30 )
	private String  contactNumber;
	

}
