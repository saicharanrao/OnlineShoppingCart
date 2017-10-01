package net.charan.shoppingBackEndDAO;

import java.util.List;

import net.charan.shoppingBackEndDTO.Cart;
import net.charan.shoppingBackEndDTO.CartLine;
import net.charan.shoppingBackEndDTO.OrderDetail;
 

public interface CartLineDAO {

	public List<CartLine> list(int cartId);
	public CartLine get(int id);	
	public boolean add(CartLine cartLine);
	public boolean update(CartLine cartLine);
	public boolean delete(CartLine cartLine);
	
	// Get CartLine based on cartId and productId
	public CartLine getByCartAndProduct(int cartId, int productId);		
		
	// updating the cart
	boolean updateCart(Cart cart);
	
	// Available cartLine List
	public List<CartLine> listAvailable(int cartId);
	
	// adding order details
	boolean addOrderDetail(OrderDetail orderDetail);

	
}
