package com.learnreactivespring.fluxandmonoplayground;

import java.time.Duration;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoWithTimeTest {

	@Test
	public void infiniteSequence() throws InterruptedException {
		Flux<Long> infiniteFlux = Flux.interval(Duration.ofMillis(200)).log();

		infiniteFlux.subscribe((element) -> System.out.println("Value is:" + element));

		Thread.sleep(3000);
	}

	@Test
	public void infiniteSequenceTest() throws InterruptedException {
		Flux<Long> infiniteFlux = Flux.interval(Duration.ofMillis(200)).take(3).log();

		StepVerifier.create(infiniteFlux).expectSubscription().expectNext(0L, 1L, 2L).verifyComplete();
	}

	@Test
	public void infiniteSequenceTestMap() throws InterruptedException {
		Flux<Integer> infiniteFlux = Flux.interval(Duration.ofMillis(200)).map(l -> new Integer(l.intValue())).take(3)
				.log();

		StepVerifier.create(infiniteFlux).expectSubscription().expectNext(0, 1, 2).verifyComplete();
	}

	@Test
	public void infiniteSequenceTestMapWithDelay() throws InterruptedException {
		Flux<Integer> infiniteFlux = Flux.interval(Duration.ofMillis(200))
				.delayElements(Duration.ofSeconds(1))
				.map(l -> new Integer(l.intValue()))
				.take(3)
				.log();

		StepVerifier.create(infiniteFlux).expectSubscription().expectNext(0, 1, 2).verifyComplete();
	}
}
