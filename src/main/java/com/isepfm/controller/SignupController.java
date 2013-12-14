package com.isepfm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.isepfm.bdd.TestJDBC;

@Controller
@RequestMapping("/signup")
public class SignupController {
	
	@RequestMapping(method = RequestMethod.GET)
	public void printWelcome( ) {
 
	}
}
