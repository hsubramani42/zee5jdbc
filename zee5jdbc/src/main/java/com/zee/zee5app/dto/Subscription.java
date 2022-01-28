package com.zee.zee5app.dto;

import java.util.Date;
import java.util.Objects;

import com.zee.zee5app.exception.InvalidAmountException;
import com.zee.zee5app.exception.InvalidIdLengthException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
//@EqualsAndHashCode
@ToString
public class Subscription implements Comparable<Subscription> {

	private String type;
	private Date dateOfPurchase;
	private boolean status;
	private String packCountry;
	private String paymentMode;
	private boolean autoRenewal;
	private Date expriryDate;
	private float amount;
	private String id;
	public Subscription(String type, Date dateOfPurchase, boolean status, String packCountry, String paymentMode,
			boolean autoRenewal, Date expriryDate, String id, float amount) throws InvalidAmountException, InvalidIdLengthException {
		this.setType(type);
		this.setDateOfPurchase(dateOfPurchase);
		this.setAutoRenewal(autoRenewal);
		this.setStatus(status);
		this.setId(id);
		this.setPackCountry(packCountry);
		this.setExpriryDate(expriryDate);
		this.setAmount(amount);
		
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setDateOfPurchase(Date dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public void setPackCountry(String packCountry) {
		this.packCountry = packCountry;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public void setAutoRenewal(boolean autoRenewal) {
		this.autoRenewal = autoRenewal;
	}
	public void setExpriryDate(Date expriryDate) {
		this.expriryDate = expriryDate;
	}

	public void setAmount(float amount) throws InvalidAmountException {
		if(amount < 400)
			throw new InvalidAmountException("Invalid Amount");
		this.amount = amount;
	}
	public void setId(String id) throws InvalidIdLengthException{
		if((id==null)||(id.length()<8))
			throw new InvalidIdLengthException("Invalid Id length");
		this.id = id;
	}
	@Override
	public int hashCode() {
		return Objects.hash(amount, autoRenewal, dateOfPurchase, expriryDate, id, packCountry, paymentMode, status,
				type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Subscription))
			return false;
		Subscription other = (Subscription) obj;
		return Float.floatToIntBits(amount) == Float.floatToIntBits(other.amount) && autoRenewal == other.autoRenewal
				&& Objects.equals(dateOfPurchase, other.dateOfPurchase)
				&& Objects.equals(expriryDate, other.expriryDate) && Objects.equals(id, other.id)
				&& Objects.equals(packCountry, other.packCountry) && Objects.equals(paymentMode, other.paymentMode)
				&& status == other.status && Objects.equals(type, other.type);
	}
	@Override
	public int compareTo(Subscription obj) {
		return this.id.compareTo(obj.id);
	}
	
	

}
