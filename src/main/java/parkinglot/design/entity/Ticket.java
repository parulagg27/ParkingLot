package parkinglot.design.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Ticket {

    String ticketNumber;
    String vehicleRegistrationNumber;
    ParkingSlot parkingSlot;
    LocalDateTime inTime;

    public Ticket(Vehicle vehicle, ParkingSlot parkingSlot) {
        this.ticketNumber = generateTicketNumber(parkingSlot.getBuildingId(), parkingSlot.getFloorId(), parkingSlot.getId());
        this.vehicleRegistrationNumber = vehicle.getRegistrationNumber();
        this.parkingSlot = parkingSlot;
        this.inTime = LocalDateTime.now();
    }

    private String generateTicketNumber(Integer buildingId, Integer floorId, Integer slotId) {
        return buildingId + "_" + floorId + "_" + slotId;
    }
}
