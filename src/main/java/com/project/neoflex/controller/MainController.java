package com.project.neoflex.controller;

import com.project.neoflex.service.CalculatorService;
import com.project.neoflex.dto.CalculatorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {

    @Autowired
    private CalculatorService calculatorService;

    ///@RequestParam(name = "start") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate vacationStartDate,
    //@RequestParam(name = "end") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate vacationEndDate
    @GetMapping(value="/calculate")
    public ResponseEntity<Double> calculate (@RequestBody CalculatorDTO calculatorDTO) {

        double holidayPay = calculatorService.
                CalculateHolidayPay(calculatorDTO.getAverageYearSalary(),
                                    calculatorDTO.getAmountOfHolidayDays());

        return ResponseEntity.ok(holidayPay);
    }
}
