//package vn.com.tma.training.pi;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import vn.com.tma.training.pi.algorithm.abstractclass.IAlgorithm;
//import vn.com.tma.training.pi.algorithm.concreteclass.LeibnizAlgorithm;
//
//public class LeibnizAlgorithmTest {
//	
//	LeibnizAlgorithm leibnizTest;
//
//	@Before
//	public void setUp() throws Exception {
//		leibnizTest = new LeibnizAlgorithm();
//		leibnizTest.setStartNumber(0);
//		leibnizTest.setEndNumber(10);
//	}
//
//	@Test
//	public void testCall() throws Exception {
//		IAlgorithm result = leibnizTest.call();
//		assertEquals(11.0, result.getCurrentNumber(),0.1);
//	}
//
//	@Test
//	public void testGetEndNumber() {
//		assertEquals(10, leibnizTest.getEndNumber(),0.1);
//	}
//
//	@Test(expected = ArithmeticException.class)
//	public void testSetEndNumberException() {
//		leibnizTest.setEndNumber(-10);		
//	}
//	
//	@Test
//	public void testSetEndNumber() {
//		leibnizTest.setEndNumber(100);		
//		assertEquals(100, leibnizTest.getEndNumber(),0.1);
//	}
//
//	@Test
//	public void testGetSum() throws Exception {
//		leibnizTest.setEndNumber(0);
//		leibnizTest = (LeibnizAlgorithm) leibnizTest.call();
//		assertEquals(4.0, leibnizTest.getSum(),0.1);
//	}
//
//	@Test
//	public void testGetStartNumber() {
//		leibnizTest.setStartNumber(10);
//		assertEquals(10, leibnizTest.getStartNumber(),0.1);
//	}
//	
//	@Test (expected = ArithmeticException.class)
//	public void testSetStartNumberException1() {
//		leibnizTest.setEndNumber(10);
//		leibnizTest.setStartNumber(1000);
//		assertEquals(1000, leibnizTest.getStartNumber(),0.1);
//	}
//	
//	@Test (expected = ArithmeticException.class)
//	public void testSetStartNumberException() {
//		leibnizTest.setStartNumber(-1000);
//		assertEquals(1000, leibnizTest.getStartNumber(),0.1);
//	}
//
//	@Test
//	public void testReset() {
//		leibnizTest.reset();
//		assertEquals(0, leibnizTest.getStartNumber(),0.1);
//		assertEquals(0, leibnizTest.getEndNumber(),0.1);
//	}
//
//	@Test
//	public void testNewInstance() {
//		IAlgorithm temp = leibnizTest.newInstance();
//		assertEquals(LeibnizAlgorithm.class, temp.getClass());
//	}
//
//
//}
