package parkinglot.design.service;

import parkinglot.design.constants.VehicleType;
import parkinglot.design.entity.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

public class ParkingService {

    Dashboard dashboard = Dashboard.getInstance();

    private Optional<ParkingSlot> findFirstEmptySlot(VehicleType vehicleType, ParkingLot parkingLot) {
        return parkingLot.getParkingFloors().values()
                .stream()
                .filter(parkingFloor -> parkingFloor.isEmptySlotAvailable(vehicleType))
                .findFirst()
                .map(parkingFloor -> parkingFloor.getEmptySlotsForType(vehicleType).get(0));
    }

    public Ticket park(Vehicle vehicle, ParkingLot parkingLot) {
        var firstEmptySlot = findFirstEmptySlot(vehicle.getType(), parkingLot);
        if (firstEmptySlot.isEmpty())
            throw new RuntimeException("No empty slots available for type: " + vehicle.getType());
        System.out.println("empty slot found on floor: " + firstEmptySlot.get().getFloorId() +
                " for vehicle id: " + vehicle.getRegistrationNumber());
        int floorId = firstEmptySlot.get().getFloorId();
        var floor = parkingLot.getParkingFloors().get(floorId);
        floor.bookEmptySlot(vehicle.getType(), firstEmptySlot.get());
        dashboard.updateDashBoardAfterParking(vehicle.getType(), firstEmptySlot.get());
        return new Ticket(vehicle, firstEmptySlot.get());
    }

    public long unParkAndGetFinalCharges(Vehicle vehicle, Ticket ticket, ParkingLot parkingLot) {
        var slotToBeFreed = ticket.getParkingSlot();
        int floorId = slotToBeFreed.getFloorId();
        var floor = parkingLot.getParkingFloors().get(floorId);
        floor.freeUpOccupiedSlot(vehicle.getType(), slotToBeFreed);
        dashboard.updateDashBoardAfterUnPark(vehicle.getType(), slotToBeFreed);
        var timeElapsed = getTimeElapsed(ticket);
        return getFinalCharges(vehicle, timeElapsed);
    }

    private static long getFinalCharges(Vehicle vehicle, long timeElapsed) {
        return vehicle.getType().getBaseCharge() + vehicle.getType().getHourlyCharge() * timeElapsed;
    }

    private static long getTimeElapsed(Ticket ticket) {
        var outTime = LocalDateTime.now();
        var outTimeEpochSecond = outTime.toEpochSecond(ZoneOffset.UTC);
        var inTimeEpochSecond = ticket.getInTime().toEpochSecond(ZoneOffset.UTC);
        return (outTimeEpochSecond - inTimeEpochSecond) / 3600;
    }
}
