package com.brillio.reactivespring;

import com.brillio.reactivespring.controller.EmployeeController;
import com.brillio.reactivespring.dto.EmployeeDto;
import com.brillio.reactivespring.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import  static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(EmployeeController.class)
class ReactiveSpringApplicationTests {

	@Autowired
	private WebTestClient webTestClient;
	@MockBean
	private EmployeeService service;

	@Test
	public void addEmployeeTest(){
		Mono<EmployeeDto> employeeDtoMono= Mono.just(new EmployeeDto("101","malli","malli@gmail.com",30000));
		when(service.saveEmployee(employeeDtoMono)).thenReturn(employeeDtoMono);

		webTestClient.post().uri("/employees")
				.body(Mono.just(employeeDtoMono), EmployeeDto.class)
				.exchange()
				.expectStatus().isOk();//200

	}

	@Test
	public void getEmployeesTest(){
		Flux<EmployeeDto> employeeDtoFlux=Flux.just(new EmployeeDto("102","swarup","swarup@gmail.com",50000),
				new EmployeeDto("103","srinu","srinu@gmail.com",70000));
		when(service.getEmployees()).thenReturn(employeeDtoFlux);

		Flux<EmployeeDto> responseBody = webTestClient.get().uri("/employees")
				.exchange()
				.expectStatus().isOk()
				.returnResult(EmployeeDto.class)
				.getResponseBody();

		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNext(new EmployeeDto("102","swarup","swarup@gmail.com",50000))
				.expectNext(new EmployeeDto("103","srinu","srinu@gmail.com",70000))
				.verifyComplete();

	}

	@Test
	public void getEmployeeTest(){
		Mono<EmployeeDto> employeeDtoMono=Mono.just(new EmployeeDto("102","swarup","swarup@gmail.com",50000));
		when(service.getEmployee(any())).thenReturn(employeeDtoMono);

		Flux<EmployeeDto> responseBody = webTestClient.get().uri("/employees/102")
				.exchange()
				.expectStatus().isOk()
				.returnResult(EmployeeDto.class)
				.getResponseBody();

		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNextMatches(p->p.getName().equals("swarup"))
				.verifyComplete();
	}


	@Test
	public void updateEmployeeTest(){
		Mono<EmployeeDto> employeeDtoMono=Mono.just(new EmployeeDto("102","swarup","swarupkumar@gmail.com",80000));
		when(service.updateEmployee(employeeDtoMono,"102")).thenReturn(employeeDtoMono);

		webTestClient.put().uri("/employees/update/102")
				.body(Mono.just(employeeDtoMono),EmployeeDto.class)
				.exchange()
				.expectStatus().isOk();//200
	}

	@Test
	public void deleteEmployeeTest(){
		given(service.deleteEmployee(any())).willReturn(Mono.empty());
		webTestClient.delete().uri("/employees/delete/102")
				.exchange()
				.expectStatus().isOk();//200
	}


}
