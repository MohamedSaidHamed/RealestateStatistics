package com.realestate.statistics.model;

import java.util.List;

public class DataModel {
    List<House> houses;

    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }

    @Override
    public String toString() {
        return "Data{" +
                "houses=" + houses +
                '}';
    }
}
