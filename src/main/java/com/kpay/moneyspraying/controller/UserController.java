package com.kpay.moneyspraying.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kpay.common.domain.User;
import com.kpay.common.service.UserService;

@RestController
@RequestMapping("/v1/banking")
public class UserController {

	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> createNewUser(@Valid @RequestBody User user, BindingResult bindingResult) {
		User userExists = userService.findUserByLoginId(user.getLoginId());
		if (userExists != null) {
			bindingResult.rejectValue("loginId", "error.loginId", "There is already a user registered with the loginId provided");
		}
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			userService.saveUser(user);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

}
