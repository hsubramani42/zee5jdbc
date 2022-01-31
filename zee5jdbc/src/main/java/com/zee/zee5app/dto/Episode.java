package com.zee.zee5app.dto;

import com.zee.zee5app.exception.InvalidIdLengthException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Setter
@ToString
public class Episode {

	@Setter(value = AccessLevel.NONE)
	private String epiId;

	@Setter(value = AccessLevel.NONE)
	private String serialId;

	private String episodename;
	private int length;
	private String location;
	private String trailer;

	public void setEpiId(String epiId) throws InvalidIdLengthException {
		if ((epiId == null) || (epiId.length() < 6))
			throw new InvalidIdLengthException("Id length < 6");
		this.epiId = epiId;
	}

	public void setSerialId(String serialId) throws InvalidIdLengthException {
		if ((serialId == null) || (serialId.length() < 6))
			throw new InvalidIdLengthException("Id length < 6");
		this.serialId = serialId;
	}

	public Episode(String epiId, String serialId, String episodename, int length, String location, String trailer)
			throws InvalidIdLengthException {
		super();
		this.setEpiId(epiId);
		;
		this.setSerialId(serialId);
		;
		this.setEpisodename(episodename);
		;
		this.setLength(length);
		;
		this.setLocation(location);
		;
		this.setTrailer(trailer);
		;
	}

}
