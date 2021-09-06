package com.brillio.reactivespring.repository;

import com.brillio.reactivespring.dto.EmployeeDto;
import com.brillio.reactivespring.entity.Employee;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EmployeeRepository extends ReactiveMongoRepository<Employee,String> {
    Flux<EmployeeDto> findBySalaryBetween(Range<Double> salaryRange);
}
