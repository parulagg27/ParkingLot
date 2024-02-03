package parkinglot.design.model;

import lombok.Getter;
import parkinglot.design.entity.ParkingSlot;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ParkingSlotPOJO {

    List<ParkingSlot> emptySlots = new ArrayList<>();
    List<ParkingSlot> occupiedSlots = new ArrayList<>();

    public void addEmptySlots(List<ParkingSlot> parkingSlots) {
        emptySlots.addAll(parkingSlots);
    }
}
