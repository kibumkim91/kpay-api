package com.kpay.moneyspraying.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MoneySprayingDetail {
	
	private BigDecimal amount;
	private long userId;

	@JsonIgnore
	private long sprayingDtlId;

	@JsonIgnore
	private long sprayingId;

	@JsonIgnore
	private String createAt;

	public long getSprayingDtlId() {
		return sprayingDtlId;
	}

	public void setSprayingDtlId(long sprayingDtlId) {
		this.sprayingDtlId = sprayingDtlId;
	}

	public long getSprayingId() {
		return sprayingId;
	}

	public void setSprayingId(long sprayingId) {
		this.sprayingId = sprayingId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	@Override
	public String toString() {
		return "MoneySprayingDetail [sprayingDtlId=" + sprayingDtlId + ", sprayingId=" + sprayingId + ", amount="
				+ amount + ", userId=" + userId + ", createAt=" + createAt + "]";
	}
}