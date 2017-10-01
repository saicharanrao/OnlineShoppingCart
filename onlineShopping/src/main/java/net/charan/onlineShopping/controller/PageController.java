package net.charan.onlineShopping.controller;

 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.charan.onlineShopping.exception.ProductNotFoundException;
import net.charan.shoppingBackEndDAO.CategoryDAO;
import net.charan.shoppingBackEndDAO.ProductDAO;
import net.charan.shoppingBackEndDTO.Category;
import net.charan.shoppingBackEndDTO.Product;

@Controller
public class PageController {
	
	private static final Logger logger = LoggerFactory.getLogger(PageController.class);
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@RequestMapping(value = {"/","/index","/home"})
	public ModelAndView index(@RequestParam(name="logout",required=false)String logout)
	{
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title", "Home Page");
		
		logger.info("Inside PageController index method - INFO");
		logger.info("Inside PageController index method - DEBUG");
		
		mv.addObject("categories", categoryDAO.list()); //Passing the list of categories
		
		if(logout!=null) {
			mv.addObject("message", "You have successfully logged out!");			
		}
		
		mv.addObject("userClickHome",true);
		return mv;
	}
	
	@RequestMapping(value = {"/about"})
	public ModelAndView about()
	{
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title", "About Us");
		mv.addObject("userClickAbout",true);
		return mv;
	}
	
	@RequestMapping(value = {"/contact"})
	public ModelAndView contact()
	{
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title", "Contact Us");
		mv.addObject("userClickContact",true);
		return mv;
	}
	
	/*
	 * Methods to load all products and depending on Category
	 */
	@RequestMapping(value = {"/show/all/products"})
	public ModelAndView showAllProducts()
	{
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title", "All Products");
		mv.addObject("categories", categoryDAO.list()); //Passing the list of categories
		mv.addObject("userClickAllProducts",true);
		return mv;
	}
	
	@RequestMapping(value = {"/show/category/{id}/products"})
	public ModelAndView showCategoryProducts(@PathVariable("id") int id) 
	{
		ModelAndView mv = new ModelAndView("page");
		
		//Create Category object to fetch Single Category
		Category category = null;
		category = categoryDAO.get(id);
		
		mv.addObject("title", category.getName());
		mv.addObject("categories", categoryDAO.list()); //Passing the list of categories
		mv.addObject("category", category); //Passing the single category
		
		mv.addObject("userClickCategoryProducts",true);
		return mv;
	}
	
	//Request for viewing a single product
	
	@RequestMapping(value = "/show/{id}/product")
	public ModelAndView showSingleProduct (@PathVariable int id) throws ProductNotFoundException
	{
		ModelAndView mv = new ModelAndView("page");
		
		Product product = productDAO.get(id);
		
		if(product == null)throw new ProductNotFoundException();
		//Updating the view count
		product.setViews(product.getViews()+1);
		productDAO.update(product);
		
		mv.addObject("title",product.getName());
		mv.addObject("product", product);
		
		mv.addObject("userClickShowProduct", true);
		
		return mv;
	}
	
	// This is for Login page
	@RequestMapping(value = {"/login"})
	public ModelAndView login(@RequestParam(name="error" , required = false) String error,
			@RequestParam(name="logout" , required = false) String logout)
	{
		ModelAndView mv = new ModelAndView("login");
		
		if(error != null)
		{
			mv.addObject("message","Invalid Username and Password !");
		}
		
		if(logout != null)
		{
			mv.addObject("logout","You have successfully logged out !");
		}
		
		mv.addObject("title", "Login");
		 
		return mv;
	}
	
	//Request mapping for Access Denied Page
	@RequestMapping(value = {"/access-denied"})
	public ModelAndView accessDenied()
	{
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("title", "403 - Access Denied");
		mv.addObject("errorTitle", "Sorry ! You dont have access to this page !");
		mv.addObject("errorDescription","You are not authorized to view this Page !");
		return mv;
	}
	
	//To Handle logout
	@RequestMapping(value = {"/perform-logout"})
	public String logout(HttpServletRequest request, HttpServletResponse response)
	{
		 
		//Get the authentication
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
		
		
		return "redirect:/login?logout";
	}
	
	@RequestMapping(value="/membership")
	public ModelAndView register() {
		ModelAndView mv= new ModelAndView("page");
		
		logger.info("Page Controller membership called!");
		
		return mv;
	}
	
}
