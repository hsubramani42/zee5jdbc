package com.zee.zee5app.dto;

import java.util.Date;
import com.zee.zee5app.dto.enums.GENRE;
import com.zee.zee5app.dto.enums.LANGUAGE;
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
public class Series implements Comparable<Series> {

	@Setter(value = AccessLevel.NONE)
	private String id;
	private int agelimit;
	private String[] cast;
	private GENRE genre;
	private int length;
	private String trailer;
	private Date releasedate;
	private LANGUAGE language;
	private int noofepisodes;

	public Series(String id, int agelimit, String[] cast, GENRE genre, int length, String trailer, Date releasedate,
			LANGUAGE language, int noofepisodes) throws InvalidIdLengthException {
		this.setId(id);
		this.setAgelimit(agelimit);
		this.setCast(cast);
		this.setGenre(genre);
		this.setLength(length);
		this.setTrailer(trailer);
		this.setReleasedate(releasedate);
		this.setLanguage(language);
		this.setNoofepisodes(noofepisodes);
	}

	public void setId(String id) throws InvalidIdLengthException {
		if ((id == null) || (id.length() < 8))
			throw new InvalidIdLengthException("Invalid Id length");
		this.id = id;
	}

	@Override
	public int compareTo(Series obj) {
		return this.id.compareTo(obj.id);
	}

}
