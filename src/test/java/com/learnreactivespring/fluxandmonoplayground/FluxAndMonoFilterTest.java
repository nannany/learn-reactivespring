package com.learnreactivespring.fluxandmonoplayground;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoFilterTest {

	List<String> names = Arrays.asList("adam", "anna", "jack", "jenny");

	@Test
	public void filterTest() {
		Flux<String> namesFlux = Flux.fromIterable(names) // adam,anna,jack,jenny
				.filter(s -> s.startsWith("a")).log(); // adam,anna

		StepVerifier.create(namesFlux).expectNext("adam", "anna").verifyComplete();
	}

	@Test
	public void filterTestLength() {
		Flux<String> namesFlux = Flux.fromIterable(names) // adam,anna,jack,jenny
				.filter(s -> s.length() > 4).log(); // 

		StepVerifier.create(namesFlux).expectNext("jenny").verifyComplete();
	}
}
