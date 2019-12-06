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

    /**
     * A method that sorts list of houses by distance based on a house source ascending
     * @param street
     * @return DataModel object that contains a list of sorted houses
     */
    public DataModel sortByDistanceToStreet(String street,DataModel dataModel){
        House sourceHouse = getHouseByName(street, dataModel.getHouses());
        List<House> housesList = sortByDistance(sourceHouse, dataModel.getHouses());
        dataModel.setHouses(housesList);
        return dataModel;
    }

    /**
     * A method that sorts a list of houses based on the number of rooms ascending
     * @param numOfRooms
     * @return DataModel object that contains a list of sorted houses
     * @throws Exception
     */
    public DataModel sortListByRoom(int numOfRooms, DataModel dataModel ){
        List<House> housesList = dataModel.getHouses();
        housesList = housesList.stream().filter(h -> h.getParams() != null && h.getParams().getRooms() != null &&
                h.getParams().getRooms() > numOfRooms)
                .collect(Collectors.toList());
        Collections.sort(housesList, new Comparator<House>() {
            @Override
            public int compare(House o1, House o2) {
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
    public DataModel fetchMissingHousesDataFromList(DataModel dataModel )  {
        List<House> housesList = dataModel.getHouses();
        List<House> filteredHousesList = new ArrayList<>();
        housesList.stream()
                .filter(h -> !h.getStreet().isEmpty())
                .forEach(h -> {
                    if (h.getParams() != null && h.getParams().getValue() != null && h.getParams().getRooms() != null
                            && h.getCoords() != null && h.getCoords().getLon() != null && h.getCoords().getLat() != null) {
                        filteredHousesList.add(h);
                    }
                });
        housesList.removeAll(filteredHousesList);
        housesList = housesList.stream().sorted(Comparator.comparing(House::getStreet)).collect(Collectors.toList());
        dataModel.setHouses(housesList);
        return dataModel;
    }

    /**
     * A method that decides which house to move to based on constrains
     * @return desired house Object
     * @throws Exception
     */
    public House moveToNearestHouseWithConstrains(String street, int room, int price,DataModel dataModel )  {
        House sourceHouse = getHouseByName(street, dataModel.getHouses());
        List<House> housesList = sortByDistance(sourceHouse, dataModel.getHouses());
        House house = housesList.stream()
                .filter(h -> h.getParams() != null  && h.getParams().getRooms() !=null &&
                        h.getParams().getRooms() > room && h.getParams().getValue() < price)
                .findFirst().orElse(null);
        return house;
    }

    /**
     * A method that takes a a source house and list of houses and return a sorted list based on distance ascending
     * @param sourceHouse
     * @param destHousesList
     * @return list of sorted houses based on distance ascending
     */
    private static List<House> sortByDistance(House sourceHouse, List<House> destHousesList){
        return destHousesList.stream().filter(house -> !house.equals(sourceHouse))
                .sorted(Comparator.comparingDouble(house -> house.distanceTo(sourceHouse))).collect(Collectors.toList());
    }

    /**
     *A method that search house by name
     * @param name
     * @param housesList
     * @return house object
     */
    private static House getHouseByName(String name, List<House> housesList){
        return  housesList.stream()
                .filter(s -> s.getStreet().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }
}
