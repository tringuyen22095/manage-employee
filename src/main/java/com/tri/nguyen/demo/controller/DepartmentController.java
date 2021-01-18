package com.tri.nguyen.demo.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tri.nguyen.demo.controller.exception.CustomException;
import com.tri.nguyen.demo.models.contants.ErrorMessages;
import com.tri.nguyen.demo.models.entity.Department;
import com.tri.nguyen.demo.models.entity.User;
import com.tri.nguyen.demo.models.req.CreateDepartmentReq;
import com.tri.nguyen.demo.models.req.DepartmentAssignMemberReq;
import com.tri.nguyen.demo.models.req.UpdateDepartmentReq;
import com.tri.nguyen.demo.service.DepartmentService;
import com.tri.nguyen.demo.service.UserService;

@Validated
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/department")
@Secured({ "ROLE_CEO" })
public class DepartmentController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	private DepartmentService departmentSer;

	@Autowired
	private UserService userSer;

	@PostMapping
	public ResponseEntity<Void> createDepartment(@Valid @RequestBody CreateDepartmentReq req) {
		this.departmentSer.save(req.toModel());

		return ResponseEntity.noContent().build();
	}

	@PutMapping
	public ResponseEntity<Void> updateDepartment(@Valid @RequestBody UpdateDepartmentReq req) {
		Optional<Department> departmentOpt = this.departmentSer.findById(req.getId());
		if (!departmentOpt.isPresent()) {
			LOGGER.error(ErrorMessages.DEPARTMENT_NOT_FOUND);
			throw new CustomException(ErrorMessages.DEPARTMENT_NOT_FOUND);
		}

		Department updatedEntity = req.toModel(departmentOpt.get());
		this.departmentSer.save(updatedEntity);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDepartment(@PathVariable("id") Integer id) {
		Optional<Department> departmentOpt = this.departmentSer.findById(id);
		if (!departmentOpt.isPresent()) {
			LOGGER.error(ErrorMessages.DEPARTMENT_NOT_FOUND);
			throw new CustomException(ErrorMessages.DEPARTMENT_NOT_FOUND);
		}

		Department deletedEntity = departmentOpt.get();
		this.departmentSer.delete(deletedEntity);

		return ResponseEntity.noContent().build();
	}

	@PutMapping("/assign")
	public ResponseEntity<Void> assignManager(@Valid @RequestBody DepartmentAssignMemberReq req) {
		Optional<User> userOpt = this.userSer.findById(req.getUserId());
		if (!userOpt.isPresent()) {
			LOGGER.error(ErrorMessages.USER_NOT_FOUND);
			throw new CustomException(ErrorMessages.USER_NOT_FOUND);
		}

		Optional<Department> departmentOpt = this.departmentSer.findById(req.getDepartmentId());
		if (!departmentOpt.isPresent()) {
			LOGGER.error(ErrorMessages.DEPARTMENT_NOT_FOUND);
			throw new CustomException(ErrorMessages.DEPARTMENT_NOT_FOUND);
		}

		User user = userOpt.get();
		Department department = departmentOpt.get();
		this.departmentSer.assignManager(department, user);

		return ResponseEntity.noContent().build();
	}

}
