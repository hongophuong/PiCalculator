package vn.com.tma.training.pi;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import vn.com.tma.training.pi.algorithm.concreteclass.LeibnizParameter;
import vn.com.tma.training.pi.calculator.CalculatorParameter;

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
	public void testSetParameters() {
		parameters.add(10.5);
		parameters.add(15.0);
		testParameter.setParameters(parameters);
	}
	
	@Test
	public void testSetParameters() {
		parameters.add(10.0);
		parameters.add(15.0);
		testParameter.setParameters(parameters);
	}

	@Test
	public void testGetParameters() {
		fail("Not yet implemented");
	}

}
