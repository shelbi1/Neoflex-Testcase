package com.project.neoflex.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public double CalculateHolidayPay (int averageYearSalary, int amountOfHolidayDays) {
        double averageDailyEarnings = (averageYearSalary / 12) / 29.3;
        return averageDailyEarnings * amountOfHolidayDays;
    }
}
