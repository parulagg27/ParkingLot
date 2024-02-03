package parkinglot.design.entity;

import lombok.Getter;
import parkinglot.design.constants.VehicleType;

@Getter
public class Vehicle {

    String registrationNumber;
    VehicleType type;
}
