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
import it.beta80group.middleware.model.Role;
import it.beta80group.middleware.utils.NullAwareBeanUtilsBean;

@Service
public class RoleService {

	@Autowired
	private RoleRepository repo;
	
	@Autowired
	private NullAwareBeanUtilsBean utilsBean;

	public Role create(Role role) {
		return repo.save(role);
	}

	public Page<Role> getAll(PageRequestDto request) {
		return repo.findAll(PageRequest.of(request.getPage(), request.getSize()));
	}

	public Role getOne(Integer id) {
		return repo.findById(id).orElseThrow(() -> {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		});
	}

	public Role update(Integer id, Role role) throws IllegalAccessException, InvocationTargetException {
		Role roleToUpdate = repo.findById(id).orElseThrow(() -> {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		});
		utilsBean.copyProperties(roleToUpdate, role);
		return repo.save(roleToUpdate);
	}

	public void delete(Integer id) {
		try {
			repo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

}
