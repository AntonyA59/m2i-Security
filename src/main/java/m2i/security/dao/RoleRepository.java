package m2i.security.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import m2i.security.model.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
	Optional<Role> findByName(String name);
}
