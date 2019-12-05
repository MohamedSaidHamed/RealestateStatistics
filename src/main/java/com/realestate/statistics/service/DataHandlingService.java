package com.realestate.statistics.service;

import com.realestate.statistics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataHandlingService {
    @Autowired
    DataFetchingService dataFetchingService;

    /**
     * A method that sorts list of houses by distance based on a house source ascending
     * @param street
     * @return DataModel object that contains a list of sorted houses
     * @throws Exception
     */
    public DataModel sortListByDistance(String street) throws Exception {
        DataModel dataModel = dataFetchingService.dataFetching();
        List<Houses> housesList = dataModel.getHouses();
        Houses sourceHouse = getHouseByName(street, housesList);
        housesList = sortByDistance(sourceHouse, housesList);
        dataModel.setHouses(housesList);
        return dataModel;
    }

    /**
     * A method that sorts a list of houses based on the number of rooms ascending
     * @param numOfRooms
     * @return DataModel object that contains a list of sorted houses
     * @throws Exception
     */
    public DataModel sortListByRoom(int numOfRooms) throws Exception {
        DataModel dataModel = dataFetchingService.dataFetching();
        List<Houses> housesList = dataModel.getHouses();
        housesList = housesList.stream().filter(h -> h.getParams() != null && h.getParams().getRooms() != null && h.getParams().getRooms() > numOfRooms)
                .collect(Collectors.toList());
        Collections.sort(housesList, new Comparator<Houses>() {
            @Override
            public int compare(Houses o1, Houses o2) {
                return o1.getParams().getRooms().compareTo(o2.getParams().getRooms());
            }
        });
        dataModel.setHouses(housesList);
        return dataModel;
    }

    /**
     * ِ A method that returns a list of houses that contains missing data
     * @return DataModel object that contains a list of houses
     * @throws Exception
     */
    public DataModel fetchMissingHousesDataFromList() throws Exception {
        DataModel dataModel = dataFetchingService.dataFetching();
        List<Houses> housesList = dataModel.getHouses();
        List<Houses> filteredHousesList = new ArrayList<>();
        housesList.stream()
                .filter(h -> !h.getStreet().isEmpty())
                .forEach(h -> {
                    if (h.getParams() != null && h.getParams().getValue() != null && h.getParams().getRooms() != null
                            && h.getCoords() != null && h.getCoords().getLon() != null && h.getCoords().getLat() != null) {
                        filteredHousesList.add(h);
                    }
                });
        housesList.removeAll(filteredHousesList);
        housesList = housesList.stream().sorted(Comparator.comparing(Houses::getStreet)).collect(Collectors.toList());
        dataModel.setHouses(housesList);
        return dataModel;
    }

    /**
     * A method that decides which house to move to based on constrains
     * @return desired house Object
     * @throws Exception
     */
    public Houses moveToNearestHouseWithConstrains(String street) throws Exception {
        DataModel dataModel = dataFetchingService.dataFetching();
        List<Houses> housesList = dataModel.getHouses();
        Houses sourceHouse = getHouseByName(street, housesList);
        housesList = sortByDistance(sourceHouse, housesList);
        Houses house = housesList.stream()
                .filter(h -> h.getParams() != null  && h.getParams().getRooms() !=null &&  h.getParams().getRooms() > 10 && h.getParams().getValue() < 5000000)
                .findFirst().orElse(null);
        return house;
    }

    /**
     * A reusable method to calculate the distance between any two coordinates
     * @param coords1
     * @param coords2
     * @return distance
     */
    public static double calculateDistance(Coords coords1, Coords coords2) {
        if(coords1 == coords2 ){
            return 0;
        }
        return Point2D.distance(coords1.getLat(), coords1.getLon(), coords2.getLat(), coords2.getLon());
    }

    /**
     * A method that takes a a source house and list of houses and return a sorted list based on distance ascending
     * @param sourceHouse
     * @param destHousesList
     * @return list of sorted houses based on distance ascending
     * @throws Exception
     */
    private static List<Houses> sortByDistance(Houses sourceHouse, List<Houses> destHousesList) throws Exception{
        destHousesList.stream()
                .forEach(h -> {
                    h.setDistance(
                            calculateDistance(sourceHouse.getCoords(), h.getCoords()));
                });

        destHousesList = destHousesList.stream().filter(s -> s.getDistance() > 0)
                .sorted(Comparator.comparingDouble(Houses::getDistance)).collect(Collectors.toList());
        return destHousesList;
    }

    /**
     *A method that search house by name
     * @param name
     * @param housesList
     * @return house object
     * @throws Exception
     */
    private static Houses getHouseByName(String name, List<Houses> housesList) throws Exception{
        return  housesList.stream()
                .filter(s -> s.getStreet().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }
}
