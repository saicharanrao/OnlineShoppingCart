package net.charan.onlineShopping.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtility {
	
	private static final String ABS_PATH = "C:\\Users\\saich\\git\\OnlineShoppingCart\\onlineShopping\\src\\main\\webapp\\assests\\images\\";
	//Real path is where tomcat deploys this application
	private static String REAL_PATH = "";
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadUtility.class);
	
	public static void uploadFile(HttpServletRequest request, MultipartFile file, String code) {
		 
		//Getting real path
		REAL_PATH = request.getSession().getServletContext().getRealPath("/assets/images/");
		
		logger.info(REAL_PATH);
		
		//To make sure all directory exists
		//Please create the directories
		
		if(!new File(ABS_PATH).exists()){
			//If it doesnt exist then create one
			new File(ABS_PATH).mkdirs();
		}
		
		if(!new File(REAL_PATH).exists()){
			//If it doesnt exist then create one
			new File(REAL_PATH).mkdirs();
		}
		
		try{
			//Server upload
			file.transferTo(new File(REAL_PATH + code +".jpg"));
			//Project directory upload
			file.transferTo(new File(ABS_PATH + code +".jpg"));
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
	
}
