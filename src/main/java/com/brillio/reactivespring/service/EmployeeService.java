package com.brillio.reactivespring.service;

import com.brillio.reactivespring.dto.EmployeeDto;
import com.brillio.reactivespring.repository.EmployeeRepository;
import com.brillio.reactivespring.utils.EmployeeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;


    public Flux<EmployeeDto> getEmployees(){
        return repository.findAll().map(EmployeeUtils::entityToDto);
    }

    public Mono<EmployeeDto> getEmployee(String id){
        return repository.findById(id).map(EmployeeUtils::entityToDto);
    }

    public Flux<EmployeeDto> getEmployeeInRange(double min,double max){
        return repository.findBySalaryBetween(Range.closed(min,max));
    }

    public Mono<EmployeeDto> saveEmployee(Mono<EmployeeDto> employeeDtoMono){
        System.out.println("service method called ...");
      return  employeeDtoMono.map(EmployeeUtils::dtoToEntity)
                .flatMap(repository::insert)
                .map(EmployeeUtils::entityToDto);
    }

    public Mono<EmployeeDto> updateEmployee(Mono<EmployeeDto> employeeDtoMono,String id){
       return repository.findById(id)
                .flatMap(p->employeeDtoMono.map(EmployeeUtils::dtoToEntity)
                .doOnNext(e->e.setId(id)))
                .flatMap(repository::save)
                .map(EmployeeUtils::entityToDto);

    }

    public Mono<Void> deleteEmployee(String id){
        return repository.deleteById(id);
    }
}
