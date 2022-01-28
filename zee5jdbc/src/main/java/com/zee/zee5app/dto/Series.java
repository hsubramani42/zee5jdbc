package com.zee.zee5app.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.exception.InvalidNameException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
//@EqualsAndHashCode
@ToString
public class Series implements Comparable<Series> {

	private String name;
	private String category;
	private Date releaseDate;
	private String trailer;
	private String language;
	private String cast[];
	private int length;
	private String id;
	private String location;

	public Series(String name, String category, Date releaseDate, String trailer, String language, String[] cast,
			int length, String id, String location) throws InvalidNameException, InvalidIdLengthException {

		this.setName(name);
		this.setCategory(category);
		this.setReleaseDate(releaseDate);
		this.setTrailer(trailer);
		this.setLanguage(language);
		this.setCast(cast);
		this.setLength(length);
		this.setId(id);
		this.setLocation(location);
	}

	public void setName(String name) throws InvalidNameException {
		if ((name == null) || (name.length() == 0))
			throw new InvalidNameException("Invalid Name");
		this.name = name;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setCast(String[] cast) {
		this.cast = cast;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setId(String id) throws InvalidIdLengthException {
		if ((id == null) || (id.length() < 8))
			throw new InvalidIdLengthException("Invalid Id length");
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(cast);
		result = prime * result + Objects.hash(category, id, language, length, name, releaseDate, trailer);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Series))
			return false;
		Series other = (Series) obj;
		return Arrays.equals(cast, other.cast) && Objects.equals(category, other.category)
				&& Objects.equals(id, other.id) && Objects.equals(language, other.language) && length == other.length
				&& Objects.equals(name, other.name) && Objects.equals(releaseDate, other.releaseDate)
				&& Objects.equals(trailer, other.trailer);
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int compareTo(Series obj) {
		return this.id.compareTo(obj.id);
	}

}
