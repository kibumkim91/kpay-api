package com.kpay.moneyspraying.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.kpay.common.Constants.RetrieveUnit;
import com.kpay.moneyspraying.domain.MoneySpraying;

@RunWith(SpringRunner.class)
@MybatisTest
public class MoneySprayingMapperTest {

	@Autowired
	private MoneySprayingMapper moneySprayingMapper;

	@Before
	public void setup() {
		testInsertMoneySpraying();
	}

	/**
	 * 뿌리기 입력이 올바르게 동작하는지 테스트한다.
	 */
	@Test
	public void testInsertMoneySpraying() {
		MoneySpraying moneySpraying = new MoneySpraying();

		moneySpraying.setRoomId("R02");
		moneySpraying.setUserId(373707);
		moneySpraying.setTotalAmount(BigDecimal.valueOf(10000));
		moneySpraying.setTotalCount(5);
		moneySpraying.setToken(moneySprayingMapper.selectToken());

		moneySprayingMapper.insertMoneySpraying(moneySpraying);
		assertThat(moneySpraying.getSprayingId()).isGreaterThan(0);
	}

	/**
	 * 토큰을 올바르게 생성하는지 테스트한다.
	 */
	@Test
	public void testSelectToken() {
		String token = moneySprayingMapper.selectToken();
		assertThat(token).isNotBlank();
		assertThat(token.length()).isEqualTo(3);
	}

	/**
	 * 뿌리기 목록 조회 테스트
	 */
	@Test
	public void testSelectMoneySprayingList() {
		MoneySpraying moneySpraying = new MoneySpraying();
		List<MoneySpraying> moneySprayingList = moneySprayingMapper.selectMoneySprayingList(moneySpraying);

		assertThat(moneySprayingList.size()).isGreaterThan(0);
	}

	/**
	 * 뿌리기 단건 조회 테스트
	 */
	@Test
	public void testSelectMoneySpraying() {
		MoneySpraying moneySpraying = new MoneySpraying();
		moneySpraying.setRoomId("R02");
		moneySpraying.setUserId(373707);
		moneySpraying.setRetrieveInterval(7);
		moneySpraying.setRetrieveUnit(RetrieveUnit.DAY);

		moneySpraying = moneySprayingMapper.selectMoneySpraying(moneySpraying);

		assertThat(moneySpraying).isNotNull();
	}

	/**
	 * 뿌리기 갱신 테스트
	 */
	@Test
	public void testUpdateMoneySpraying() {
		MoneySpraying moneySpraying = new MoneySpraying();
		moneySpraying.setRoomId("R02");
		moneySpraying.setUserId(373707);
		moneySpraying.setRetrieveInterval(7);
		moneySpraying.setRetrieveUnit(RetrieveUnit.DAY);

		moneySpraying = moneySprayingMapper.selectMoneySpraying(moneySpraying);

		moneySpraying.setSprayingId(moneySpraying.getSprayingId());
		moneySpraying.setRemitAmount(moneySpraying.getTotalAmount());

		int affectedRows = moneySprayingMapper.updateMoneySpraying(moneySpraying);

		assertThat(affectedRows).isGreaterThan(0);
	}

	/**
	 * 뿌리기 삭제 테스트
	 */
	@Test
	public void testDeleteMoneySpraying() {
		MoneySpraying moneySpraying = new MoneySpraying();
		moneySpraying.setRoomId("R02");
		moneySpraying.setUserId(373707);
		moneySpraying.setRetrieveInterval(7);
		moneySpraying.setRetrieveUnit(RetrieveUnit.DAY);

		moneySpraying = moneySprayingMapper.selectMoneySpraying(moneySpraying);

		moneySpraying.setSprayingId(moneySpraying.getSprayingId());
		int affectedRows = moneySprayingMapper.deleteMoneySpraying(moneySpraying);

		assertThat(affectedRows).isGreaterThan(0);
	}

}
