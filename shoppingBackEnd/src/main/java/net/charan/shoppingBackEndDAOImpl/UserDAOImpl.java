package net.charan.shoppingBackEndDAOImpl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.charan.shoppingBackEndDAO.UserDAO;
import net.charan.shoppingBackEndDTO.Address;
import net.charan.shoppingBackEndDTO.Cart;
import net.charan.shoppingBackEndDTO.User;


@Repository("userDAO")
@Transactional
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public boolean add(User user) {
		 
		try {			
			sessionFactory.getCurrentSession().persist(user);			
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean addAddress(Address address) {
		try {			
			// will look for this code later and why we need to change it
			sessionFactory.getCurrentSession().persist(address);			
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean addCart(Cart cart) {
		try {			
			// will look for this code later and why we need to change it
			sessionFactory.getCurrentSession().persist(cart);			
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

}
