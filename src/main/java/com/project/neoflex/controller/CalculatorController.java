package com.project.neoflex.controller;

import com.project.neoflex.response.CalculatorResponse;
import com.project.neoflex.service.CalculatorService;
import com.project.neoflex.dto.CalculatorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorService;

    @GetMapping(value="/calculate")
    public ResponseEntity<String> calculate (@RequestBody CalculatorDTO calculatorDTO) {

        CalculatorResponse response = calculatorService.CalculateHolidayPay(calculatorDTO);

        return new ResponseEntity<> (response.getMessage(), response.getHttpStatus());
    }


}
