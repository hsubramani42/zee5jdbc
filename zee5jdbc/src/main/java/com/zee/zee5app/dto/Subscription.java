package com.zee.zee5app.dto;

import java.util.Date;

import com.zee.zee5app.dto.enums.PLAN_AUTORENEWAL;
import com.zee.zee5app.dto.enums.PLAN_STATUS;
import com.zee.zee5app.dto.enums.PLAN_TYPE;
import com.zee.zee5app.exception.InvalidAmountException;
import com.zee.zee5app.exception.InvalidIdLengthException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@Setter
public class Subscription implements Comparable<Subscription> {

	@Setter(value = AccessLevel.NONE)
	private String id;

	private Date dateOfPurchase;
	private Date expiryDate;

	@Setter(value = AccessLevel.NONE)
	private float amount;
	private PLAN_STATUS status;
	private PLAN_TYPE type;
	private PLAN_AUTORENEWAL autoRenewal;

	@Setter(value = AccessLevel.NONE)
	private String regId;

	public Subscription(String id, Date dateOfPurchase, Date expiryDate, float amount, PLAN_STATUS status, PLAN_TYPE type,
			PLAN_AUTORENEWAL autoRenewal, String regId) throws InvalidAmountException, InvalidIdLengthException {
		this.setId(id);
		this.setDateOfPurchase(dateOfPurchase);
		this.setExpiryDate(expiryDate);
		this.setAmount(amount);
		this.setStatus(status);
		this.setType(type);
		this.setAutoRenewal(autoRenewal);
		this.setRegId(regId);
	}

	public void setAmount(float amount) throws InvalidAmountException {
		if (amount < 50)
			throw new InvalidAmountException("Invalid Amount");
		this.amount = amount;
	}

	public void setId(String id) throws InvalidIdLengthException {
		if ((id == null) || (id.length() < 8))
			throw new InvalidIdLengthException("Invalid Id length");
		this.id = id;
	}

	public void setRegId(String regId) throws InvalidIdLengthException {
		if ((regId == null) || (regId.length() < 6))
			throw new InvalidIdLengthException("Id length < 6.");
		this.regId = regId;
	}

	@Override
	public int compareTo(Subscription obj) {
		return this.id.compareTo(obj.id);
	}

}
