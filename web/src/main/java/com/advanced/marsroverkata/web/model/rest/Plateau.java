package com.advanced.marsroverkata.web.model.rest;

import java.io.Serializable;
import java.util.Objects;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "x", "y" })
@Generated("jsonschema2pojo")
public class Plateau implements Serializable {

	@JsonProperty("x")
	private Integer x;
	@JsonProperty("y")
	private Integer y;
	private final static long serialVersionUID = -7736203681213553981L;

	public Plateau(Integer x, Integer y) {
		super();
		this.x = x;
		this.y = y;
	}

	@JsonProperty("x")
	public Integer getX() {
		return x;
	}

	@JsonProperty("x")
	public void setX(Integer x) {
		this.x = x;
	}

	public Plateau withX(Integer x) {
		this.x = x;
		return this;
	}

	@JsonProperty("y")
	public Integer getY() {
		return y;
	}

	@JsonProperty("y")
	public void setY(Integer y) {
		this.y = y;
	}

	public Plateau withY(Integer y) {
		this.y = y;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Plateau.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)))
				.append('[');
		sb.append("x");
		sb.append('=');
		sb.append(this.x);
		sb.append(',');
		sb.append("y");
		sb.append('=');
		sb.append(this.y);
		sb.append(',');
		if (sb.charAt((sb.length() - 1)) == ',') {
			sb.setCharAt((sb.length() - 1), ']');
		} else {
			sb.append(']');
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Plateau other = (Plateau) obj;
		return Objects.equals(x, other.x) && Objects.equals(y, other.y);
	}

	

}
