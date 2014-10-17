package vn.com.tma.training.pi;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;

import vn.com.tma.training.pi.algorithm.concreteclass.LeibnizAlgorithm;
import vn.com.tma.training.pi.algorithm.concreteclass.LeibnizAlgorithmDecorator;
import vn.com.tma.training.pi.calculator.MultiThreadPiCalculator;

public class MultiThreadPiCalculatorTest {

	MultiThreadPiCalculator testCalculator;

	@Before
	public void setUp() throws Exception {
		testCalculator = new MultiThreadPiCalculator();
		testCalculator.calculate(8, new BigDecimal("1e6"),
				new BigDecimal("1e4"), new LeibnizAlgorithmDecorator(
						new LeibnizAlgorithm()));
	}

	@Test
	public void testSetNumberOfThreadNormal() {
		testCalculator.setNumberOfThread(4);
		assertEquals(4, testCalculator.getNumberOfThread());
		testCalculator.setNumberOfThread(0);
		assertEquals(Runtime.getRuntime().availableProcessors(),
				testCalculator.getNumberOfThread());
	}

	@Test(expected = ArithmeticException.class)
	public void testSetNumberOfThreadOutOfRange() {

		testCalculator.setNumberOfThread(-10);
		assertEquals(Runtime.getRuntime().availableProcessors(),
				testCalculator.getNumberOfThread());
		testCalculator.setNumberOfThread(101);
		assertEquals(Runtime.getRuntime().availableProcessors(),
				testCalculator.getNumberOfThread());
	}

	@Test(expected = NumberFormatException.class)
	public void testSetNumberOfThreadString() {
		testCalculator.setNumberOfThread(Integer.parseInt("a"));
		assertEquals(4, testCalculator.getNumberOfThread());
		testCalculator.setNumberOfThread(Integer.parseInt(null));
		assertEquals(4, testCalculator.getNumberOfThread());
	}

	@Test
	public void testSetNumberOfCalculationNormal() {
		testCalculator.setNumberOfCalculation(new BigDecimal(1E9));
		assertEquals(1E9, testCalculator.getNumberOfCalculation(), 0.1);

		testCalculator.setNumberOfCalculation(new BigDecimal(0));
		assertEquals(0, testCalculator.getNumberOfCalculation(), 0.1);
	}

	@Test(expected = ArithmeticException.class)
	public void testSetNumberOfCalculationOutOfRange() {
		testCalculator.setNumberOfCalculation(new BigDecimal("9E309"));
		assertEquals(1E9, testCalculator.getNumberOfCalculation(), 0.1);

		testCalculator.setNumberOfCalculation(new BigDecimal(-1));
		assertEquals(0, testCalculator.getNumberOfCalculation(), 0.1);
	}

	@Test
	public void testSetCalcEachThreadNormal() {
		testCalculator.setNumberOfCalculation(new BigDecimal(1E9));
		testCalculator.setCalcEachThread(new BigDecimal(1e7));
		assertEquals(1E7, testCalculator.getCalcEachThread(), 0.1);

		testCalculator.setNumberOfCalculation(new BigDecimal(0));
		assertEquals(0, testCalculator.getNumberOfCalculation(), 0.1);
	}

	@Test(expected = ArithmeticException.class)
	public void testSetnEachThreadOutOfRange() {
		testCalculator.setCalcEachThread(new BigDecimal("9E309"));
		assertEquals(1E9, testCalculator.getNumberOfCalculation(), 0.1);

		testCalculator.setNumberOfCalculation(new BigDecimal(-1));
		assertEquals(0, testCalculator.getNumberOfCalculation(), 0.1);
	}

	@Test
	public void testCalculate() throws InterruptedException, ExecutionException {

		assertEquals(Math.PI, testCalculator.calculate(0, new BigDecimal(1e9),
				new BigDecimal(1e8), new LeibnizAlgorithm()), 0.00000001);
	}
}
