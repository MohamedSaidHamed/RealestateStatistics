package com.realestate.statistics.controller;

import com.realestate.statistics.model.Coords;
import com.realestate.statistics.model.DataModel;
import com.realestate.statistics.model.Houses;
import com.realestate.statistics.service.DataFetchingService;
import com.realestate.statistics.service.DataHandlingService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class MainControllerTest {

    @Autowired
    DataFetchingService dataFetchingService;
    @Autowired
    DataHandlingService dataHandlingService;

    @Test
    void fetchData() throws Exception {
        String dataSourceUrl = "https://demo.interfacema.de/programming-assessment-1.0/buildings";
        ResponseEntity<DataModel> response = mockServices(dataSourceUrl);
        assertEquals(response.getStatusCode(),(HttpStatus.OK));
        assertNotNull(dataFetchingService.dataFetching());

    }

    @Test
    void sortByDistance() throws Exception {
        assertNotNull(dataHandlingService.sortListByDistance("Eberswalder Straße 55"));
        Coords coords1 = new Coords();
        coords1.setLat(52.5013632);
        coords1.setLon(13.4174913);
        Coords coords2 = new Coords();
        coords2.setLat(52.5418739);
        coords2.setLon(13.4057378);
        assertNotNull(dataHandlingService.calculateDistance(coords1,coords2));
        assertNotNull(dataHandlingService.calculateDistance(null,null));
        DataModel model = dataHandlingService.sortListByDistance("Eberswalder Straße 55");
        Assert.assertEquals(model.getHouses().get(0).getStreet(),"Fehrbelliner Straße 23");
    }

    @Test
    void sortByRooms() throws Exception{
        assertNotNull(dataHandlingService.sortListByRoom(5));
        assertNotNull(dataHandlingService.sortListByRoom(100));
        DataModel model = dataHandlingService.sortListByRoom(5);
        for (Houses house : model.getHouses()) {
            Assert.assertNotEquals(house.getParams().getRooms(), new Integer(5));
        }
        Assert.assertEquals(model.getHouses().get(0).getParams().getRooms(),new Integer(8));
    }

    @Test
    void fetchMissingHousesData() throws Exception {
        assertNotNull(dataHandlingService.fetchMissingHousesDataFromList());
        DataModel model = dataHandlingService.fetchMissingHousesDataFromList();
        Assert.assertEquals(model.getHouses().get(0).getStreet(),"Brandenburgische Straße 10");
    }

    @Test
    void moveToHouseWithConstrains() throws Exception {
        assertNotNull(dataHandlingService.moveToNearestHouseWithConstrains("Eberswalder Straße 55", 5, 5000000));
        Houses house = dataHandlingService.moveToNearestHouseWithConstrains("Eberswalder Straße 55", 5, 5000000);
        Assert.assertEquals(house.getStreet(),"Hermannstraße 1");

    }
    ResponseEntity<DataModel> mockServices(String url) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<DataModel> response
                = restTemplate.getForEntity(url + "/1", DataModel.class);
        return response;
    }
}