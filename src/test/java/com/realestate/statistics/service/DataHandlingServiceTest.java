package com.realestate.statistics.service;

import com.realestate.statistics.model.Coords;
import com.realestate.statistics.model.DataModel;
import com.realestate.statistics.model.House;
import com.realestate.statistics.model.Params;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNot;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class DataHandlingServiceTest {

    @Test
    void sortEmptyListByDistanceToStreet() {
        DataModel dm = new DataModel();
        DataHandlingService dataHandlingService = new DataHandlingService();
        dm.setHouses(Collections.emptyList());
        DataModel result = dataHandlingService.sortByDistanceToStreet("Eberswalder Straße 55",dm);
        assertEquals(Collections.emptyList(),result.getHouses());
    }
    @Test
    void sortByDistanceToStreet() {
        DataHandlingService dataHandlingService = new DataHandlingService();
        DataModel dm = new DataModel();
        House house = new House();
        house.setStreet("anything");
        house.setCoords(new Coords());
        house.setParams(new Params());
        dm.setHouses(Arrays.asList(house));
        DataModel result = dataHandlingService.sortByDistanceToStreet("Eberswalder Straße 55",dm);
        Assert.assertThat(result.getHouses(), Matchers.contains(house));
    }
    @Test
    void sortByDistanceToSameHouse() {
        DataHandlingService dataHandlingService = new DataHandlingService();
        DataModel dm = new DataModel();
        House house = new House();
        house.setStreet("Eberswalder Straße 55");
        house.setCoords(new Coords());
        house.setParams(new Params());
        dm.setHouses(Arrays.asList(house));
        DataModel result = dataHandlingService.sortByDistanceToStreet("Eberswalder Straße 55",dm);
        assertEquals(Collections.emptyList(),result.getHouses());
    }

    @Test
    void sortListByRoomNumberGreaterThanProvided() {
        DataHandlingService dataHandlingService = new DataHandlingService();
        DataModel dm = new DataModel();
        House house = new House();
        Params params = new Params();
        params.setRooms(10);
        params.setValue(2000000);
        house.setStreet("anything");
        house.setCoords(new Coords());
        house.setParams(params);
        dm.setHouses(Arrays.asList(house));
        DataModel result = dataHandlingService.sortListByRoom(5,dm);
        Assert.assertThat(result.getHouses(), Matchers.contains(house));
    }
    @Test
    void sortListByRoomNumberLessThanProvided() {
        DataHandlingService dataHandlingService = new DataHandlingService();
        DataModel dm = new DataModel();
        House house = new House();
        Params params = new Params();
        params.setRooms(3);
        params.setValue(2000000);
        house.setStreet("anything");
        house.setCoords(new Coords());
        house.setParams(params);
        dm.setHouses(Arrays.asList(house));
        DataModel result = dataHandlingService.sortListByRoom(5,dm);
        Assert.assertThat(result.getHouses(), new IsNot(Matchers.contains(house)));
    }

    @Test
    void fetchMissingHousesDataFromListWithMissingParams() {
        DataHandlingService dataHandlingService = new DataHandlingService();
        DataModel dm = new DataModel();
        House house = new House();
        Params params = new Params();
        params.setRooms(10);
        params.setValue(2000000);
        house.setStreet("anything");
        house.setCoords(new Coords());
        house.setParams(params);
        dm.setHouses(Arrays.asList(house));
        DataModel result = dataHandlingService.fetchMissingHousesDataFromList(dm);
        Assert.assertThat(result.getHouses(),Matchers.contains(house));
    }

    @Test
    void moveToNearestHouseWithConstrains() {
        DataHandlingService dataHandlingService = new DataHandlingService();
        DataModel dm = new DataModel();
        House house = new House();
        Params params = new Params();
        params.setRooms(10);
        params.setValue(2000000);
        house.setStreet("anything");
        house.setCoords(new Coords());
        house.setParams(params);
        dm.setHouses(Arrays.asList(house));
        House result = dataHandlingService.moveToNearestHouseWithConstrains("Eberswalder Straße 55",5,5000000,dm);
        assertEquals(result,house);
    }
}