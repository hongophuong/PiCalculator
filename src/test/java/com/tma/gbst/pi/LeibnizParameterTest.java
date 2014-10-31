package com.tma.gbst.pi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tma.gbst.pi.algorithm.concreteclass.LeibnizParameter;
import com.tma.gbst.pi.calculator.CalculatorParameter;

public class LeibnizParameterTest {

	LeibnizParameter testParameter;
	List<Object> parameters = new ArrayList<Object>();
	
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
		parameters.add(10.0);
		parameters.add(15.0);
		testParameter.setParameters(parameters);
		List<?> storedParameter = testParameter.getParameters();
		assertEquals((Double)storedParameter.get(0), 10.0, 0.1);
		assertEquals((Double)storedParameter.get(1), 15.0, 0.1);
	}
	
	@Test (expected = NullPointerException.class)
	public void testSetGetParametersNullException() {
		parameters.add(null);
		parameters.add(null);
		testParameter.setParameters(parameters);
		List<?> storedParameter = testParameter.getParameters();
		assertEquals((Double)storedParameter.get(0), 10.0, 0.1);
		assertEquals((Double)storedParameter.get(1), 15.0, 0.1);
	}

	@Test (expected = InputMismatchException.class)
	public void testSetGetParametersInputException() {
		parameters.add(new String("asd"));
		parameters.add(new Long (10));
		testParameter.setParameters(parameters);
		List<?> storedParameter = testParameter.getParameters();
		assertEquals((Double)storedParameter.get(0), 10.0, 0.1);
		assertEquals((Double)storedParameter.get(1), 15.0, 0.1);
	}

	
}
