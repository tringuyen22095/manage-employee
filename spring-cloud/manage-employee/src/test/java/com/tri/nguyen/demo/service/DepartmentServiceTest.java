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
import com.tri.nguyen.demo.models.entity.User;
import com.tri.nguyen.demo.repository.DepartmentRepository;

@SpringBootTest
public class DepartmentServiceTest {
	
	@Autowired
	private DepartmentService service;
	
	@MockBean
    private DepartmentRepository repo;

	@Test
    @DisplayName("Test save Success")
    void testSaveSuccess() {
		// Arrange
		Department entity = generateDepartment();

        // Act
		service.save(entity);

        // Assert
        verify(repo, times(1)).saveAndFlush(any());
    }

	@Test
    @DisplayName("Test delete Success")
    void testDeleteSuccess() {
		// Arrange
		Department entity = generateDepartment();

        // Act
		service.delete(entity);

        // Assert
        verify(repo, times(1)).saveAndFlush(any());
    }
	
	@Test
    @DisplayName("Test assignManager Success")
    void testAssignManagerSuccess() {
		// Arrange
		Department entity = generateDepartment();

        // Act
		service.assignManager(entity, UserServiceTest.generateUser("password"));

        // Assert
        verify(repo, times(1)).saveAndFlush(any());
    }

	@Test
    @DisplayName("Test findById Success")
    void testFindByIdSuccess() {
		// Arrange
		Department entity = generateDepartment();
		when(repo.findById(anyInt())).thenReturn(Optional.of(entity));

        // Act
		Optional<Department> teamOpt = service.findById(1);

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
		Optional<Department> teamOpt = service.findById(1);

        // Assert
        verify(repo, times(1)).findById(anyInt());
        assertFalse(teamOpt.isPresent());
    }
	
	private Department generateDepartment() {
		return Department.builder()
				.withId(1)
				.withName("name")
				.withShortName("short")
				.withDescription("description")
				.withUser(User.builder()
						.withId(1)
						.withEmail("admin@mailinator.com")
						.build())
				.build();
	}
}
