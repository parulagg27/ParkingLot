package parkinglot.design.entity;

import lombok.Builder;
import lombok.Getter;
import parkinglot.design.constants.VehicleType;

@Getter
@Builder
public class ParkingSlot {

    Integer id;
    VehicleType type;
    Integer floorId;
    Integer buildingId;
}
