package com.realestate.statistics.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Houses {

    private Coords coords;
    private Params params;
    private String street;
    @JsonIgnore
    private double distance;

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Houses{" +
                "coords=" + coords +
                ", params=" + params +
                ", street='" + street + '\'' +
                '}';
    }
}
