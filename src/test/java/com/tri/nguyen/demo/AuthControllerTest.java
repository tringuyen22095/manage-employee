package com.tri.nguyen.demo;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
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
import com.tri.nguyen.demo.models.entity.Profile;
import com.tri.nguyen.demo.models.entity.Role;
import com.tri.nguyen.demo.models.entity.Team;
import com.tri.nguyen.demo.models.entity.User;
import com.tri.nguyen.demo.models.entity.common.EnumCommon.Gender;
import com.tri.nguyen.demo.models.entity.common.EnumCommon.RoleId;
import com.tri.nguyen.demo.models.entity.common.EnumCommon.UserStatus;
import com.tri.nguyen.demo.models.req.SignInReq;
import com.tri.nguyen.demo.models.req.SignUpReq;
import com.tri.nguyen.demo.models.res.JwtRes;
import com.tri.nguyen.demo.service.UserService;
import com.tri.nguyen.demo.service.UserServiceTest;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

	@MockBean
	private UserService userSer;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
    private MockMvc mockMvc;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
    @DisplayName("POST /api/auth/sign-in fail because email not found")
    void testSignInFail() throws Exception {
		// Arrange
		when(userSer.findByEmail(anyString())).thenReturn(Optional.empty());
        // Act & Assert
		mockMvc.perform(post("/api/auth/sign-in")
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateSignInReq()))
				.andExpect(status().isBadRequest());
	}
	
	@Test
    @DisplayName("POST /api/auth/sign-in fail because user is terminted")
    void testSignInFail2() throws Exception {
		// Arrange
		when(userSer.findByEmail(anyString())).thenReturn(Optional.of(generateTerminatedUser()));
        // Act & Assert
		mockMvc.perform(post("/api/auth/sign-in")
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateSignInReq()))
				.andExpect(status().isBadRequest());
	}
	
	@Test
    @DisplayName("POST /api/auth/sign-in success")
    void testSignInSuccess() throws Exception {
		// Arrange
		when(userSer.findByEmail(anyString())).thenReturn(Optional.of(UserServiceTest.generateUser("123")));
        // Act & Assert
		mockMvc.perform(post("/api/auth/sign-in")
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateSignInReq()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.token").isNotEmpty());
	}
	
	@Test
    @DisplayName("POST /api/auth/sign-up fail becaust unauthoried")
    void testSigUpFail() throws Exception {
		// Arrange
		when(userSer.findByEmail(anyString())).thenReturn(Optional.of(UserServiceTest.generateUser("123")));
        // Act & Assert
		mockMvc.perform(post("/api/auth/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateSignUpReq()))
				.andExpect(status().isUnauthorized());
	}
	
	@Test
    @DisplayName("POST /api/auth/sign-up fail because invalid body")
    void testSigUpFail2() throws Exception {
		// Arrange
		when(userSer.findByEmail(anyString())).thenReturn(Optional.of(UserServiceTest.generateUser("123")));
        // Act & Assert
		mockMvc.perform(post("/api/auth/sign-up")
				.header("Authorization", generateToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").isNotEmpty());
	}
	
	@Test
    @DisplayName("POST /api/auth/sign-up fail because email exist")
    void testSigUpFail3() throws Exception {
		// Arrange
		when(userSer.findByEmail(anyString())).thenReturn(Optional.of(UserServiceTest.generateUser("123")));
        // Act & Assert
		mockMvc.perform(post("/api/auth/sign-up")
				.header("Authorization", generateToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateSignUpReq()))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").isNotEmpty());
	}
	
	@Test
    @DisplayName("POST /api/auth/sign-up success")
    void testSigUpSuccess() throws Exception {
		// Arrange
		when(userSer.findByEmail(anyString())).thenReturn(Optional.empty());
        // Act & Assert
		mockMvc.perform(post("/api/auth/sign-up")
				.header("Authorization", generateToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateSignUpReq()))
				.andExpect(status().isNoContent());
	}
	
	private String generateToken() {
		JwtRes res = jwtTokenProvider.generateToken(UserServiceTest.generateUser("123"));
		return String.format("Bearer %s", res.getToken());
	}
	
	private String generateSignInReq() {
		try {
			SignInReq req = new SignInReq();
			req.setEmail("example@gmail.com");
			req.setPassword("password");
			return mapper.writeValueAsString(req);
		} catch (JsonProcessingException e) {
			return "{}";
		}
	}
	
	private String generateSignUpReq() {
		try {
			SignUpReq req = new SignUpReq();
			req.setEmail("example@gmail.com");
			req.setPassword("password");
			req.setDateOfBirth(new Date());
			req.setFirstName("first name");
			req.setLastName("last name");
			req.setGender(Gender.MALE);
			req.setPhoneNumber("123456789");
			req.setRole(RoleId.MEMBER);
			return mapper.writeValueAsString(req);
		} catch (JsonProcessingException e) {
			return "{}";
		}
	}
	
	private User generateTerminatedUser() {
		User u = User.builder()
					.withEmail("example@gmail.com")
					.withId(1)
					.withPassword("text")
					.withRoles(Collections.singleton(new Role(1)))
					.withProfile(Profile.builder()
							.withFirstName("first_name")
							.withLastName("last_name")
							.withDateOfBirth(new Date())
							.withGender(Gender.MALE)
							.withId(1)
							.withPhoneNumber("0123456789")
							.build())
					.build();
		u.setDepartments(new HashSet<Department>());
		u.setTeams(new HashSet<Team>());
		u.setStatus(UserStatus.TERMINATED);
		return u;
	}
}
