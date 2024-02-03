package parkinglot.design.entity;

import parkinglot.design.constants.VehicleType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dashboard {
    private Map<VehicleType, List<ParkingSlot>> occupiedSlots = new HashMap<>();
    private Map<VehicleType, List<ParkingSlot>> emptySlots = new HashMap<>();

    private Dashboard() {
    }

    private static final Dashboard INSTANCE = new Dashboard();

    public static Dashboard getInstance() {
        return INSTANCE;
    }

    public void updateDashBoardAfterParking(VehicleType type, ParkingSlot slotToBeOccupied) {
        this.emptySlots.get(type).remove(slotToBeOccupied);
        addParkingSlotToMap(this.occupiedSlots, type, slotToBeOccupied);
    }

    public void updateDashBoardAfterUnPark(VehicleType type, ParkingSlot slotToBeFreed) {
        this.occupiedSlots.get(type).remove(slotToBeFreed);
        addParkingSlotToMap(this.emptySlots, type, slotToBeFreed);
    }

    public void reflectNewEmptySlots(VehicleType type, List<ParkingSlot> parkingSlots) {
        if (this.emptySlots.containsKey(type)) {
            var existingEmptySlots = this.emptySlots.get(type);
            existingEmptySlots.addAll(parkingSlots);
            this.emptySlots.put(type, existingEmptySlots);
        } else this.emptySlots.put(type, parkingSlots);
    }

    private void addParkingSlotToMap(Map<VehicleType, List<ParkingSlot>> typeListMap, VehicleType type,
                                     ParkingSlot parkingSlot) {
        if (typeListMap.containsKey(type)) {
            var existingSlots = typeListMap.get(type);
            existingSlots.add(parkingSlot);
            typeListMap.put(type, existingSlots);
        } else typeListMap.get(type).add(parkingSlot);
    }
}
