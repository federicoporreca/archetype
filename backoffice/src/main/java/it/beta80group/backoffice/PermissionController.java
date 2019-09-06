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

import it.beta80group.middleware.PermissionService;
import it.beta80group.middleware.dto.PageRequestDto;
import it.beta80group.middleware.model.Permission;
import it.beta80group.middleware.validation.OnCreate;

@RestController
@RequestMapping("${api.basepath}${api.endpoints.permissions}")
@Validated
public class PermissionController {
	
	@Autowired
	private PermissionService service;
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Permission> handleConstraintViolationException() {
		return new ResponseEntity<Permission>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping
	@Validated(OnCreate.class)
	public Permission create(@Valid @RequestBody Permission permission) {
		return service.create(permission);
	}

	@GetMapping
	public Page<Permission> getAll(@Valid @RequestBody PageRequestDto request) {
		return service.getAll(request);
	}
	
	@GetMapping("/{id}")
	public Permission getOne(@PathVariable Integer id) {
		return service.getOne(id);
	}
	
	@PutMapping("/{id}")
	@Validated(OnCreate.class)
	public Permission update(@PathVariable Integer id, @Valid @RequestBody Permission permission) throws IllegalAccessException, InvocationTargetException {
		return service.update(id, permission);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		service.delete(id);
	}
}
