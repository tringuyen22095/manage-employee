package com.tri.nguyen.demo.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tri.nguyen.demo.config.JwtTokenProvider;
import com.tri.nguyen.demo.controller.exception.CustomException;
import com.tri.nguyen.demo.models.contants.ErrorMessages;
import com.tri.nguyen.demo.models.entity.User;
import com.tri.nguyen.demo.models.entity.common.EnumCommon.UserStatus;
import com.tri.nguyen.demo.models.req.SignInReq;
import com.tri.nguyen.demo.models.req.SignUpReq;
import com.tri.nguyen.demo.models.res.JwtRes;
import com.tri.nguyen.demo.service.UserService;

@Validated
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private UserService userSer;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping("/sign-in")
	public ResponseEntity<JwtRes> signIn(@Valid @RequestBody SignInReq req) {
		Optional<User> userOpt = this.userSer.findByEmail(req.getEmail());
		if (!userOpt.isPresent()) {
			LOGGER.error("User not found with email [{}].", req.getEmail());
			throw new CustomException(ErrorMessages.USER_NOT_FOUND);
		}

		User user = userOpt.get();
		if (UserStatus.TERMINATED.equals(user.getStatus())) {
			LOGGER.error(ErrorMessages.USER_IS_TERMINATED);
			throw new CustomException(ErrorMessages.USER_IS_TERMINATED);
		}

		this.userSer.signIn(user, req.getPassword());

		return ResponseEntity.ok().body(this.jwtTokenProvider.generateToken(user));
	}

	@Secured({ "ROLE_CEO" })
	@PostMapping("/sign-up")
	public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpReq req) {
		Optional<User> userOpt = this.userSer.findByEmail(req.getEmail());
		if (userOpt.isPresent()) {
			LOGGER.error("Email [{}] has been used.", req.getEmail());
			throw new CustomException(ErrorMessages.USER_ALREADY_EXISTS);
		}

		User user = req.toModel(this.bCryptPasswordEncoder);
		this.userSer.save(user);

		return ResponseEntity.noContent().build();
	}

}
