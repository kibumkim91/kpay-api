package com.kpay.common.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.kpay.common.domain.Role;

@Component
@Mapper
public interface RoleMapper {
	Role getRoleInfo(@Param("role") String role);
}
