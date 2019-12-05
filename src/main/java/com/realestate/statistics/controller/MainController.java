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
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/fetchData/", produces = "application/json")
    public DataModel fetchData() throws Exception {
        DataModel dataModel = dataFetchingService.dataFetching();
        return dataModel;
    }

    /**
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/sortByDistanceFrom/{street}")
    public DataModel sortByDistance(@PathVariable String street) throws Exception {
        DataModel dataModel = dataHandlingService.sortListByDistance(street);
        return dataModel;
    }

    /**
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/sortByRoomsGreaterThan/{rooms}")
    public DataModel sortByRooms(@PathVariable int rooms) throws Exception {
        DataModel dataModel = dataHandlingService.sortListByRoom(rooms);
        return dataModel;
    }

    /**
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/fetchMissingHousesData/")
    public DataModel fetchMissingHousesData() throws Exception {
        DataModel dataModel = dataHandlingService.fetchMissingHousesDataFromList();
        return dataModel;
    }

    /**
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/moveToHouseWithConstrains/{street}")
    public Houses moveToHouseWithConstrains(@PathVariable String street) throws Exception {
        Houses house = dataHandlingService.moveToNearestHouseWithConstrains(street);
        return house;
    }
}
