package com.mlab.encrypt;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private HomeService homeS;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() throws Exception{
//		return "home";
		return homeS.home();
	}
	
	@RequestMapping(value = "/test")
	public String test(HttpServletRequest request) throws Exception{
		return homeS.test(request);
	}
}
