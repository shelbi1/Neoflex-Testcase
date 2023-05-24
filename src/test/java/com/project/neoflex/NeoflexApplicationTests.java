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

        // when
        double result = calculatorService.Calculate(480000, 4);

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

        // when
        double result = calculatorService.Calculate(480000, startDate, endDate);

        //then
        double expected = 5460.75;
        assertEquals(result, expected);
    }

    @Test
    public void catchCommonExceptions() {

        HttpStatus errorHttpCode = HttpStatus.BAD_REQUEST;

        // ---- Incorrect amount of average salary ----
        // given
        CalculatorDTO calculatorDTO1 = new CalculatorDTO(-1, 4, null, null);

        // when
        HttpStatus result1 = calculatorService.CalculateHolidayPay(calculatorDTO1).getHttpStatus();

        // then
        assertEquals(result1, errorHttpCode);

        // ---- Incorrect number of vacation days ----
        // given
        CalculatorDTO calculatorDTO2 = new CalculatorDTO(480000, 0, null, null);

        // when
        HttpStatus result2 = calculatorService.CalculateHolidayPay(calculatorDTO2).getHttpStatus();

        // then
        assertEquals(result2, errorHttpCode);

        // ---- Start date is after end date ----
        // given
        CalculatorDTO calculatorDTO3 = new CalculatorDTO(480000, 0,
                LocalDate.parse("2023-04-04"), LocalDate.parse("2023-01-01"));

        // when
        HttpStatus result3 = calculatorService.CalculateHolidayPay(calculatorDTO3).getHttpStatus();

        // then
        assertEquals(result3, errorHttpCode);
    }

}
