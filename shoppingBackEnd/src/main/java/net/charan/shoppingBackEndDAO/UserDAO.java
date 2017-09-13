package net.charan.shoppingBackEndDAO;

import net.charan.shoppingBackEndDTO.Address;
import net.charan.shoppingBackEndDTO.Cart;
import net.charan.shoppingBackEndDTO.User;

public interface UserDAO {
	 

		boolean add(User user);
		
		 
		boolean addAddress(Address address);
		
		boolean addCart(Cart cart);
}
