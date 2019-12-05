package com.realestate.statistics.model;

import java.util.List;

public class DataModel {
    List<Houses> houses;

    public List<Houses> getHouses() {
        return houses;
    }

    public void setHouses(List<Houses> houses) {
        this.houses = houses;
    }

    @Override
    public String toString() {
        return "Data{" +
                "houses=" + houses +
                '}';
    }
}
