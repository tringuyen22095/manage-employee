package com.tri.nguyen.demo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tri.nguyen.demo.models.entity.Department;
import com.tri.nguyen.demo.models.entity.User;
import com.tri.nguyen.demo.models.entity.common.EnumCommon.ObjectStatus;
import com.tri.nguyen.demo.repository.DepartmentRepository;
import com.tri.nguyen.demo.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepo;

	@Override
	public void save(Department entity) {
		this.departmentRepo.saveAndFlush(entity);
	}

	@Override
	public void delete(Department entity) {
		entity.setObjectStatus(ObjectStatus.DELETED);

		this.departmentRepo.saveAndFlush(entity);
	}

	@Override
	public Optional<Department> findById(Integer id) {
		return this.departmentRepo.findById(id);
	}

	@Override
	public void assignManager(Department department, User user) {
		department.setUser(user);
		
		this.save(department);
	}

}
