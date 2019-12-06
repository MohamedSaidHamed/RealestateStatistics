package com.realestate.statistics.controller;

import com.realestate.statistics.model.*;
import com.realestate.statistics.service.DataFetchingService;
import com.realestate.statistics.service.DataHandlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    DataFetchingService dataFetchingService;
    @Autowired
    DataHandlingService dataHandlingService;

    /**
     * API to fetch all the data
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/fetchData/", produces = "application/json")
    public DataModel fetchData() throws Exception {
        DataModel dataModel = dataFetchingService.getAllHousesData();
        return dataModel;
    }

    /**
     * API to sort all available houses from a specific street address based on distance ASC
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/sortByDistanceFrom/{street}")
    public DataModel sortByDistance(@PathVariable String street) {
        DataModel dataModel = dataFetchingService.getAllHousesData();
        dataModel = dataHandlingService.sortByDistanceToStreet(street,dataModel);
        return dataModel;
    }

    /**
     * API to sort houses' rooms based on the desired room number ASC
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/sortByRoomsGreaterThan/{rooms}")
    public DataModel sortByRooms(@PathVariable int rooms) {
        DataModel dataModel = dataFetchingService.getAllHousesData();
        dataModel = dataHandlingService.sortListByRoom(rooms,dataModel);
        return dataModel;
    }

    /**
     * API to return houses that has missing data
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/fetchMissingHousesData/")
    public DataModel fetchMissingHousesData() {
        DataModel dataModel = dataFetchingService.getAllHousesData();
        dataModel = dataHandlingService.fetchMissingHousesDataFromList(dataModel);
        return dataModel;
    }

    /**
     * API to decide which house to move to based on street address, room number and prices
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/moveToHouseWithConstrains/{street}/{room}/{price}")
    public House moveToHouseWithConstrains(@PathVariable String street,
                                           @PathVariable int room,
                                           @PathVariable int price) throws Exception {
        DataModel dataModel = dataFetchingService.getAllHousesData();
        House house = dataHandlingService.moveToNearestHouseWithConstrains(street, room, price,dataModel);
        return house;
    }
}
