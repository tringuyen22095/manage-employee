package com.tri.nguyen.demo.service;

import java.util.Optional;

import com.tri.nguyen.demo.models.entity.Department;
import com.tri.nguyen.demo.models.entity.User;

public interface DepartmentService {

	void save(Department entity);

	void delete(Department entity);

	Optional<Department> findById(Integer id);
	
	void assignManager(Department department, User user);

}
