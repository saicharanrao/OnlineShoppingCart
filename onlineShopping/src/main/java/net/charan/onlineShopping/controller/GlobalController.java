package net.charan.onlineShopping.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import net.charan.onlineShopping.model.UserModel;
import net.charan.shoppingBackEndDAO.UserDAO;
import net.charan.shoppingBackEndDTO.User;

@ControllerAdvice
public class GlobalController {
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private HttpSession session;

	private UserModel userModel = null;
	private User user = null;

	@ModelAttribute("userModel")
	public UserModel getUserModel() {
		if (session.getAttribute("userModel") == null) { // There is no existing
															// model, add one
			// Authentication object
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!authentication.getPrincipal().equals("anonymousUser")) {
				// Retrieve user from DB
				user = userDAO.getByEmail(authentication.getName());

				if (user != null) {
					// New user model needed
					userModel = new UserModel();
					// setting name and the id
					userModel.setId(user.getId());
					userModel.setEmail(user.getEmail());
					userModel.setFullName(user.getFirstName() + " " + user.getLastName());
					userModel.setRole(user.getRole());

					if (user.getRole().equals("USER")) {
						// Only user has a cart
						userModel.setCart(user.getCart());
					}

					session.setAttribute("userModel", userModel);
					return userModel;
				}
			}
		}
		// If not there exists one already
		return (UserModel) session.getAttribute("userModel");
	}
}
