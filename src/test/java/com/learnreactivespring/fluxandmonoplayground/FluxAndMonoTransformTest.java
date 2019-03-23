package com.learnreactivespring.fluxandmonoplayground;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

public class FluxAndMonoTransformTest {

	List<String> names = Arrays.asList("adam", "anna", "jack", "jenny");

	@Test
	public void transformUsingMap() {
		Flux<String> namesFlux = Flux.fromIterable(names).map(s -> s.toUpperCase()) // ADAM,ANNA,JACK,JENNY
				.log();

		StepVerifier.create(namesFlux).expectNext("ADAM", "ANNA", "JACK", "JENNY").verifyComplete();
	}

	@Test
	public void transformUsingMapLength() {
		Flux<Integer> namesFlux = Flux.fromIterable(names).map(s -> s.length()) // ADAM,ANNA,JACK,JENNY
				.repeat(1).log();

		StepVerifier.create(namesFlux).expectNext(4, 4, 4, 5, 4, 4, 4, 5).verifyComplete();
	}

	@Test
	public void transformUsingMapFilter() {
		Flux<String> namesFlux = Flux.fromIterable(names).filter(s -> s.length() > 4).map(s -> s.toUpperCase()) // JENNY
				.log();

		StepVerifier.create(namesFlux).expectNext("JENNY").verifyComplete();
	}

	@Test
	public void transformUsingFlatMap() {
		Flux<String> names = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F")).flatMap(s -> {
			return Flux.fromIterable(convertToList(s));
		}).log();

		StepVerifier.create(names).expectNextCount(12).verifyComplete();
	}

	private List<String> convertToList(String s) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Arrays.asList(s, "newValue");
	}

	@Test
	public void transformUsingFlatMapUsingParallel() {
		Flux<String> names = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F")).window(2) // Flux<Flux<String>
																										// ->
																										// (A,B),(C,D),(E,F)
				.flatMap((s) -> s.map(this::convertToList).subscribeOn(Schedulers.parallel()))
				.flatMap(s -> Flux.fromIterable(s)).log();

		StepVerifier.create(names).expectNextCount(12).verifyComplete();
	}

	@Test
	public void transformUsingFlatMapUsingParallelMaintainOrder() {
		Flux<String> names = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F")).window(2) // Flux<Flux<String>
																										// ->
																										// (A,B),(C,D),(E,F)
				.concatMap((s) -> s.map(this::convertToList).subscribeOn(Schedulers.parallel()))
				.flatMap(s -> Flux.fromIterable(s)).log();

		StepVerifier.create(names).expectNextCount(12).verifyComplete();
	}
}
