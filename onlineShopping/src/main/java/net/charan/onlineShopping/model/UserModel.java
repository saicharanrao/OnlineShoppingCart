package net.charan.onlineShopping.model;

import java.io.Serializable;

import net.charan.shoppingBackEndDTO.Cart;

public class UserModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String fullName;
	private String email;
	private String role;
	private Cart cart;
	
	@Override
	public String toString() {
		return "UserModel [id=" + id + ", fullname=" + fullName + ", email=" + email + ", role=" + role + ", cart="
				+ cart + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullname) {
		this.fullName = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
}
