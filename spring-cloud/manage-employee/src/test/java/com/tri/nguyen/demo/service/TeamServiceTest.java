package com.tri.nguyen.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tri.nguyen.demo.models.entity.Department;
import com.tri.nguyen.demo.models.entity.Team;
import com.tri.nguyen.demo.repository.TeamRepository;

@SpringBootTest
public class TeamServiceTest {
	
	@Autowired
	private TeamService service;
	
	@MockBean
    private TeamRepository repo;

	@Test
    @DisplayName("Test save Success")
    void testSaveSuccess() {
		// Arrange
		Team entity = generateTeam();

        // Act
		service.save(entity);

        // Assert
        verify(repo, times(1)).saveAndFlush(any());
    }

	@Test
    @DisplayName("Test delete Success")
    void testDeleteSuccess() {
		// Arrange
		Team entity = generateTeam();

        // Act
		service.delete(entity);

        // Assert
        verify(repo, times(1)).saveAndFlush(any());
    }

	@Test
    @DisplayName("Test findById Success")
    void testFindByIdSuccess() {
		// Arrange
		Team entity = generateTeam();
		when(repo.findById(anyInt())).thenReturn(Optional.of(entity));

        // Act
		Optional<Team> teamOpt = service.findById(1);

        // Assert
        verify(repo, times(1)).findById(anyInt());
        assertTrue(teamOpt.isPresent());
        assertEquals(entity, teamOpt.get());
    }
	
	@Test
    @DisplayName("Test findById Success but return empty")
    void testFindByIdSuccessButReturnEmpty() {
		// Arrange
		when(repo.findById(anyInt())).thenReturn(Optional.empty());

        // Act
		Optional<Team> teamOpt = service.findById(1);

        // Assert
        verify(repo, times(1)).findById(anyInt());
        assertFalse(teamOpt.isPresent());
    }
	
	private Team generateTeam() {
		return Team.builder()
				.withId(1)
				.withName("name")
				.withShortName("short")
				.withDescription("description")
				.withDepartment(Department.builder()
						.withId(1)
						.build())
				.build();
	}
}
