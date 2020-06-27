package com.kpay.moneyspraying.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kpay.common.Constants.ResponseCode;
import com.kpay.common.Constants.RetrieveUnit;
import com.kpay.common.exception.GeneralException;
import com.kpay.moneyspraying.domain.MoneySpraying;
import com.kpay.moneyspraying.domain.MoneySprayingDetail;
import com.kpay.moneyspraying.mapper.MoneySprayingDetailMapper;
import com.kpay.moneyspraying.mapper.MoneySprayingMapper;

/**
 * 뿌리기 서비스 
 */
@Service
public class MoneySprayService {
	
	@Autowired
	private MoneySprayingMapper moneySprayingMapper;
	
	@Autowired
	private MoneySprayingDetailMapper moneySprayingDetailMapper;
	
	/**
	 * 뿌릴 금액을 뿌릴 인원 수에 맞게 뿌립니다.
	 * 
	 * @param moneySpraying 뿌리기 정보
	 * @return 뿌리기 요청건에 대한 고유 token
	 */
	@Transactional
	public String sprayMoney(MoneySpraying moneySpraying) {
		moneySpraying.setToken(moneySprayingMapper.selectToken());
		moneySprayingMapper.insertMoneySpraying(moneySpraying);
		
		MoneySprayingDetail moneySprayingDetail = new MoneySprayingDetail();
		moneySprayingDetail.setSprayingId(moneySpraying.getSprayingId());
		BigDecimal[] amounts = getAmountPerPerson(moneySpraying.getTotalAmount(), moneySpraying.getTotalCount());
		
		for (BigDecimal amount : amounts) {
			moneySprayingDetail.setAmount(amount);
			moneySprayingDetailMapper.insertMoneySprayingDetail(moneySprayingDetail);	
		}
		
		return moneySpraying.getToken();
	}

	/**
	 * 조건에 해당하는 뿌리기 건에서 뿌려진 돈을 받습니다.
	 * 
	 * @param userId 받는 사용자 아이디
	 * @param roomId 대화방 아이디
	 * @param token 뿌리기 요청건에 대한 고유 token
	 * @return 받은 금액
	 */
	@Transactional
	public BigDecimal takeMoney(long userId, String roomId, String token) {
		
		// 1. roomId와 token으로 MONEY_SPRAYING 테이블을 조회하여 spraying_id 값을 얻어온다
		MoneySpraying moneySpraying = new MoneySpraying();
		moneySpraying.setRoomId(roomId);
		moneySpraying.setToken(token);
		moneySpraying.setRetrieveInterval(10);
		moneySpraying.setRetrieveUnit(RetrieveUnit.MINUTE);
		moneySpraying = moneySprayingMapper.selectMoneySpraying(moneySpraying);
		
		// 2. 뿌리기 건은 10분만 유효하며, 동일한 대화방에 속한 사용자만이 받을 수 있음
		if(moneySpraying == null) {
			throw new GeneralException(ResponseCode.FAIL, "존재하지 않거나 만료된 뿌리기 건입니다");
		}
		
		// 3. 뿌리기 금액은 뿌리기 총액을 넘을 수 없음
		if (moneySpraying.getTotalAmount().equals(moneySpraying.getRemitAmount())) {
			throw new GeneralException(ResponseCode.FAIL, "모든 금액이 소진되었습니다");
		}
		
		long sprayingId = moneySpraying.getSprayingId();
		
		// 4. 자신이 뿌리기한 건은 자신이 받을 수 없음
		long senderId = moneySpraying.getUserId();
		if(userId == senderId) {
			throw new GeneralException(ResponseCode.FAIL, "자신이 뿌리기한 건은 자신이 받을 수 없습니다");
		}
		
		// 5. 뿌리기 당 한 사용자는 한번만 받을 수 있음 
		MoneySprayingDetail moneySprayingDetail = new MoneySprayingDetail();
		moneySprayingDetail.setSprayingId(sprayingId);
		moneySprayingDetail.setUserId(userId);
		if(moneySprayingDetailMapper.selectMoneySprayingDetail(moneySprayingDetail) != null) {
			throw new GeneralException(ResponseCode.FAIL, "뿌리기 당 한 사용자는 한번만 받을 수 있습니다");
		} 
		
		// 6. (1)에서 얻은 spraying_id와 userId로 비어있는 detail table을 업데이트하고, 받기 완료된 금액을 업데이트한다.
		moneySprayingDetailMapper.updateMoneySprayingDetail(moneySprayingDetail);
		BigDecimal totalRemittedAmount = moneySpraying.getRemitAmount().add(moneySprayingDetail.getAmount());
		updateMoneySpraying(sprayingId, totalRemittedAmount);
		
		return moneySprayingDetail.getAmount();
	}
	
	/**
	 * 받기 완료된 금액을 업데이트 합니.
	 * @param sprayingId 뿌리기 아이디
	 * @param remitAmount 받기 완료된 금액
	 */
	private void updateMoneySpraying(long sprayingId, BigDecimal remitAmount) {
		MoneySpraying moneySpraying = new MoneySpraying();
		moneySpraying.setSprayingId(sprayingId);
		moneySpraying.setRemitAmount(remitAmount);
		
		moneySprayingMapper.updateMoneySpraying(moneySpraying);
	}
	
	/**
	 * 뿌리기 건을 조회합니다
	 * 
	 * @param userId 뿌린 사람 아이디
	 * @param roomId 대화방 아이디
	 * @param token 뿌리기 요청건에 대한 고유 token
	 * @return
	 */
	public Map<String, Object> describeMoneySpraying(long userId, String roomId, String token) {
		
		MoneySpraying moneySpraying = new MoneySpraying();
		moneySpraying.setUserId(userId);
		moneySpraying.setToken(token);
		moneySpraying.setRoomId(roomId);
		moneySpraying.setRetrieveInterval(7);
		moneySpraying.setRetrieveUnit(RetrieveUnit.DAY);

		moneySpraying = moneySprayingMapper.selectMoneySpraying(moneySpraying);
		
		if(moneySpraying == null) {
			throw new GeneralException(ResponseCode.FAIL, "존재하지 않는 뿌리기 건입니다");
		}
		
		MoneySprayingDetail moneySprayingDetail= new MoneySprayingDetail();
		moneySprayingDetail.setSprayingId(moneySpraying.getSprayingId());
		List<MoneySprayingDetail> moneySprayingDetailList = moneySprayingDetailMapper.selectMoneySprayingDetailList(moneySprayingDetail);
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("info", moneySpraying);
		resultMap.put("details", moneySprayingDetailList);
		
		return resultMap;
	}
	
	/**
	 * 뿌릴 금액을 인원 수에 맞게 분배한다. 
	 * 
	 * @param totalAmount 뿌리려는 총 금액
	 * @param totalCount 뿌릴 인원 명수
	 * @return
	 */
	private BigDecimal[] getAmountPerPerson(BigDecimal totalAmount, int totalCount) {

		BigDecimal[] amounts = new BigDecimal[totalCount];
		int total = totalAmount.intValue();
		
		Random rand = new Random();
		for (int i = 0; i < amounts.length - 1; i++) {
			amounts[i] = new BigDecimal(rand.nextInt(total));
			total -= amounts[i].intValue();
		}
		amounts[amounts.length - 1] = new BigDecimal(total);
		
		return amounts;
	}
}