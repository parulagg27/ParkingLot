package parkinglot.design.service;

import parkinglot.design.constants.VehicleType;
import parkinglot.design.entity.Dashboard;
import parkinglot.design.entity.ParkingFloor;
import parkinglot.design.entity.ParkingLot;
import parkinglot.design.entity.ParkingSlot;
import parkinglot.design.model.ParkingSlotPOJO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AdminService {

    Dashboard dashboard = Dashboard.getInstance();

    public ParkingFloor addParkingFloor(ParkingLot parkingLot) {
        var nextFloorId = parkingLot.getParkingFloors().size();
        var parkingFloor = ParkingFloor.builder()
                .floorNumber(nextFloorId)
                .buildingId(parkingLot.getBuildingId())
                .vehicleTypeToSlotStatusMapping(new HashMap<>())
                .build();
        parkingLot.addFloor(parkingFloor);
        return parkingFloor;
    }

    public ParkingFloor addInitialSpots(ParkingFloor parkingFloor, Map<VehicleType, Integer> vehicleTypeToSlotCount) {
        vehicleTypeToSlotCount.keySet().forEach(type -> {
            int count = vehicleTypeToSlotCount.get(type);
            var parkingSlots = generateParkingSlotsOfType(parkingFloor, type, count);
            var parkingSlotPOJO = new ParkingSlotPOJO();
            parkingSlotPOJO.addEmptySlots(parkingSlots);
            dashboard.reflectNewEmptySlots(type, parkingSlots);
            parkingFloor.getVehicleTypeToSlotStatusMapping().put(type, parkingSlotPOJO);
        });
        return parkingFloor;
    }

    private List<ParkingSlot> generateParkingSlotsOfType(ParkingFloor parkingFloor, VehicleType type, int count) {
        return IntStream.range(0, count).mapToObj(i ->
                ParkingSlot.builder().id(i + 1)
                        .buildingId(parkingFloor.getBuildingId())
                        .floorId(parkingFloor.getFloorNumber())
                        .type(type)
                        .build()
        ).collect(Collectors.toList());
    }
}
