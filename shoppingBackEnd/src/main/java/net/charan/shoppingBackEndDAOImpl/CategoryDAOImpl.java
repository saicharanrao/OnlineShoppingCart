package net.charan.shoppingBackEndDAOImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import net.charan.shoppingBackEndDAO.CategoryDAO;
import net.charan.shoppingBackEndDTO.Category;

@Repository("categoryDAO")
public class CategoryDAOImpl implements CategoryDAO {

	private static List<Category> categories = new ArrayList<>();

	static {

		Category category = new Category();

		// Adding First category
		category.setId(1);
		category.setName("Television");
		category.setDescription("Description for cat1");
		category.setImageURL("CAT1.png");

		categories.add(category);

		category = new Category();
		// Adding Second category
		category.setId(2);
		category.setName("Tablet");
		category.setDescription("Description for cat2");
		category.setImageURL("CAT2.png");

		categories.add(category);

	}

	@Override
	public List<Category> list() {
		// TODO Auto-generated method stub
		return categories;
	}

	@Override
	public Category get(int id) {
		 
		for(Category category:categories)
		{
			if(category.getId() == id)
				return category;
		}
		
		return null;
	}

}
