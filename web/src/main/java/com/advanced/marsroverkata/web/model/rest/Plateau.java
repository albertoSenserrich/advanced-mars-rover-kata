package com.advanced.marsroverkata.web.model.rest;

import java.io.Serializable;

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

	@JsonProperty("y")
	public Integer getY() {
		return y;
	}

}
