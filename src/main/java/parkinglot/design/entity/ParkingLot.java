package parkinglot.design.entity;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ParkingLot {

    Integer buildingId;
    Map<Integer, ParkingFloor> parkingFloors = new HashMap<>();

    public void addFloor(ParkingFloor parkingFloor) {
        parkingFloors.putIfAbsent(parkingFloor.floorNumber, parkingFloor);
    }
}
