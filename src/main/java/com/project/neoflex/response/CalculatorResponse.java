package com.project.neoflex.response;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
@Builder
public class CalculatorResponse {
    private String message;
    private HttpStatus httpStatus;
}