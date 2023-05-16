package com.project.neoflex.dto;

public class CalculatorDTO {

    private int averageYearSalary;
    private int amountOfHolidayDays;

    public CalculatorDTO (int averageYearSalary, int amountOfHolidayDays) {
        this.averageYearSalary = averageYearSalary;
        this.amountOfHolidayDays = amountOfHolidayDays;
    }

    public int getAverageYearSalary () {
        return averageYearSalary;
    }

    public int getAmountOfHolidayDays () {
        return amountOfHolidayDays;
    }

    public void setAverageYearSalary (int averageYearSalary) {
        this.averageYearSalary = averageYearSalary;
    }

    public void setAmountOfHolidayDays (int amountOfHolidayDays) {
        this.amountOfHolidayDays = amountOfHolidayDays;
    }
}
