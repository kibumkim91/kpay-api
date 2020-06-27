package com.kpay.moneyspraying.domain;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MoneySpraying {

	private long sprayingId;

	private String roomId;

	@Min(1)
	private int totalCount;

	@DecimalMin(value = "0.0", inclusive = false)
	@Digits(integer = 7, fraction = 2)
	private BigDecimal totalAmount;

	private BigDecimal remitAmount;

	private String token;

	private long userId;

	private String createAt;

	/**
	 * 조회 기간
	 */
	@JsonIgnore
	private int retrieveInterval;
	
	/**
	 * 조회 기간 단위
	 * e.g) DAY, MINUTE
	 */
	@JsonIgnore
	private String retrieveUnit;

	public long getSprayingId() {
		return sprayingId;
	}

	public void setSprayingId(long sprayingId) {
		this.sprayingId = sprayingId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getRemitAmount() {
		return remitAmount;
	}

	public void setRemitAmount(BigDecimal remitAmount) {
		this.remitAmount = remitAmount;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}
	
	public int getRetrieveInterval() {
		return retrieveInterval;
	}

	public void setRetrieveInterval(int retrieveInterval) {
		this.retrieveInterval = retrieveInterval;
	}

	public String getRetrieveUnit() {
		return retrieveUnit;
	}

	public void setRetrieveUnit(String retrieveUnit) {
		this.retrieveUnit = retrieveUnit;
	}

}