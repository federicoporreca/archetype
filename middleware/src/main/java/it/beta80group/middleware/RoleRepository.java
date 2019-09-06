package it.beta80group.middleware;

import org.springframework.data.repository.PagingAndSortingRepository;

import it.beta80group.middleware.model.Role;

public interface RoleRepository extends PagingAndSortingRepository<Role, Integer>{

}
