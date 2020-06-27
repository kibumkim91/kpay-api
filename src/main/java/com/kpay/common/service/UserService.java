package com.kpay.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kpay.common.domain.Role;
import com.kpay.common.domain.User;
import com.kpay.common.domain.UserPrincipal;
import com.kpay.common.domain.UserRole;
import com.kpay.common.mapper.RoleMapper;
import com.kpay.common.mapper.UserMapper;
import com.kpay.common.mapper.UserRoleMapper;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public User findUserByLoginId(String loginId) {
		return userMapper.findUserByLoginId(loginId);
	}

	public void saveUser(User user) {
		user.setPassword(passwordEncoder().encode(user.getPassword()));
		user.setActive(1);
		userMapper.insertUserInfo(user);
		Role role = roleMapper.getRoleInfo("ADMIN");
		UserRole userRole = new UserRole();
		userRole.setRoleId(role.getId());
		userRole.setUserId(user.getId());
		userRoleMapper.setUserRoleInfo(userRole);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.findUserByLoginId(username);
		return new UserPrincipal(user);
	}
}
