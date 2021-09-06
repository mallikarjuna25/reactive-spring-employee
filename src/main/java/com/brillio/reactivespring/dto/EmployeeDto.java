package com.brillio.reactivespring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    @Id
    private String id;
    private String name;
    private String email;
    private double salary;
}
