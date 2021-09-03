package com.advanced.marsroverkata.web.model.rest;
import java.io.Serializable;
import java.util.Objects;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Alberto Senserrich Montals
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "position", "orders" })
@Generated("jsonschema2pojo")
public class Robot implements Serializable {

	public Robot(Integer x, Integer y, String orientation, String orders) {
		super();
		this.position = new Position(x, y, orientation);
		this.orders = orders;
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

	@JsonProperty("orders")
	public String getOrders() {
		return orders;
	}

}
