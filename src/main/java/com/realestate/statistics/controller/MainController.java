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
        DataModel dataModel = dataFetchingService.dataFetching();
        return dataModel;
    }

    /**
     * API to sort all available houses from a specific street address based on distance ASC
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/sortByDistanceFrom/{street}")
    public DataModel sortByDistance(@PathVariable String street) throws Exception {
        DataModel dataModel = dataHandlingService.sortListByDistance(street);
        return dataModel;
    }

    /**
     * API to sort houses' rooms based on the desired room number ASC
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/sortByRoomsGreaterThan/{rooms}")
    public DataModel sortByRooms(@PathVariable int rooms) throws Exception {
        DataModel dataModel = dataHandlingService.sortListByRoom(rooms);
        return dataModel;
    }

    /**
     * API to return houses that has missing data
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/fetchMissingHousesData/")
    public DataModel fetchMissingHousesData() throws Exception {
        DataModel dataModel = dataHandlingService.fetchMissingHousesDataFromList();
        return dataModel;
    }

    /**
     * API to decide which house to move to based on street address, room number and prices
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/moveToHouseWithConstrains/{street}/{room}/{price}")
    public Houses moveToHouseWithConstrains(@PathVariable String street,
                                            @PathVariable int room,
                                            @PathVariable int price) throws Exception {
        Houses house = dataHandlingService.moveToNearestHouseWithConstrains(street, room, price);
        return house;
    }
}
