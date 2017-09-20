package net.charan.onlineShopping.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import net.charan.onlineShopping.model.RegisterModel;
import net.charan.shoppingBackEndDAO.UserDAO;
import net.charan.shoppingBackEndDTO.Address;
import net.charan.shoppingBackEndDTO.Cart;
import net.charan.shoppingBackEndDTO.User;

@Component
public class RegisterHandler {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public RegisterModel init() {
		return new RegisterModel();
	}

	public void addUser(RegisterModel registerModel, User user) {
		registerModel.setUser(user);
	}

	public void addBilling(RegisterModel registerModel, Address billing) {
		registerModel.setBilling(billing);
	}

	public String saveAll(RegisterModel model) {

		String transitionValue = "success";

		// Fetch the user
		User user = model.getUser();

		if (user.getRole().equals("USER")) {
			Cart cart = new Cart();
			cart.setUser(user);
			user.setCart(cart);
		}
		
		//Before pushing into DB, we have to encode our password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		// Saving user

		userDAO.add(user);

		// Get the address
		Address billing = model.getBilling();

		billing.setUserId(user.getId());

		billing.setBilling(true);

		userDAO.addAddress(billing);

		return transitionValue;

	}
	
	
	public String validateUser(User user, MessageContext error){
		
		String transitionValue = "success";
		
		if( ! (user.getPassword().equals(user.getConfirmPassword())))
		{	
			error.addMessage(new MessageBuilder().error()
					.source("confirmPassword")
					.defaultText("Password does not match Confirm Password!").build());
			
			transitionValue = "failure";
		}
		
		//See if the emailId does not exist previously
		
		if(userDAO.getByEmail(user.getEmail()) != null)
		{	
			error.addMessage(new MessageBuilder().error()
					.source("email")
					.defaultText("Email Id already exists!").build());
			transitionValue = "failure";
		}
		
		return transitionValue;
	}

}
