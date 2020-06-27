package com.kpay.moneyspraying.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kpay.common.exception.GeneralException;
import com.kpay.moneyspraying.domain.MoneySpraying;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MoneySprayServiceTest {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(MoneySprayServiceTest.class);

	@Autowired
	MoneySprayService moneySprayService;

	/**
	 * 뿌리기 동작 테스트
	 */
	@Test
	public void testSprayMoney() {
		
		long userId = 373707;
		String roomId = "R01";
		BigDecimal totalAmount = BigDecimal.valueOf(10000);
		int totalCount = 4;

		MoneySpraying moneySpraying = new MoneySpraying();
		moneySpraying.setUserId(userId);
		moneySpraying.setRoomId(roomId);
		moneySpraying.setTotalAmount(totalAmount);
		moneySpraying.setTotalCount(totalCount);
		
		String token = moneySprayService.sprayMoney(moneySpraying);
		assertThat(token).isNotBlank();
		assertThat(token.length()).isEqualTo(3);
	}

	/**
	 * 받기 동작 테스트
	 */
	@Test
	public void testTakeMoney() {
		
		long senderId = 373707;
		long receiverId = 99999;
		String roomId = "R01";
		int totalCount = 4;
		BigDecimal totalAmount = BigDecimal.valueOf(10000);

		MoneySpraying moneySpraying = new MoneySpraying();
		moneySpraying.setUserId(senderId);
		moneySpraying.setRoomId(roomId);
		moneySpraying.setTotalAmount(totalAmount);
		moneySpraying.setTotalCount(totalCount);
		
		String token = moneySprayService.sprayMoney(moneySpraying);
		
		BigDecimal ammount = moneySprayService.takeMoney(receiverId, roomId, token);
		assertThat(ammount.intValue()).isGreaterThan(0);
	}
	
	/**
	 * 받기 동작 예외 테스트
	 * 자신이 뿌리기한 건은 자신이 받을 수 없음
	 */
	@Test(expected = GeneralException.class)
	public void test_자신이_뿌기리한_건은_받을수_없음() {
		
		long senderId = 373707;
		String roomId = "R01";
		int totalCount = 4;
		BigDecimal totalAmount = BigDecimal.valueOf(10000);

		MoneySpraying moneySpraying = new MoneySpraying();
		moneySpraying.setUserId(senderId);
		moneySpraying.setRoomId(roomId);
		moneySpraying.setTotalAmount(totalAmount);
		moneySpraying.setTotalCount(totalCount);
		
		String token = moneySprayService.sprayMoney(moneySpraying);
		
		// 받는 사용자 아이디를 뿌린 사용자 아이디(senderId)로 설정  
		moneySprayService.takeMoney(senderId, roomId, token);
	}
	
	/**
	 * 받기 동작 예외 테스트
	 * 동일한 대화방에 속한 사용자만이 받을 수 있음
	 */
	@Test(expected = GeneralException.class)
	public void test_동일한_대화방에_속한_사용자만이_받을_수_있음() {
		
		long senderId = 373707;
		String roomId = "R01";
		int totalCount = 4;
		BigDecimal totalAmount = BigDecimal.valueOf(10000);

		MoneySpraying moneySpraying = new MoneySpraying();
		moneySpraying.setUserId(senderId);
		moneySpraying.setRoomId(roomId);
		moneySpraying.setTotalAmount(totalAmount);
		moneySpraying.setTotalCount(totalCount);
		
		String token = moneySprayService.sprayMoney(moneySpraying);
		
		// 속하지 않은 대화방 아이디 설정  
		String anotherRoomId = "R99";
		moneySprayService.takeMoney(senderId, anotherRoomId, token);
	}

	/**
	 * 받기 동작 예외 테스트
	 * 뿌리기 당 한 사용자는 한번만 받을 수 있음
	 */
	@Test(expected = GeneralException.class)
	public void test_뿌리기_당_한_사용자는_한번만_받을_수_있음() {
		
		long senderId = 373707;
		long receiverId = 99999;
		String roomId = "R01";
		int totalCount = 4;
		BigDecimal totalAmount = BigDecimal.valueOf(10000);

		MoneySpraying moneySpraying = new MoneySpraying();
		moneySpraying.setUserId(senderId);
		moneySpraying.setRoomId(roomId);
		moneySpraying.setTotalAmount(totalAmount);
		moneySpraying.setTotalCount(totalCount);
		
		String token = moneySprayService.sprayMoney(moneySpraying);
		
		// 뿌린 돈은 최초 1회만 받을 수 있다
		moneySprayService.takeMoney(receiverId, roomId, token);
		
		// 두 번 이상 받을 경우에는 오류 발생 
		moneySprayService.takeMoney(receiverId, roomId, token);
	}
	
	/**
	 * 뿌리기 건 조회 테스트
	 */
	@Test
	public void testDescribeMoneySpraying() {
		long senderId = 373707;
		String roomId = "R01";
		int totalCount = 4;
		BigDecimal totalAmount = BigDecimal.valueOf(10000);

		MoneySpraying moneySpraying = new MoneySpraying();
		moneySpraying.setUserId(senderId);
		moneySpraying.setRoomId(roomId);
		moneySpraying.setTotalAmount(totalAmount);
		moneySpraying.setTotalCount(totalCount);
		
		String token = moneySprayService.sprayMoney(moneySpraying);
		assertThat(moneySprayService.describeMoneySpraying(senderId, roomId, token)).isNotNull();
	}

}
