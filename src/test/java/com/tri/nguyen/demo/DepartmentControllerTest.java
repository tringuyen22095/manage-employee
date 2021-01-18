package com.tri.nguyen.demo;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tri.nguyen.demo.config.JwtTokenProvider;
import com.tri.nguyen.demo.models.entity.Department;
import com.tri.nguyen.demo.models.req.CreateDepartmentReq;
import com.tri.nguyen.demo.models.req.DepartmentAssignMemberReq;
import com.tri.nguyen.demo.models.req.UpdateDepartmentReq;
import com.tri.nguyen.demo.models.res.JwtRes;
import com.tri.nguyen.demo.service.DepartmentService;
import com.tri.nguyen.demo.service.UserService;
import com.tri.nguyen.demo.service.UserServiceTest;

@SpringBootTest
@AutoConfigureMockMvc
public class DepartmentControllerTest {

	@MockBean
	private UserService userSer;
	
	@MockBean
	private DepartmentService departmentSer;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
    private MockMvc mockMvc;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
    @DisplayName("POST /api/department success")
    void testCreateDepartmentSuccess() throws Exception {
        // Act & Assert
		mockMvc.perform(post("/api/department")
				.header("Authorization", generateToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateCreateDepartmentReq()))
				.andExpect(status().isNoContent());
	}
	
	@Test
    @DisplayName("POST /api/department fail because unauthorized")
    void testCreateDepartmentFail() throws Exception {
        // Act & Assert
		mockMvc.perform(post("/api/department")
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateCreateDepartmentReq()))
				.andExpect(status().isUnauthorized());
	}
	
	@Test
    @DisplayName("POST /api/department fail because request invalid")
    void testCreateDepartmentFail2() throws Exception {
        // Act & Assert
		mockMvc.perform(post("/api/department")
				.header("Authorization", generateToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
    @DisplayName("PUT /api/department fail because department not found")
    void testUpdateDepartmentFail() throws Exception {
		// Arrange
		when(departmentSer.findById(anyInt())).thenReturn(Optional.empty());
        // Act & Assert
		mockMvc.perform(put("/api/department")
				.header("Authorization", generateToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateUpdateDepartmentReq()))
				.andExpect(status().isBadRequest());
	}
	
	@Test
    @DisplayName("PUT /api/department success")
    void testUpdateDepartmentSuccess() throws Exception {
		// Arrange
		when(departmentSer.findById(anyInt())).thenReturn(Optional.of(generateDepartment()));
        // Act & Assert
		mockMvc.perform(put("/api/department")
				.header("Authorization", generateToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateUpdateDepartmentReq()))
				.andExpect(status().isNoContent());
	}
	
	@Test
    @DisplayName("DELETE /api/department fail because department not found")
    void testDeleteDepartmentFail() throws Exception {
		// Arrange
		when(departmentSer.findById(anyInt())).thenReturn(Optional.empty());
        // Act & Assert
		mockMvc.perform(delete("/api/department/{id}", 1)
				.header("Authorization", generateToken()))
				.andExpect(status().isBadRequest());
	}
	
	@Test
    @DisplayName("DELETE /api/department success")
    void testDeleteDepartmentSuccess() throws Exception {
		// Arrange
		when(departmentSer.findById(anyInt())).thenReturn(Optional.of(generateDepartment()));
        // Act & Assert
		mockMvc.perform(delete("/api/department/{id}", 1)
				.header("Authorization", generateToken()))
				.andExpect(status().isNoContent());
	}
	
	@Test
    @DisplayName("PUT /api/department/assign fail because department not found")
    void testAssignDepartmentFail() throws Exception {
		// Arrange
		when(userSer.findById(anyInt())).thenReturn(Optional.of(UserServiceTest.generateUser("pwd")));
		when(departmentSer.findById(anyInt())).thenReturn(Optional.empty());
        // Act & Assert
		mockMvc.perform(put("/api/department/assign")
				.header("Authorization", generateToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateDepartmentAssignMemberReq()))
				.andExpect(status().isBadRequest());
	}
	
	@Test
    @DisplayName("PUT /api/department/assign fail because user not found")
    void testAssignDepartmentFail2() throws Exception {
		// Arrange
		when(userSer.findById(anyInt())).thenReturn(Optional.empty());
        // Act & Assert
		mockMvc.perform(put("/api/department/assign")
				.header("Authorization", generateToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateDepartmentAssignMemberReq()))
				.andExpect(status().isBadRequest());
	}
	
	@Test
    @DisplayName("PUT /api/department/assign success")
    void testAssignDepartmentSuccess() throws Exception {
		// Arrange
		when(departmentSer.findById(anyInt())).thenReturn(Optional.of(generateDepartment()));
		when(userSer.findById(anyInt())).thenReturn(Optional.of(UserServiceTest.generateUser("pwd")));
        // Act & Assert
		mockMvc.perform(put("/api/department/assign")
				.header("Authorization", generateToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateDepartmentAssignMemberReq()))
				.andExpect(status().isNoContent());
	}
	
	private String generateToken() {
		JwtRes res = jwtTokenProvider.generateToken(UserServiceTest.generateUser("123"));
		return String.format("Bearer %s", res.getToken());
	}
	
	private String generateCreateDepartmentReq() {
		try {
			CreateDepartmentReq req = new CreateDepartmentReq();
			req.setName("name");
			return mapper.writeValueAsString(req);
		} catch (JsonProcessingException e) {
			return "{}";
		}
	}
	
	private String generateUpdateDepartmentReq() {
		try {
			UpdateDepartmentReq req = new UpdateDepartmentReq();
			req.setName("name");
			req.setId(1);
			return mapper.writeValueAsString(req);
		} catch (JsonProcessingException e) {
			return "{}";
		}
	}
	
	private String generateDepartmentAssignMemberReq() {
		try {
			DepartmentAssignMemberReq req = new DepartmentAssignMemberReq();
			req.setDepartmentId(1);
			req.setUserId(1);
			return mapper.writeValueAsString(req);
		} catch (JsonProcessingException e) {
			return "{}";
		}
	}
	
	private Department generateDepartment() {
		return Department.builder()
				.withId(1)
				.withName("name")
				.withDescription("description")
				.withShortName("short name")
				.build();
	}

}
