package com.project.neoflex.service;

import com.project.neoflex.IsDayOffClient;
import com.project.neoflex.IsDayOffService;
import com.project.neoflex.dto.CalculatorDTO;
import com.project.neoflex.exceptions.InvalidVacationDates;
import com.project.neoflex.response.CalculatorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.project.neoflex.exceptions.InvalidSalaryException;
import com.project.neoflex.exceptions.InvalidHoldayDaysException;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;


@Service
public class CalculatorService {

    @Autowired
    private IsDayOffService isDayOffService;

    public CalculatorResponse CalculateHolidayPay (CalculatorDTO calculatorDTO) {

        int averageYearSalary = calculatorDTO.getAverageYearSalary();
        int amountOfHolidayDays =  calculatorDTO.getAmountOfHolidayDays();
        LocalDate startDate = calculatorDTO.getStartDate();
        LocalDate endDate = calculatorDTO.getEndDate();

        try {
            ValidateInputParams(averageYearSalary, amountOfHolidayDays, startDate, endDate);
        }
        catch (InvalidSalaryException | InvalidHoldayDaysException | InvalidVacationDates e) {
            return CalculatorResponse.builder()
                    .message(e.getMessage())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        double holidayPay = 0;
        if (startDate == null) {
            holidayPay = Calculate(averageYearSalary, amountOfHolidayDays);
        }
        else {
            holidayPay = Calculate(averageYearSalary, startDate, endDate);
        }

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

    public double Calculate (int averageYearSalary, LocalDate startDate, LocalDate endDate) {

        int countDays = Period.between(startDate, endDate).getDays() + 1;
        int countDaysOff = 0;
        while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {

            ResponseEntity<String> entity = isDayOffService.isDayOff(startDate);
            if (Objects.equals(entity.getBody(), "1"))
                countDaysOff++;
            startDate = startDate.plusDays(1);
        }

        return Calculate(averageYearSalary, countDays - countDaysOff);
    }


    public void ValidateInputParams (int averageYearSalary, int amountOfHolidayDays, LocalDate startDate, LocalDate endDate) {
        if (averageYearSalary < 0)
            throw new InvalidSalaryException("Negative amount of salary");
        if (averageYearSalary == 0)
            throw new InvalidSalaryException("Zero salary");

        if ((amountOfHolidayDays <= 0 && startDate == null) || amountOfHolidayDays > 365)
            throw new InvalidHoldayDaysException("Incorrect number of vacation days");

        if (startDate != null && startDate.isAfter(endDate))
            throw new InvalidVacationDates("Start date is after end date");
    }
}
