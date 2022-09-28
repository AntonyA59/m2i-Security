package m2i.security.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import m2i.security.dao.RoleRepository;
import m2i.security.dao.UserRepository;
import m2i.security.dto.UserDto;
import m2i.security.model.Role;
import m2i.security.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public User createUser(UserDto userDto) {
		Role userRole = roleRepository.findByName("USER").get();
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(userRole);
		
		User user = new User(
				userDto.getEmail(),
				userDto.getPassword(),
				true,
				roles
				);
		return userRepository.save(user);
	}
}
