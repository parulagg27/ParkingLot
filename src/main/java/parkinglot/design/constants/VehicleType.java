package parkinglot.design.constants;

import lombok.Getter;

@Getter
public enum VehicleType {
    CAR(20, 10),
    TWO_WHEELER(10, 5),
    TRUCK(30, 10);

    private final Integer baseCharge;
    private final Integer hourlyCharge;

    VehicleType(Integer baseCharge, Integer hourlyCharge) {
        this.baseCharge = baseCharge;
        this.hourlyCharge = hourlyCharge;
    }
}
