package com.brillio.reactivespring.controller;

import com.brillio.reactivespring.dto.EmployeeDto;
import com.brillio.reactivespring.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping
    public Flux<EmployeeDto> getEmployees(){
        return service.getEmployees();
    }

    @GetMapping("/{id}")
    public Mono<EmployeeDto> getemployee(@PathVariable String id){
        return service.getEmployee(id);
    }

    @GetMapping("/employee-range")
    public Flux<EmployeeDto> getEmployeeBetweenRange(@RequestParam("min") double min, @RequestParam("max")double max){
        return service.getEmployeeInRange(min,max);
    }

    @PostMapping
    public Mono<EmployeeDto> saveEmployee(@RequestBody Mono<EmployeeDto> employeeDtoMono){
        System.out.println("controller method called ...");
        return service.saveEmployee(employeeDtoMono);
    }

    @PutMapping("/update/{id}")
    public Mono<EmployeeDto> updateEmployee(@RequestBody Mono<EmployeeDto> employeeDtoMono,@PathVariable String id){
        return service.updateEmployee(employeeDtoMono,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteEmployee(@PathVariable String id){
        return service.deleteEmployee(id);
    }



}
