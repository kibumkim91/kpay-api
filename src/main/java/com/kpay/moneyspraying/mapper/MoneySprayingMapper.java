package com.kpay.moneyspraying.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kpay.moneyspraying.domain.MoneySpraying;

@Repository
public interface MoneySprayingMapper {

	int insertMoneySpraying(MoneySpraying moneySpraying);

	String selectToken();

	List<MoneySpraying> selectMoneySprayingList(MoneySpraying moneySpraying);

	MoneySpraying selectMoneySpraying(MoneySpraying moneySpraying);

	int updateMoneySpraying(MoneySpraying moneySpraying);

	int deleteMoneySpraying(MoneySpraying moneySpraying);

}