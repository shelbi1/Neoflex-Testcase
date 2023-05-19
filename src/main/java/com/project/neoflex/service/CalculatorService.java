package com.project.neoflex.service;

import com.project.neoflex.dto.CalculatorDTO;
import com.project.neoflex.response.CalculatorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.project.neoflex.exceptions.InvalidSalaryException;
import com.project.neoflex.exceptions.InvalidHoldayDaysException;


@Service
public class CalculatorService {

    public CalculatorResponse CalculateHolidayPay (CalculatorDTO calculatorDTO) {

        int averageYearSalary = calculatorDTO.getAverageYearSalary();
        int amountOfHolidayDays =  calculatorDTO.getAmountOfHolidayDays();

        try {
            ValidateInputParams(averageYearSalary, amountOfHolidayDays);
        }
        catch (InvalidSalaryException | InvalidHoldayDaysException e) {
            return CalculatorResponse.builder()
                    .message(e.getMessage())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        double holidayPay = Calculate(averageYearSalary, amountOfHolidayDays);

        return CalculatorResponse.builder()
                .message("Your holiday pay: " + holidayPay)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public double Calculate (int averageYearSalary, int amountOfHolidayDays) {
        double averageDailyEarnings = (averageYearSalary / 12) / 29.3;
        double holidayPay = averageDailyEarnings * amountOfHolidayDays;
        holidayPay = Math.round(holidayPay * 100.0) / 100.0;
        return holidayPay;
    }


    public void ValidateInputParams (int averageYearSalary, int amountOfHolidayDays) {
        if (averageYearSalary < 0)
            throw new InvalidSalaryException("Negative amount of salary");
        if (averageYearSalary == 0)
            throw new InvalidSalaryException("Zero salary");

        if (amountOfHolidayDays <= 0 || amountOfHolidayDays > 365)
            throw new InvalidHoldayDaysException("Incorrect number of vacation days");
    }
}
