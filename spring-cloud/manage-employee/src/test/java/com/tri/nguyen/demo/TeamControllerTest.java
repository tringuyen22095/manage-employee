package com.tri.nguyen.demo;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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
import com.tri.nguyen.demo.models.entity.Team;
import com.tri.nguyen.demo.models.req.CreateTeamReq;
import com.tri.nguyen.demo.models.req.CriteriaReq;
import com.tri.nguyen.demo.models.req.TeamAssignMemberReq;
import com.tri.nguyen.demo.models.req.UpdateTeamReq;
import com.tri.nguyen.demo.models.res.JwtRes;
import com.tri.nguyen.demo.models.res.SearchResults;
import com.tri.nguyen.demo.service.TeamService;
import com.tri.nguyen.demo.service.UserService;
import com.tri.nguyen.demo.service.UserServiceTest;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamControllerTest {

	@MockBean
	private UserService userSer;
	
	@MockBean
	private TeamService teamSer;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
    private MockMvc mockMvc;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
    @DisplayName("GET /api/team/members success")
    void testSearchMemberSuccess() throws Exception {
		// Arrange
		when(userSer.searchMember(any(CriteriaReq.class)))
					.thenReturn(SearchResults.builder()
											.withPageTotal(1)
											.withRecords(new ArrayList<Object>())
											.build());
        // Act & Assert
		mockMvc.perform(get("/api/team/members")
				.header("Authorization", generateToken()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.pageTotal", is(1)));
	}
	
	@Test
    @DisplayName("POST /api/team success")
    void testCreateTeamSuccess() throws Exception {
        // Act & Assert
		mockMvc.perform(post("/api/team")
				.header("Authorization", generateToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateCreateTeamReq()))
				.andExpect(status().isNoContent());
	}
	
	@Test
    @DisplayName("POST /api/team fail because unauthorized")
    void testCreateTeamFail() throws Exception {
        // Act & Assert
		mockMvc.perform(post("/api/team")
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateCreateTeamReq()))
				.andExpect(status().isUnauthorized());
	}
	
	@Test
    @DisplayName("POST /api/team fail because request invalid")
    void testCreateTeamFail2() throws Exception {
        // Act & Assert
		mockMvc.perform(post("/api/team")
				.header("Authorization", generateToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
    @DisplayName("PUT /api/team fail because team not found")
    void testUpdateTeamFail() throws Exception {
		// Arrange
		when(teamSer.findById(anyInt())).thenReturn(Optional.empty());
        // Act & Assert
		mockMvc.perform(put("/api/team")
				.header("Authorization", generateToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateUpdateTeamReq()))
				.andExpect(status().isBadRequest());
	}
	
	@Test
    @DisplayName("PUT /api/team success")
    void testUpdateTeamSuccess() throws Exception {
		// Arrange
		when(teamSer.findById(anyInt())).thenReturn(Optional.of(generateTeam()));
        // Act & Assert
		mockMvc.perform(put("/api/team")
				.header("Authorization", generateToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateUpdateTeamReq()))
				.andExpect(status().isNoContent());
	}
	
	@Test
    @DisplayName("DELETE /api/team fail because team not found")
    void testDeleteDepartmentFail() throws Exception {
		// Arrange
		when(teamSer.findById(anyInt())).thenReturn(Optional.empty());
        // Act & Assert
		mockMvc.perform(delete("/api/team/{id}", 1)
				.header("Authorization", generateToken()))
				.andExpect(status().isBadRequest());
	}
	
	@Test
    @DisplayName("DELETE /api/team success")
    void testDeleteDepartmentSuccess() throws Exception {
		// Arrange
		when(teamSer.findById(anyInt())).thenReturn(Optional.of(generateTeam()));
        // Act & Assert
		mockMvc.perform(delete("/api/team/{id}", 1)
				.header("Authorization", generateToken()))
				.andExpect(status().isNoContent());
	}
	
	@Test
    @DisplayName("PUT /api/team/assign fail because team not found")
    void testAssignTeamFail() throws Exception {
		// Arrange
		when(userSer.findById(anyInt())).thenReturn(Optional.of(UserServiceTest.generateUser("pwd")));
		when(teamSer.findById(anyInt())).thenReturn(Optional.empty());
        // Act & Assert
		mockMvc.perform(put("/api/team/assign")
				.header("Authorization", generateToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateTeamAssignMemberReq()))
				.andExpect(status().isBadRequest());
	}
	
	@Test
    @DisplayName("PUT /api/team/assign fail because user not found")
    void testAssignTeamFail2() throws Exception {
		// Arrange
		when(userSer.findById(anyInt())).thenReturn(Optional.empty());
        // Act & Assert
		mockMvc.perform(put("/api/team/assign")
				.header("Authorization", generateToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateTeamAssignMemberReq()))
				.andExpect(status().isBadRequest());
	}
	
	@Test
    @DisplayName("PUT /api/team/assign success")
    void testAssignTeamSuccess() throws Exception {
		// Arrange
		when(teamSer.findById(anyInt())).thenReturn(Optional.of(generateTeam()));
		when(userSer.findById(anyInt())).thenReturn(Optional.of(UserServiceTest.generateUser("pwd")));
        // Act & Assert
		mockMvc.perform(put("/api/team/assign")
				.header("Authorization", generateToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateTeamAssignMemberReq()))
				.andExpect(status().isNoContent());
	}
	
	@Test
    @DisplayName("PUT /api/team/assign fail because department not found")
    void testUnassignTeamFail() throws Exception {
		// Arrange
		when(userSer.findById(anyInt())).thenReturn(Optional.of(UserServiceTest.generateUser("pwd")));
		when(teamSer.findById(anyInt())).thenReturn(Optional.empty());
        // Act & Assert
		mockMvc.perform(put("/api/team/unassign")
				.header("Authorization", generateToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateTeamAssignMemberReq()))
				.andExpect(status().isBadRequest());
	}
	
	@Test
    @DisplayName("PUT /api/team/assign fail because user not found")
    void testUnassignTeamFail2() throws Exception {
		// Arrange
		when(userSer.findById(anyInt())).thenReturn(Optional.empty());
        // Act & Assert
		mockMvc.perform(put("/api/team/unassign")
				.header("Authorization", generateToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateTeamAssignMemberReq()))
				.andExpect(status().isBadRequest());
	}
	
	@Test
    @DisplayName("PUT /api/team/unassign success")
    void testUnassignTeamtSuccess() throws Exception {
		// Arrange
		when(teamSer.findById(anyInt())).thenReturn(Optional.of(generateTeam()));
		when(userSer.findById(anyInt())).thenReturn(Optional.of(UserServiceTest.generateUser("pwd")));
        // Act & Assert
		mockMvc.perform(put("/api/team/unassign")
				.header("Authorization", generateToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(generateTeamAssignMemberReq()))
				.andExpect(status().isNoContent());
	}
	
	private String generateToken() {
		JwtRes res = jwtTokenProvider.generateToken(UserServiceTest.generateUser("123"));
		return String.format("Bearer %s", res.getToken());
	}
	
	private String generateCreateTeamReq() {
		try {
			CreateTeamReq req = new CreateTeamReq();
			req.setName("name");
			req.setDepartmentId(1);
			return mapper.writeValueAsString(req);
		} catch (JsonProcessingException e) {
			return "{}";
		}
	}
	
	private String generateUpdateTeamReq() {
		try {
			UpdateTeamReq req = new UpdateTeamReq();
			req.setName("name");
			req.setId(1);
			req.setDepartmentId(1);
			return mapper.writeValueAsString(req);
		} catch (JsonProcessingException e) {
			return "{}";
		}
	}
	
	private String generateTeamAssignMemberReq() {
		try {
			TeamAssignMemberReq req = new TeamAssignMemberReq();
			req.setTeamId(1);
			req.setUserId(1);
			return mapper.writeValueAsString(req);
		} catch (JsonProcessingException e) {
			return "{}";
		}
	}
	
	private Team generateTeam() {
		return Team.builder()
				.withId(1)
				.withName("name")
				.withDescription("description")
				.withShortName("short name")
				.build();
	}

}
