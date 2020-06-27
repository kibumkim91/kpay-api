package com.kpay.common.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kpay.common.domain.User;

@Repository
public interface UserMapper {
	User findUserByLoginId(@Param("loginId") String loginId);

	int insertUserInfo(@Param("param") User param);
}
