package net.charan.shoppingBackEndDAO;

import java.util.List;

import net.charan.shoppingBackEndDTO.Category;

public interface CategoryDAO {
	
	List<Category> list();
	Category get(int id);
}
