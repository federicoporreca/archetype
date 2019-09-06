package it.beta80group.middleware;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.beta80group.middleware.dto.PageRequestDto;
import it.beta80group.middleware.model.Permission;
import it.beta80group.middleware.utils.NullAwareBeanUtilsBean;

@Service
public class PermissionService {

	@Autowired
	private PermissionRepository repo;
	
	@Autowired
	private NullAwareBeanUtilsBean utilsBean;

	public Permission create(Permission permission) {
		return repo.save(permission);
	}

	public Page<Permission> getAll(PageRequestDto request) {
		return repo.findAll(PageRequest.of(request.getPage(), request.getSize()));
	}

	public Permission getOne(Integer id) {
		return repo.findById(id).orElseThrow(() -> {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		});
	}

	public Permission update(Integer id, Permission permission) throws IllegalAccessException, InvocationTargetException {
		Permission permissionToUpdate = repo.findById(id).orElseThrow(() -> {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		});
		utilsBean.copyProperties(permissionToUpdate, permission);
		return repo.save(permissionToUpdate);
	}

	public void delete(Integer id) {
		try {
			repo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

}
