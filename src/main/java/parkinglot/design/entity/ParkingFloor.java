package parkinglot.design.entity;

import lombok.Builder;
import lombok.Getter;
import parkinglot.design.constants.VehicleType;
import parkinglot.design.model.ParkingSlotPOJO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@Builder
public class ParkingFloor {

    Integer floorNumber;
    Integer buildingId;
    Map<VehicleType, ParkingSlotPOJO> vehicleTypeToSlotStatusMapping;

    public List<ParkingSlot> getEmptySlotsForType(VehicleType vehicleType) {
        return this.vehicleTypeToSlotStatusMapping.get(vehicleType).getEmptySlots();
    }

    public List<ParkingSlot> getOccupiedSlotsForType(VehicleType vehicleType) {
        return this.vehicleTypeToSlotStatusMapping.get(vehicleType).getOccupiedSlots();
    }

    public boolean isEmptySlotAvailable(VehicleType vehicleType) {
        return !this.getEmptySlotsForType(vehicleType).isEmpty();
    }

    public void bookEmptySlot(VehicleType type, ParkingSlot firstEmptySlot) {
        System.out.println("slots before parking: " + this.getVehicleTypeToSlotStatusMapping().get(type));
        getEmptySlotsForType(type).remove(firstEmptySlot);
        getOccupiedSlotsForType(type).add(firstEmptySlot);
        System.out.println("slots after parking: " + this.getVehicleTypeToSlotStatusMapping().get(type));
    }

    public void freeUpOccupiedSlot(VehicleType type, ParkingSlot slotToBeFreed) {
        System.out.println("slots before freeing occupied space: " + this.getVehicleTypeToSlotStatusMapping().get(type));
        this.getOccupiedSlotsForType(type).remove(slotToBeFreed);
        this.getEmptySlotsForType(type).add(slotToBeFreed);
        System.out.println("slots after freeing occupied space: " + this.getVehicleTypeToSlotStatusMapping().get(type));
    }
}
