package com.tma.gbst.pi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.InputMismatchException;

import org.junit.Before;
import org.junit.Test;

import com.tma.gbst.pi.algorithm.concreteclass.LeibnizParameter;
import com.tma.gbst.pi.calculator.CalculatorParameter;

public class LeibnizParameterTest {

	LeibnizParameter testParameter;

	@Before
	public void setUp() throws Exception {
		testParameter = new LeibnizParameter();
	}

	@Test
	public void testIstypeTrue() {
		assertTrue(testParameter.istype(LeibnizParameter.TYPE));
	}

	@Test
	public void testIstypeFalse() {
		assertTrue(!testParameter.istype(CalculatorParameter.TYPE));
	}

	@Test
	public void testSetGetParameters() {
		testParameter.setParameters(10.0, 15.0);
		Object[] storedParameter = testParameter.getParameters();
		assertEquals((Double) storedParameter[0], 10.0, 0.1);
		assertEquals((Double) storedParameter[1], 15.0, 0.1);
	}

	@Test(expected = NullPointerException.class)
	public void testSetGetParametersNullException() {
	
		testParameter.setParameters(null, null);
		Object[] storedParameter = testParameter.getParameters();
		assertEquals((Double) storedParameter[0], 10.0, 0.1);
		assertEquals((Double) storedParameter[1], 15.0, 0.1);
	}

	@Test(expected = InputMismatchException.class)
	public void testSetGetParametersInputException() {
		testParameter.setParameters(new String("asd"), new Long(10));
		Object[] storedParameter = testParameter.getParameters();
		assertEquals((Double) storedParameter[0], 10.0, 0.1);
		assertEquals((Double) storedParameter[1], 15.0, 0.1);
	}

}
