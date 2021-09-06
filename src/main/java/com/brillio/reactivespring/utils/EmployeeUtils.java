package com.brillio.reactivespring.utils;

import com.brillio.reactivespring.dto.EmployeeDto;
import com.brillio.reactivespring.entity.Employee;
import org.springframework.beans.BeanUtils;

public class EmployeeUtils {

    public static EmployeeDto entityToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        BeanUtils.copyProperties(employee, employeeDto);
        return employeeDto;
    }

    public static Employee dtoToEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDto, employee);
        return employee;
    }
}
