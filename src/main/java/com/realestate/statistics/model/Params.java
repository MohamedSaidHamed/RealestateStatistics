package com.realestate.statistics.model;

public class Params {

    private Integer rooms;
    private Integer value;

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Params{" +
                "rooms=" + rooms +
                ", value=" + value +
                '}';
    }
}
