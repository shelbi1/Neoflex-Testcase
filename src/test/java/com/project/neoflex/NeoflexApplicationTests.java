package com.project.neoflex;

import com.project.neoflex.dto.CalculatorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.project.neoflex.service.CalculatorService;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class NeoflexApplicationTests {

	@Autowired
	private CalculatorService calculatorService;

	@Test
	public void CalculateHolidayPayTest() {

		CalculatorDTO calculatorDTO = new CalculatorDTO(480000, 4);
		double result = 0;
		if (calculatorService.CalculateHolidayPay(calculatorDTO).getHttpStatus() == HttpStatus.OK) {
			result = calculatorService.Calculate(480000, 4);
		}
		double expected = 5460.75;
		assertEquals(result, expected);
	}



}
