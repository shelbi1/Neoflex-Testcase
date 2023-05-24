package com.project.neoflex;

import com.project.neoflex.dto.CalculatorDTO;
import com.project.neoflex.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class NeoflexApplicationTests {

    @Autowired
    private CalculatorService calculatorService;

    @Test
    public void calculateHolidayPayWithoutDatesTest() {

        // given
        CalculatorDTO calculatorDTO = new CalculatorDTO(480000, 4, null, null);
        double result = 0;

        // when
        if (calculatorService.CalculateHolidayPay(calculatorDTO).getHttpStatus() == HttpStatus.OK) {
            result = calculatorService.Calculate(480000, 4);
        }

        //then
        double expected = 5460.75;
        assertEquals(result, expected);
    }

    @Test
    public void calculateHolidayPayWithDatesTest() {

        LocalDate startDate = LocalDate.parse("2023-03-08");
        LocalDate endDate = LocalDate.parse("2023-03-14");

        // given
        CalculatorDTO calculatorDTO = new CalculatorDTO(480000, 0, startDate, endDate);
        double result = 0;

        // when
        if (calculatorService.CalculateHolidayPay(calculatorDTO).getHttpStatus() == HttpStatus.OK) {
            result = calculatorService.Calculate(480000, startDate, endDate);
        }

        //then
        double expected = 5460.75;
        assertEquals(result, expected);
    }

    @Test
    public void catchCommonExceptions() {

        // ---- Incorrect amount of average salary ----
        // given
        CalculatorDTO calculatorDTO1 = new CalculatorDTO(-1, 4, null, null);
        boolean result1 = false;

        // when
        if (calculatorService.CalculateHolidayPay(calculatorDTO1).getHttpStatus() == HttpStatus.BAD_REQUEST) {
            result1 = true;
        }

        // then
        boolean expected1 = true;
        assertEquals(result1, expected1);

        // ---- Incorrect number of vacation days ----
        // given
        CalculatorDTO calculatorDTO2 = new CalculatorDTO(480000, 0, null, null);
        boolean result2 = false;

        // when
        if (calculatorService.CalculateHolidayPay(calculatorDTO2).getHttpStatus() == HttpStatus.BAD_REQUEST) {
            result2 = true;
        }

        // then
        boolean expected2 = true;
        assertEquals(result2, expected2);

        // ---- Start date is after end date ----
        // given
        CalculatorDTO calculatorDTO = new CalculatorDTO(480000, 0,
                LocalDate.parse("2023-04-04"), LocalDate.parse("2023-01-01"));
        boolean result3 = false;

        // when
        if (calculatorService.CalculateHolidayPay(calculatorDTO2).getHttpStatus() == HttpStatus.BAD_REQUEST) {
            result3 = true;
        }

        // then
        boolean expected3 = true;
        assertEquals(result3, expected3);
    }

}
