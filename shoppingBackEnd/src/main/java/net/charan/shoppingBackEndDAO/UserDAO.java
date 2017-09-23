package net.charan.shoppingBackEndDAO;

import java.util.List;

import net.charan.shoppingBackEndDTO.Address;
import net.charan.shoppingBackEndDTO.Cart;
import net.charan.shoppingBackEndDTO.User;

public interface UserDAO {
	 

		boolean add(User user);
		User getByEmail(String email);
		
		 
		boolean addAddress(Address address);
		
		Address getBillingAddress(User user);
		
		List<Address> listShippingAddresses(User user);
		
		
}
