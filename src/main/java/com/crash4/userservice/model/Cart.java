package com.crash4.userservice.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
 
public class Cart {
	@Id
	private int cartId;
 	private List<Item> itemList;
}
