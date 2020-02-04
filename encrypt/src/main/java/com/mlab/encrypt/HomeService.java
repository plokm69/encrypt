package com.mlab.encrypt;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public class HomeService {
	
	public String home() throws Exception{
		return "home";
	}
	
	public String test(HttpServletRequest request) throws Exception{
		
		String val = (String)request.getParameter("test");
		String enResult = "";
		String deResult = "";
		
		StringEncrypter encrypter = new StringEncrypter();
		
		enResult = encrypter.encrypt(val);
		deResult = encrypter.decrypt(enResult);
		
		System.out.println("enval ] "+ enResult);
		System.out.println("deVal ] "+ deResult);
		
		return "home";
	}
	
}
