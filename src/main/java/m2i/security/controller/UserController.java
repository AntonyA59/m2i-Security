package m2i.security.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import m2i.security.dto.UserDto;
import m2i.security.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public String RegisterUser(@Valid UserDto userDto, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return "/register";
		
		userService.createUser(userDto);
		return "/login";
	}
	
	@GetMapping("/register")
	public String getForm(UserDto userDto) {
		return "/register";
	}
}
