package m2i.security.listener;


import java.util.ArrayList;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;

import m2i.security.dao.RoleRepository;
import m2i.security.model.Privilege;
import m2i.security.model.Role;

@Component
public class RoleInitListener {
	
	@Autowired
	private RoleRepository roleRepository;
	
	public void onApplicationEvent(ApplicationContextEvent event) {
		createRoleIfNotExists("USER");
		createRoleIfNotExists("ADMIN");
	}
	
	private void createRoleIfNotExists(String roleName) {
		
		Optional<Role> roleOpt = roleRepository.findByName(roleName);
		if(roleOpt.isEmpty()) {
			Role role = new Role(roleName, new ArrayList<Privilege>() );
			roleRepository.save(role);
		}
		
		
	}
}
