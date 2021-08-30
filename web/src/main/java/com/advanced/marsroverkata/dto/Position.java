package com.advanced.marsroverkata.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "x", "y", "orientation" })
@Generated("jsonschema2pojo")
public class Position implements Serializable {

	public Position(Integer x, Integer y, String orientation) {
		super();
		this.x = x;
		this.y = y;
		this.orientation = orientation;
	}

	public Position(Integer x, Integer y, String orientation, String status) {
		super();
		this.x = x;
		this.y = y;
		this.orientation = orientation;
		this.status = status;
	}

	@JsonProperty("status")
	private String status;	
	@JsonProperty("x")
	private Integer x;
	@JsonProperty("y")
	private Integer y;
	@JsonProperty("orientation")
	private String orientation;
	private final static long serialVersionUID = -189482216218061566L;

	@JsonProperty("x")
	public Integer getX() {
		return x;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("x")
	public void setX(Integer x) {
		this.x = x;
	}

	public Position withX(Integer x) {
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

	public Position withY(Integer y) {
		this.y = y;
		return this;
	}

	@JsonProperty("orientation")
	public String getOrientation() {
		return orientation;
	}

	@JsonProperty("orientation")
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public Position withOrientation(String orientation) {
		this.orientation = orientation;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Position.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)))
				.append('[');
		sb.append("x");
		sb.append('=');
		sb.append(this.x);
		sb.append(',');
		sb.append("y");
		sb.append('=');
		sb.append(this.y);
		sb.append(',');
		sb.append("orientation");
		sb.append('=');
		sb.append(((this.orientation == null) ? "<null>" : this.orientation));
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
		return Objects.hash(orientation, status, x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		return Objects.equals(orientation, other.orientation) && Objects.equals(status, other.status)
				&& Objects.equals(x, other.x) && Objects.equals(y, other.y);
	}



	

}
