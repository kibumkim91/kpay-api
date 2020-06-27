package com.kpay.common.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kpay.common.domain.UserRole;

@Repository
public interface UserRoleMapper {
	void setUserRoleInfo(@Param("param") UserRole param);
}
