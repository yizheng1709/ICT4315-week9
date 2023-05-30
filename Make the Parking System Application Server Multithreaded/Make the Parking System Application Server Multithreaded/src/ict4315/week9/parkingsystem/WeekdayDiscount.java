/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict4315.parkingsystem;

import ict4315.parkingsystem.ParkingEvent;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 *
 * 
 */
public class WeekdayDiscount extends ParkingChargeDecorator {

    private Double discountRate = 0.1;

    public WeekdayDiscount(ParkingChargeCalculator calc) {
        super(calc);
    }

    @Override
    public Double getDiscount(ParkingEvent parkingEvent) {
        if (parkingEvent.getTimeOut() != null) {
            if (isWeekday(parkingEvent.getTimeOut().getDayOfWeek())) {
                System.out.println("Weekday discount rate applied");
                return super.getDiscount(parkingEvent) + discountRate;
            }
        } else {
            if (isWeekday(parkingEvent.getTimeIn().getDayOfWeek())) {
                System.out.println("Weekday discount rate applied");
                return super.getDiscount(parkingEvent) + discountRate;
            }
        }
        System.out.println("Discount cannot be applied on weekend");
        return super.getDiscount(parkingEvent);
    }

    private boolean isWeekday(DayOfWeek day) {
        var dayOfWeek = day.getValue();
        if (1 <= dayOfWeek && dayOfWeek <= 5) {
            return true;
        }
        return false;
    }

}
