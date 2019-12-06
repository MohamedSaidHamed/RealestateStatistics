package com.realestate.statistics.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.awt.geom.Point2D;
import java.util.Objects;

public class House {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return Double.compare(house.distance, distance) == 0 &&
                Objects.equals(coords, house.coords) &&
                Objects.equals(params, house.params) &&
                Objects.equals(street, house.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coords, params, street, distance);
    }
    /**
     * A method to calculate the distance between coordinates
     * @param house
     * @return distance
     */
    public double distanceTo(House house){
        if(coords == house.getCoords() ){
            return 0;
        }
        return Point2D.distance(coords.getLat(), coords.getLon(), house.getCoords().getLat(), house.getCoords().getLon());
    }
}
