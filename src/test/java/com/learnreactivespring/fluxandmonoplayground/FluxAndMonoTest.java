package com.learnreactivespring.fluxandmonoplayground;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {
	@Test
	public void fluxTest() {

		Flux<String> stringFlux = Flux.just("Spring", "Spring boot", "Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("Exception Occurred")))
				.concatWith(Flux.just("After Error")) // ‚±‚Ìƒf[ƒ^‚Í‘—‚ç‚ê‚È‚¢
				.log();

		stringFlux.subscribe(System.out::println, (e) -> System.err.println(e), () -> System.out.println("completed"));
	}

	@Test
	public void fluxTestElementsWithoutError() {
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive SPring").log();

		StepVerifier.create(stringFlux)
		.expectNext("Spring")
		.expectNext("Spring Boot")
		.expectNext("Reactive SPring")
		.verifyComplete();
	}

	@Test
	public void fluxTestElementsWithError() {
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive SPring")
				.concatWith(Flux.error(new RuntimeException("Exception Occurred")))
				.log();

		StepVerifier.create(stringFlux)
		.expectNext("Spring")
		.expectNext("Spring Boot")
		.expectNext("Reactive SPring")
//		.expectError(RuntimeException.class)
		.expectErrorMessage("Exception Occurred")
		.verify();
	}
	@Test
	public void fluxTestElementsCountWithError() {
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive SPring")
				.concatWith(Flux.error(new RuntimeException("Exception Occurred")))
				.log();

		StepVerifier.create(stringFlux)
		.expectNextCount(3)
//		.expectError(RuntimeException.class)
		.expectErrorMessage("Exception Occurred")
		.verify();
	}
	@Test
	public void fluxTestElementsWithError2() {
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive SPring")
				.concatWith(Flux.error(new RuntimeException("Exception Occurred")))
				.log();

		StepVerifier.create(stringFlux)
		.expectNext("Spring","Spring Boot","Reactive SPring")
//		.expectError(RuntimeException.class)
		.expectErrorMessage("Exception Occurred")
		.verify();
	}
}
