package com.project.neoflex.service;

import com.project.neoflex.IsDayOffService;
import com.project.neoflex.dto.CalculatorDTO;
import com.project.neoflex.exception.CommonException;
import com.project.neoflex.response.CalculatorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

@Service
public class CalculatorService {

    @Autowired
    private IsDayOffService isDayOffService;

    public CalculatorResponse CalculateHolidayPay(CalculatorDTO calculatorDTO) {

        int averageYearSalary = calculatorDTO.getAverageYearSalary();
        int amountOfHolidayDays = calculatorDTO.getAmountOfHolidayDays();
        LocalDate startDate = calculatorDTO.getStartDate();
        LocalDate endDate = calculatorDTO.getEndDate();

        try {
            ValidateInputParams(averageYearSalary, amountOfHolidayDays, startDate, endDate);

            double holidayPay = startDate == null ?
                    Calculate(averageYearSalary, amountOfHolidayDays) :
                    Calculate(averageYearSalary, startDate, endDate);

            return CalculatorResponse.builder()
                    .message("Your holiday pay: " + holidayPay)
                    .httpStatus(HttpStatus.OK)
                    .build();

        } catch (CommonException e) {
            return CalculatorResponse.builder()
                    .message(e.getMessage())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    public double Calculate(int averageYearSalary, int amountOfHolidayDays) {

        double averageDailyEarnings = (averageYearSalary / 12) / 29.3;
        double holidayPay = averageDailyEarnings * amountOfHolidayDays;
        double holidayPayRounded = Math.round(holidayPay * 100.0) / 100.0;
        return holidayPayRounded;
    }

    /**
     * This method uses Feign client to calculate holiday pay, by counting the number of vacation days.
     * <br>
     * <br> Vacation days = days between start and end date — holidays/weekends.
     *
     * @param averageYearSalary
     */
    public double Calculate(int averageYearSalary, LocalDate startDate, LocalDate endDate) {

        int countDays = Period.between(startDate, endDate).getDays() + 1;
        int countDaysOff = 0;

        while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
            ResponseEntity<String> entity = isDayOffService.isDayOff(startDate);
            if (Objects.equals(entity.getBody(), "1")) {
                countDaysOff++;
            }
            startDate = startDate.plusDays(1);
        }

        return Calculate(averageYearSalary, countDays - countDaysOff);
    }

    public void ValidateInputParams(int averageYearSalary, int amountOfHolidayDays, LocalDate startDate, LocalDate endDate) {

        if (averageYearSalary <= 0) {
            throw new CommonException("Incorrect amount of average salary");
        }
        if ((amountOfHolidayDays <= 0 && startDate == null) || amountOfHolidayDays > 365) {
            throw new CommonException("Incorrect number of vacation days");
        }
        if (startDate != null && startDate.isAfter(endDate)) {
            throw new CommonException("Start date is after end date");
        }
    }
}
