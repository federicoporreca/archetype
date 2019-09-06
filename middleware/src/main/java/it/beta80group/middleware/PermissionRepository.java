package it.beta80group.middleware;

import org.springframework.data.repository.PagingAndSortingRepository;

import it.beta80group.middleware.model.Permission;

public interface PermissionRepository extends PagingAndSortingRepository<Permission, Integer> {

}
