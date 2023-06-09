package com.project.neoflex.dto;

import java.time.LocalDate;

public class CalculatorDTO {

    private int averageYearSalary;
    private int amountOfHolidayDays;
    private LocalDate startDate;
    private LocalDate endDate;

    public CalculatorDTO(int averageYearSalary, int amountOfHolidayDays, LocalDate startDate, LocalDate endDate) {
        setAverageYearSalary(averageYearSalary);
        setAmountOfHolidayDays(amountOfHolidayDays);
        setStartDate(startDate);
        setEndDate(endDate);
    }

    public int getAverageYearSalary() {
        return averageYearSalary;
    }

    public int getAmountOfHolidayDays() {
        return amountOfHolidayDays;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setAverageYearSalary(int averageYearSalary) {
        this.averageYearSalary = averageYearSalary;
    }

    public void setAmountOfHolidayDays(int amountOfHolidayDays) {
        this.amountOfHolidayDays = amountOfHolidayDays;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

}
