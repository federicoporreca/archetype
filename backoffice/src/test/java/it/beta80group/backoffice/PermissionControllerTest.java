package it.beta80group.backoffice;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.beta80group.middleware.PermissionService;
import it.beta80group.middleware.dto.PageRequestDto;
import it.beta80group.middleware.model.Permission;

@RunWith(SpringRunner.class)
@WebMvcTest
@ActiveProfiles("test")
public class PermissionControllerTest {
	
	@Value("${api.basepath}${api.endpoints.permissions}")
	private String endpoint;
	private final ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	private PermissionService service;
	
	/* Create */
	
	@Test
	public void shouldReturnStatusOkAndResourceRepresentationWhenCreatingValidResource() throws Exception {
		Permission permission = new Permission(null, "Read", true, 1);
		Permission savedPermission = new Permission(1, "Read", true, 1);
		when(service.create(permission)).thenReturn(savedPermission);
		mvc.perform(post(endpoint)
			.contentType(MediaType.APPLICATION_JSON)
			.content(mapper.writeValueAsString(permission)))
			.andExpect(status().isOk())
			.andExpect(content().json(mapper.writeValueAsString(savedPermission)));
	}
	
	@Test
	public void shouldReturnStatusBadRequestWhenCreatingInvalidResource() throws Exception {
		Permission permission = new Permission(1, "Read", true, 1);
		when(service.create(permission)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
		mvc.perform(post(endpoint)
			.contentType(MediaType.APPLICATION_JSON)
			.content(mapper.writeValueAsString(permission)))
			.andExpect(status().isBadRequest());
	}
	
	/* Read */
	
	@Test
	public void shouldReturnStatusOkAndPageRepresentationWhenGettingAllResourcesWithValidPageParams() throws Exception {
		PageRequestDto request = new PageRequestDto(0, 1);
		Permission permission = new Permission(1, "Read", true, 1);
		List<Permission> permissions = new ArrayList<Permission>();
		permissions.add(permission);
		Page<Permission> page = new PageImpl<>(permissions, PageRequest.of(request.getPage(), request.getSize()), 1);
		when(service.getAll(request)).thenReturn(page);
		mvc.perform(get(endpoint)
			.contentType(MediaType.APPLICATION_JSON)
			.content(mapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andExpect(content().json(mapper.writeValueAsString(page)));
	}
	
	@Test
	public void shouldReturnStatusBadRequestWhenGettingAllResourcesWithInvalidPageParams() throws Exception {
		PageRequestDto request = new PageRequestDto(0, 0);
		mvc.perform(get(endpoint)
			.contentType(MediaType.APPLICATION_JSON)
			.content(mapper.writeValueAsString(request)))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldReturnStatusOkAndResourceRepresentationWhenGettingOneExistingResource() throws Exception {
		Integer id = 1;
		Permission permission = new Permission(1, "Read", true, 1);
		when(service.getOne(1)).thenReturn(permission);
		mvc.perform(get(endpoint + "/" + id))
			.andExpect(status().isOk())
			.andExpect(content().json(mapper.writeValueAsString(permission)));
	}
	
	@Test
	public void shouldReturnStatusNotFoundWhenGettingOneNonexistentResource() throws Exception {
		Integer id = 1;
		when(service.getOne(1)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
		mvc.perform(get(endpoint + "/" + id))
			.andExpect(status().isNotFound());
	}
	
	/* Update */
	
	@Test
	public void shouldReturnStatusOkAndResourceRepresentationWhenUpdatingExistingResourceWithValidValues() throws Exception {
		Integer id = 1;
		Permission permission = new Permission(null, "Write", null, null);
		Permission updatedPermission = new Permission(id, "Write", true, 1);
		when(service.update(id, permission)).thenReturn(updatedPermission);
		mvc.perform(put(endpoint + "/" + id)
			.contentType(MediaType.APPLICATION_JSON)
			.content(mapper.writeValueAsString(permission)))
			.andExpect(status().isOk())
			.andExpect(content().json(mapper.writeValueAsString(updatedPermission)));
	}
	
	@Test
	public void shouldReturnStatusBadRequestWhenUpdatingNonexistentResource() throws Exception {
		Integer id = 1;
		Permission permission = new Permission(null, "Write", null, null);
		when(service.update(id, permission)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
		mvc.perform(put(endpoint + "/" + id)
			.contentType(MediaType.APPLICATION_JSON)
			.content(mapper.writeValueAsString(permission)))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldReturnStatusBadRequestWhenUpdatingExistingResourceWithInvalidValues() throws Exception {
		Integer id = 1;
		Permission permission = new Permission(1, "Write", null, null);
		mvc.perform(put(endpoint + "/" + id)
			.contentType(MediaType.APPLICATION_JSON)
			.content(mapper.writeValueAsString(permission)))
			.andExpect(status().isBadRequest());
	}
	
	/* Delete */
	
	@Test
	public void shouldReturnStatusOkWhenDeletingExistingResource() throws Exception {
		Integer id = 1;
		doNothing().when(service).delete(id);
		mvc.perform(delete(endpoint + "/" + id))
			.andExpect(status().isOk());
	}
	
	@Test
	public void shouldReturnStatusNotFoundWhenDeletingNonexistentResource() throws Exception {
		Integer id = 1;
		doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(service).delete(id);
		mvc.perform(delete(endpoint + "/" + id))
			.andExpect(status().isNotFound());
	}
	
}
