package com.advanced.marsroverkata.web.model.rest;
import java.io.Serializable;
import java.util.Objects;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "position", "orders" })
@Generated("jsonschema2pojo")
public class Robot implements Serializable {

	public Robot(Integer x, Integer y, String orientation, String orders) {
		super();
		this.position = new Position(x, y, orientation);
		this.orders = orders;
	}

	public Robot(Integer x, Integer y, String orientation) {
		super();
		this.position = new Position(x, y, orientation);		
	}
	
	@JsonProperty("position")
	private Position position;
	@JsonProperty("orders")
	private String orders;

	private final static long serialVersionUID = -2489410180235305929L;

	@JsonProperty("position")
	public Position getPosition() {
		return position;
	}

	@JsonProperty("position")
	public void setPosition(Position position) {
		this.position = position;
	}

	public Robot withPosition(Position position) {
		this.position = position;
		return this;
	}

	@JsonProperty("orders")
	public String getOrders() {
		return orders;
	}

	@JsonProperty("orders")
	public void setOrders(String orders) {
		this.orders = orders;
	}

	public Robot withOrders(String orders) {
		this.orders = orders;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Robot.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)))
				.append('[');
		sb.append("position");
		sb.append('=');
		sb.append(((this.position == null) ? "<null>" : this.position));
		sb.append(',');
		sb.append("orders");
		sb.append('=');
		sb.append(((this.orders == null) ? "<null>" : this.orders));
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
		return Objects.hash(orders, position);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Robot other = (Robot) obj;
		return Objects.equals(orders, other.orders) && Objects.equals(position, other.position);
	}

}
