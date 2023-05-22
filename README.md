# Neoflex Project

Hey! ♡

This project calculates the amount of holiday pay for an employee.

### First variant:
* __The input__ of the program is:
  * the average salary for the year 
  * the number of vacation days
  
* __The output__ is the amount of holiday pay that will come to the employee.

Here you can see Postman request:

![result without dates](https://github.com/shelbi1/Neoflex-Testcase/assets/31365702/72098135-b7de-49b0-a759-007373437ff5)


The formula is pretty clear, you can check it out, it's down below: 

![formula](https://github.com/shelbi1/Neoflex-Testcase/assets/31365702/86c3c32b-54c9-420d-8e6d-545344e0e084)

 * We have average salary for the year, so we need to devide it to 12 (mounths) and 29.3 (it's the avarage number of days in a month), and in this way we get the __average daily earnings__. 
 * Then we multiply the average daily earnings by the number of holiday days to get __holiday pay__.

---

### Second variant:

* __The input__ of the program is:

  * the average salary for the year 
  * the start date of the vacation 
  * the end date of the vacation 

* __The output__ is the same as in the previous variant

Here you can see another variables to be send to ```/calculate```:

![result with dates](https://github.com/shelbi1/Neoflex-Testcase/assets/31365702/81b5b9da-6310-4097-8c15-ca9a0b0087d3)


So, our employee wants to take a vacation from March 8 to 14. 
What do we have? 7 days, including 8 and 14, but we have to subtract March 8 (which is a holiday) and March 11, 12, because those are weekends.

And ___finnaly___ we have 7 - 3 = 4 vacation days to be paid for.  

![Март 2023](https://github.com/shelbi1/Neoflex-Testcase/assets/31365702/9ae723e2-7b6d-4763-9f32-863cc7946d2b)


Here we use the Feign Client as a public API request to find out if the day is off or not.

![isDayOffClient](https://github.com/shelbi1/Neoflex-Testcase/assets/31365702/fe1e78ce-b68c-4ee5-9cc1-1e654f784e67)

---

### Exceptions

I also took care of catching possible exceptions, including incorrect input parameters.
In those cases you will have HTTP status = _BAD_REQUEST_ and a message of the catched exception. 

For example, negative amount of the salary or zero number of vacation days. Also if the start date of the vacation would be after the end date, you will have appropriate message. 
You can find the description of exceptions in ```CalculatorService.java → ValidateInputParams(...)```

![start date after end date](https://github.com/shelbi1/Neoflex-Testcase/assets/31365702/dd5c0a92-7837-4c11-8f58-e1ba63cadffd)

---

### Tests
