package com.advanced.marsroverkata.web.model.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "plateau",
    "robots"
})
@Generated("jsonschema2pojo")
public class SpaceStationCommands implements Serializable
{

    @JsonProperty("plateau")
    private Plateau plateau;
    @JsonProperty("robots")
    private List<Robot> robots = new ArrayList<Robot>();
    private final static long serialVersionUID = -8513444453448656245L;

    @JsonProperty("plateau")
    public Plateau getPlateau() {
        return plateau;
    }

    @JsonProperty("plateau")
    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public SpaceStationCommands withPlateau(Plateau plateau) {
        this.plateau = plateau;
        return this;
    }

    @JsonProperty("robots")
    public List<Robot> getRobots() {
        return robots;
    }

    @JsonProperty("robots")
    public void setRobots(List<Robot> robots) {
        this.robots = robots;
    }

    public SpaceStationCommands withRobots(List<Robot> robots) {
        this.robots = robots;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SpaceStationCommands.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("plateau");
        sb.append('=');
        sb.append(((this.plateau == null)?"<null>":this.plateau));
        sb.append(',');
        sb.append("robots");
        sb.append('=');
        sb.append(((this.robots == null)?"<null>":this.robots));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.plateau == null)? 0 :this.plateau.hashCode()));
        result = ((result* 31)+((this.robots == null)? 0 :this.robots.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SpaceStationCommands) == false) {
            return false;
        }
        SpaceStationCommands rhs = ((SpaceStationCommands) other);
        return (((this.plateau == rhs.plateau)||((this.plateau!= null)&&this.plateau.equals(rhs.plateau)))&&((this.robots == rhs.robots)||((this.robots!= null)&&this.robots.equals(rhs.robots))));
    }

}
