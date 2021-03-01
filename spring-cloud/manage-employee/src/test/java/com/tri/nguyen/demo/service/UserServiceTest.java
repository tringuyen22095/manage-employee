package com.tri.nguyen.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tri.nguyen.demo.controller.exception.CustomException;
import com.tri.nguyen.demo.models.entity.Department;
import com.tri.nguyen.demo.models.entity.Profile;
import com.tri.nguyen.demo.models.entity.Role;
import com.tri.nguyen.demo.models.entity.Team;
import com.tri.nguyen.demo.models.entity.User;
import com.tri.nguyen.demo.models.entity.common.EnumCommon.Gender;
import com.tri.nguyen.demo.models.entity.common.EnumCommon.SortType;
import com.tri.nguyen.demo.models.req.CriteriaReq;
import com.tri.nguyen.demo.models.res.SearchResults;
import com.tri.nguyen.demo.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserService service;
	
	@MockBean
    private UserRepository repo;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Test
    @DisplayName("Test save Success")
    void testSaveSuccess() {
		// Arrange
		User entity = generateUser(bCryptPasswordEncoder.encode("password_hash"));

        // Act
		service.save(entity);

        // Assert
        verify(repo, times(1)).saveAndFlush(any());
    }
	
	@Test
    @DisplayName("Test signIn Success")
    void testSignInSuccess() {
		// Arrange
		User entity = generateUser(bCryptPasswordEncoder.encode("password_hash"));

        // Act
		service.signIn(entity, "password_hash");

        // Assert
        assertTrue(true);
    }
	
	@Test
    @DisplayName("Test signIn fail - password not match")
    void testSignInFail() {
		// Arrange
		User entity = generateUser("wrong_password");

        // Act & Assert
		assertThrows(CustomException.class, () -> {
			service.signIn(entity, "pw_hash");
		});
    }

	@Test
    @DisplayName("Test findByEmail Success")
    void testFindByEmailSuccess() {
		// Arrange
		User entity = generateUser("");
		when(repo.findByEmail(anyString())).thenReturn(Optional.of(entity));

        // Act
		Optional<User> userOpt = service.findByEmail(entity.getEmail());

        // Assert
        verify(repo, times(1)).findByEmail(any());
        assertTrue(userOpt.isPresent());
        assertEquals(entity, userOpt.get());
    }

	@Test
    @DisplayName("Test findById Success")
    void testFindByIdSuccess() {
		// Arrange
		User entity = generateUser("");
		when(repo.findById(anyInt())).thenReturn(Optional.of(entity));

        // Act
		Optional<User> userOpt = service.findById(1);

        // Assert
        verify(repo, times(1)).findById(any());
        assertTrue(userOpt.isPresent());
        assertEquals(userOpt.get(), entity);
    }
	
	@Test
    @DisplayName("Test assignMember Success")
    void testAssignMemberSuccess() {
		// Arrange
		User entity = generateUser("");

        // Act
		service.assignMember(Team.builder().withId(1).build(), entity);

        // Assert
        verify(repo, times(1)).saveAndFlush(any());
    }
	
	@Test
    @DisplayName("Test searchMember Success")
    void testSearchMemberSuccess() {
		// Arrange
		CriteriaReq req = new CriteriaReq();
		req.setColumnName(Arrays.asList("fullName", "email"));
		req.setSortType(Arrays.asList(SortType.DESC));
		req.setKeyword("name");
		List<User> users = Arrays.asList(
				generateUser(""),
				generateUserWhichEmailMatchCriteria(),
				generateUserWhichNotMatchCriteria());
		when(this.repo.findAll()).thenReturn(users);

        // Act
		SearchResults res = service.searchMember(req);

        // Assert
        verify(repo, times(1)).findAll();
        assertEquals(2, res.getRecords().size());
    }
	
	@Test
    @DisplayName("Test searchMember Success with empty keyword & unknown column sort")
    void testSearchMemberSuccessWithEmptyKeyword() {
		// Arrange
		CriteriaReq req = new CriteriaReq();
		req.setColumnName(Arrays.asList("column"));
		req.setSortType(Arrays.asList(SortType.DESC));
		List<User> users = Arrays.asList(
				generateUser(""),
				generateUserWhichEmailMatchCriteria(),
				generateUserWhichNotMatchCriteria());
		when(this.repo.findAll()).thenReturn(users);

        // Act
		SearchResults res = service.searchMember(req);

        // Assert
        verify(repo, times(1)).findAll();
        assertEquals(users.size(), res.getRecords().size());
    }
	
	@Test
    @DisplayName("Test unassignMember Success")
    void testUnassignMemberSuccess() {
		// Arrange
		User entity = generateUser("");

        // Act
		service.unassignMember(Team.builder().withId(1).build(), entity);

        // Assert
        verify(repo, times(1)).saveAndFlush(any());
    }
	
	public static User generateUser(String password) {
		User u = User.builder()
					.withEmail("example@gmail.com")
					.withId(1)
					.withPassword(password)
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
		Role role = new Role(1);
		role.setName("CEO");
		u.setRoles(Collections.singleton(role));
		return u;
	}
	
	private User generateUserWhichEmailMatchCriteria() {
		User u = User.builder()
					.withEmail("example@gmail.com")
					.withId(1)
					.withPassword(bCryptPasswordEncoder.encode("password_hash"))
					.withRoles(Collections.singleton(new Role(1)))
					.withProfile(Profile.builder()
							.withFirstName("first")
							.withLastName("last")
							.withDateOfBirth(new Date())
							.withGender(Gender.MALE)
							.withId(1)
							.withPhoneNumber("0123456789")
							.build())
					.build();
		u.setDepartments(new HashSet<Department>());
		u.setTeams(new HashSet<Team>());
		return u;
	}
	
	private User generateUserWhichNotMatchCriteria() {
		User u = User.builder()
					.withEmail("name@gmail.com")
					.withId(1)
					.withPassword(bCryptPasswordEncoder.encode("password_hash"))
					.withRoles(Collections.singleton(new Role(1)))
					.withProfile(Profile.builder()
							.withFirstName("first")
							.withLastName("last")
							.withDateOfBirth(new Date())
							.withGender(Gender.MALE)
							.withId(1)
							.withPhoneNumber("0123456789")
							.build())
					.build();
		u.setDepartments(new HashSet<Department>());
		u.setTeams(new HashSet<Team>());
		return u;
	}
}
