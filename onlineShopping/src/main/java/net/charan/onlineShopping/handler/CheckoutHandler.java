package net.charan.onlineShopping.handler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.charan.onlineShopping.model.CheckoutModel;
import net.charan.onlineShopping.model.UserModel;
import net.charan.shoppingBackEndDAO.CartLineDAO;
import net.charan.shoppingBackEndDAO.ProductDAO;
import net.charan.shoppingBackEndDAO.UserDAO;
import net.charan.shoppingBackEndDTO.Address;
import net.charan.shoppingBackEndDTO.Cart;
import net.charan.shoppingBackEndDTO.OrderItem;

@Component
public class CheckoutHandler {
	private static final Logger logger = LoggerFactory.getLogger(CheckoutHandler.class);
	
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private CartLineDAO cartLineDAO;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private HttpSession session;
	
	public CheckoutModel init() {
		
		CheckoutModel checkoutModel = new CheckoutModel();
		UserModel userModel = (UserModel) session.getAttribute("userModel");
		Cart cart = userModel.getCart();
				
		checkoutModel.setCartLines(cartLineDAO.listAvailable(cart.getId()));
		checkoutModel.setOrderItems(new ArrayList<OrderItem>());
		
		List<Address> addresses = userDAO.listShippingAddresses(userModel.getId());
		checkoutModel.setAddresses(addresses);
		checkoutModel.setBilling(userDAO.getBillingAddress(userModel.getId()));
		
		
		return checkoutModel;
	}
	/*
	public CheckoutModel init(String name) throws Exception {

		User user = userDAO.getByEmail(name);

		// Create a new Checkout Model Object for this method
		CheckoutModel checkoutModel = null;

		if (user != null) {         //Set all of this only if user is present
			checkoutModel = new CheckoutModel();

			checkoutModel.setUser(user);
			checkoutModel.setCart(user.getCart());
			double checkoutTotal = 0.0;
			List<CartLine> cartLines = cartLineDAO.listAvailable(user.getCart().getId());

			if(cartLines.size() == 0 ) {
				throw new Exception("No products in Cart for Checkout!");
			}
			
			for(CartLine cartLine: cartLines) {
				checkoutTotal += cartLine.getTotal();
			}
			
			checkoutModel.setCartLines(cartLines);
			checkoutModel.setCheckoutTotal(checkoutTotal);
		}

		return checkoutModel;
	}
	
	
	public List<Address> getShippingAddresses(CheckoutModel model) {
		
		//Get shipping address of the User
		List<Address> addresses = userDAO.listShippingAddresses(model.getUser().getId());
		
		if(addresses.size() == 0) {
			addresses = new ArrayList<>();
		}

		addresses.add(addresses.size(), userDAO.getBillingAddress(model.getUser().getId()));			
		
		
		//Return the addresses retrieved
		return addresses;
		
	}
	
	public String saveAddressSelection(CheckoutModel checkoutModel, int shippingId) {
		
		//This method is to note preferred address of User
		
		String transitionValue = "success";
		
		 
		
		Address shipping = userDAO.getAddress(shippingId);		
		
		checkoutModel.setShipping(shipping);
		
		return transitionValue;
		
	}
			
	
	public String saveAddress(CheckoutModel checkoutModel, Address shipping) {

		String transitionValue = "success";
		
		
		shipping.setUserId(checkoutModel.getUser().getId());
		shipping.setShipping(true);
		userDAO.addAddress(shipping);
		
		// setting the shipping id to flowScope object
		checkoutModel.setShipping(shipping);
		
		return transitionValue;
		
	}
		

	public String saveOrder(CheckoutModel checkoutModel) {
		String transitionValue = "success";	
		
		// A new order object
		OrderDetail orderDetail = new OrderDetail();
				
		// Associate User with Order 
		orderDetail.setUser(checkoutModel.getUser());
				
		// Associate shipping address with User
		orderDetail.setShipping(checkoutModel.getShipping());
		
		// Associate billing address
		Address billing = userDAO.getBillingAddress(checkoutModel.getUser().getId());		
		orderDetail.setBilling(billing);

		List<CartLine> cartLines = checkoutModel.getCartLines();
		OrderItem orderItem = null;
		
		double orderTotal = 0.0;
		int orderCount = 0;
		Product product = null;
		
		for(CartLine cartLine : cartLines) {
			
			orderItem = new OrderItem();
			
			orderItem.setBuyingPrice(cartLine.getBuyingPrice());
			orderItem.setProduct(cartLine.getProduct());
			orderItem.setProductCount(cartLine.getProductCount());
			orderItem.setTotal(cartLine.getTotal());
			
			orderItem.setOrderDetail(orderDetail);
			orderDetail.getOrderItems().add(orderItem);
			
			orderTotal += orderItem.getTotal();
			orderCount++;
			
			 
			//  Product update, Quanity reduce
			product = cartLine.getProduct();
			product.setQuantity(product.getQuantity() - cartLine.getProductCount());
			product.setPurchases(product.getPurchases() + cartLine.getProductCount());
			productDAO.update(product);
			
			// delete the cartLine
			cartLineDAO.delete(cartLine);
			

			
		}
		
		orderDetail.setOrderTotal(orderTotal);
		orderDetail.setOrderCount(orderCount);
		orderDetail.setOrderDate(new Date());
		
		// save the order
		cartLineDAO.addOrderDetail(orderDetail);

		// set it to the orderDetails of checkoutModel
		checkoutModel.setOrderDetail(orderDetail);

		
		// update the cart
		Cart cart = checkoutModel.getCart();
		cart.setGrandTotal(cart.getGrandTotal() - orderTotal);
		cart.setCartLines(cart.getCartLines() - orderCount);
		cartLineDAO.updateCart(cart);
		
		// update the cart if its in the session
		UserModel userModel = (UserModel) session.getAttribute("userModel");
		if(userModel!=null) {
			userModel.setCart(cart);
		}
		
				
		return transitionValue;		
	}

	
	public OrderDetail getOrderDetail(CheckoutModel checkoutModel) {
		return checkoutModel.getOrderDetail();
	}*/

}
