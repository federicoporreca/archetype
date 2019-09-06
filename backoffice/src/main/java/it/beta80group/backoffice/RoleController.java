package it.beta80group.backoffice;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.beta80group.middleware.RoleService;
import it.beta80group.middleware.dto.PageRequestDto;
import it.beta80group.middleware.model.Permission;
import it.beta80group.middleware.model.Role;
import it.beta80group.middleware.validation.OnCreate;

@RestController
@RequestMapping("${api.basepath}${api.endpoints.roles}")
//@Validated
public class RoleController {

	@Autowired
	private RoleService service;
	
//	@ExceptionHandler(ConstraintViolationException.class)
//	public ResponseEntity<Role> handleConstraintViolationException() {
//		return new ResponseEntity<Role>(HttpStatus.BAD_REQUEST);
//	}
	
	@PostMapping
//	@Validated(OnCreate.class)
	public Role create(@Valid @RequestBody Role role) {
		return service.create(role);
	}

	@GetMapping
	public Page<Role> getAll(@Valid @RequestBody PageRequestDto request) {
		return service.getAll(request);
	}
	
	@GetMapping("/{id}")
	public Role getOne(@PathVariable Integer id) {
		return service.getOne(id);
	}
	
	@PutMapping("/{id}")
//	@Validated(OnCreate.class)
	public Role update(@PathVariable Integer id, @Valid @RequestBody Role role) throws IllegalAccessException, InvocationTargetException {
		return service.update(id, role);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		service.delete(id);
	}
	
}
