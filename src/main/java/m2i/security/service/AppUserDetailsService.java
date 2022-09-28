package m2i.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import m2i.security.dao.UserRepository;
import m2i.security.model.Role;
import m2i.security.model.User;



@Service("userDetailsService")
public class AppUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> userOpt = userRepository.findByEmail(email);
		
		if(userOpt.isEmpty())
			throw new UsernameNotFoundException("Email not found");
		
		User user = userOpt.get();
		
		List<GrantedAuthority> authorities = user
				.getRoles()
				.stream()
				.flatMap(this::transformRoleAsString)
				.toList();
		
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(),
				user.getPassword(),
				user.isEnabled(), // enabled
				true, // account not expired 
				true, // account not locked
				true, // account not locked
				authorities);
	}
	
	

	private Stream<GrantedAuthority> transformRoleAsString(Role role){
		int size = role.getPrivileges().size() + 1;
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(size);
		
		authorities.add(new SimpleGrantedAuthority(role.getName()));
		
		List<? extends GrantedAuthority> privileges = 
				role.getPrivileges()
				.stream()
				.map(p -> new SimpleGrantedAuthority(p.getName()))
				.toList();
		
		authorities.addAll(privileges);
		
		return authorities.stream();
	}
	

}
