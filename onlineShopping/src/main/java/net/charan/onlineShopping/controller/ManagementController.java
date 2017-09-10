package net.charan.onlineShopping.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.charan.onlineShopping.util.FileUploadUtility;
import net.charan.onlineShopping.validator.ProductValidator;
import net.charan.shoppingBackEndDAO.CategoryDAO;
import net.charan.shoppingBackEndDAO.ProductDAO;
import net.charan.shoppingBackEndDTO.Category;
import net.charan.shoppingBackEndDTO.Product;

@Controller
@RequestMapping("/manage")
public class ManagementController {

	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private ProductDAO productDAO;

	private static final Logger logger = LoggerFactory.getLogger(ManagementController.class);

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public ModelAndView showManageProducts(@RequestParam(name = "operation", required = false) String operation) {

		ModelAndView mv = new ModelAndView("page");

		mv.addObject("userClickManageProducts", true);
		mv.addObject("title", "Manage Products");
		Product nProduct = new Product();

		// Setting properties for product
		nProduct.setSupplierId(1);
		nProduct.setActive(true);

		mv.addObject("product", nProduct);

		if (operation != null) {
			if (operation.equals("product")) {
				mv.addObject("message", "Product submitted Successfully!");
			}
		}

		return mv;
	}

	// Adding a new product if user submits one
	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public String handleProductSubmission(@Valid @ModelAttribute("product") Product mProduct, BindingResult results,
			Model model, HttpServletRequest request) {

		new ProductValidator().validate(mProduct, results);

		// Check if there are any errors
		if (results.hasErrors()) {
			model.addAttribute("userClickManageProducts", true);
			model.addAttribute("title", "Manage Products");
			model.addAttribute("message", "Validation failed for Product submission !");
			return "page";
		}

		// Create a new Product record
		productDAO.add(mProduct);

		logger.info(mProduct.toString());

		if (!mProduct.getFile().getOriginalFilename().equals("")) {
			FileUploadUtility.uploadFile(request, mProduct.getFile(), mProduct.getCode());
		}

		return "redirect:/manage/products?operation=product";
	}
	
	@RequestMapping(value = "/product/{id}/activation", method = RequestMethod.POST)
	@ResponseBody
	public String handleProductActivation(@PathVariable int id)
	{	
		//Get the ID from database
		Product product = productDAO.get(id);
		
		boolean isActive = product.isActive();
		
		product.setActive(!product.isActive());
		
		productDAO.update(product);
		
		return (isActive)?"You have successfully deactivated your product with id "+product.getId()
							:"You have successfully activated your product with id "+product.getId();
	}

	// Returning categories for all request mapping
	@ModelAttribute("categories")
	public List<Category> getCategories() {

		return categoryDAO.list();
	}
}

/*
 * @Controller
 * 
 * @RequestMapping("/manage") public class ManagementController {
 * 
 * private static final Logger logger =
 * LoggerFactory.getLogger(ManagementController.class);
 * 
 * 
 * 
 * @Autowired private ProductDAO productDAO;
 * 
 * @Autowired private CategoryDAO categoryDAO;
 * 
 * @RequestMapping(value = "/products",method = RequestMethod.GET) public
 * ModelAndView
 * showManageProducts(@RequestParam(name="operation",required=false) String
 * operation){
 * 
 * ModelAndView mv = new ModelAndView("page");
 * 
 * mv.addObject("userClickManageProducts",true);
 * mv.addObject("title","Manage Products"); Product nProduct = new Product();
 * 
 * //Setting properties for product nProduct.setSupplierId(1);
 * nProduct.setActive(true);
 * 
 * mv.addObject("product",nProduct);
 * 
 * if(operation != null) { if(operation.equals("product")) {
 * mv.addObject("message","Product submitted Successfully!"); } }
 * 
 * return mv;
 * 
 * }
 * 
 * 
 * //Adding a new product if user submits one
 * 
 * @RequestMapping(value = "/products",method = RequestMethod.POST) public
 * String handleProductSubmission(@Valid @ModelAttribute("product") Product
 * mProduct,BindingResult results,Model model, HttpServletRequest request){
 * 
 * new ProductValidator().validate(mProduct, results);
 * 
 * //Check if there are any errors if(results.hasErrors()){
 * model.addAttribute("userClickManageProducts",true);
 * model.addAttribute("title","Manage Products");
 * model.addAttribute("message","Validation failed for Product submission !");
 * return "page"; }
 * 
 * 
 * logger.info(mProduct.toString());
 * 
 * productDAO.add(mProduct);
 * 
 * if(!mProduct.getFile().getOriginalFilename().equals("")){
 * FileUploadUtility.uploadFile(request, mProduct.getFile() ,
 * mProduct.getCode()); }
 * 
 * return "redirect:/manage/products?operation=product"; }
 * 
 * 
 * 
 * //Returning categories for all request mapping
 * 
 * @ModelAttribute("categories") public List<Category> getCategories(){
 * 
 * return categoryDAO.list(); } }
 */