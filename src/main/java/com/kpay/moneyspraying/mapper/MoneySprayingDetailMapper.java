package com.kpay.moneyspraying.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kpay.moneyspraying.domain.MoneySprayingDetail;

@Repository
public interface MoneySprayingDetailMapper {

	int insertMoneySprayingDetail(MoneySprayingDetail moneySprayingDetail);

	List<MoneySprayingDetail> selectMoneySprayingDetailList(MoneySprayingDetail moneySprayingDetail);

	MoneySprayingDetail selectMoneySprayingDetail(MoneySprayingDetail moneySprayingDetail);

	int updateMoneySprayingDetail(MoneySprayingDetail moneySprayingDetail);

	int deleteMoneySprayingDetail(MoneySprayingDetail moneySprayingDetail);

}