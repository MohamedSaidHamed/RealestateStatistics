package com.realestate.statistics.model;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class HouseTest {

    @Test
    void distanceTo() {
        House house = new House();
        House house1 = new House();
        double result = house.distanceTo(house1);
        assertEquals(0,result);
    }
}