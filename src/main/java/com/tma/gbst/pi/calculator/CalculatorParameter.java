package com.tma.gbst.pi.calculator;

import java.math.BigDecimal;
import java.util.InputMismatchException;

import com.tma.gbst.pi.algorithm.abstractclass.ParameterInput;

/**
 * CalculatorParameter have a list of 3 parameter: a BigDecimal numberofLoop, a
 * Integer numberOfThread, a BigDecimal loopEachThread.
 * 
 * @author hngophuong
 *
 */
public class CalculatorParameter extends ParameterInput {

	public static final String TYPE = "calculator";
	private final BigDecimal MAX_DOUBLE = new BigDecimal(Double.MAX_VALUE);
	private final BigDecimal DEFAULT_LOOP_EACH_THREAD = new BigDecimal("1E8");
	public static final int MAX_THREAD = 100;

	/**
	 * By default, calculator take parameters [0,1,0] for
	 * [totalNumberOfLoop,numberofThead,loopEachThread]
	 */
	public CalculatorParameter() {
		this.parameters = new Object[3];
		for (int i = 0; i < parameters.length; i++) {
			parameters[i] = null;
		}
	}

	/**
	 * Allow some of the inputed parameters call be null. The null
	 * totalNumberOfLoop is not accepted. The null nOfThread will be the number
	 * of availableProcessors. The null loopEachThread will be set to
	 * DEFAULT_LOOP_EACH_THREAD or totalNumberOfLoop (if totalNumberOfLoop <
	 * DEFAULT_LOOP_EACH_THREAD)
	 * 
	 * @param parameters
	 */
	private void autoFillParameter(Object... parameters) {
		BigDecimal totalNumberOfLoop = (BigDecimal) parameters[0];
		BigDecimal loopEachThread = (BigDecimal) parameters[2];
		Integer numberOfThread = (Integer) parameters[1];

		if (totalNumberOfLoop == null) {
			throw new NullPointerException("Number of loop is null!");
		}
		if (numberOfThread == null || numberOfThread <= 0) {
			parameters[1] = Runtime.getRuntime().availableProcessors();
		}
		if (loopEachThread == null) {
			if (totalNumberOfLoop.compareTo(DEFAULT_LOOP_EACH_THREAD) > 0) {
				parameters[2] = DEFAULT_LOOP_EACH_THREAD;
			} else {
				parameters[2] = totalNumberOfLoop;

			}
		}
	}

	/**
	 * Check inputed parameters with some rules: those number can not be
	 * negative number, numberOfLoop can't be double number overflowed,
	 * loopEachThread can not bigger than numberOfThread, numberOfThread can not
	 * bigger than MAX_THREAD
	 * 
	 * @param numberOfLoop
	 * @param numberOfThread
	 * @param loopEachThread
	 * @return true if parameter is accepted
	 */
	private boolean checkCalculatorParameter(BigDecimal numberOfLoop,
			Integer numberOfThread, BigDecimal loopEachThread) {
		if (numberOfLoop.compareTo(MAX_DOUBLE) > 0
				|| loopEachThread.compareTo(MAX_DOUBLE) > 0) {
			throw new ArithmeticException("Double number is overflowed!");
		}
		if (numberOfThread <= 0 || numberOfThread > MAX_THREAD) {
			return false;
		}
		if (numberOfLoop.compareTo(loopEachThread) < 0
				|| loopEachThread.compareTo(BigDecimal.ZERO) < 0) {
			return false;
		}
		return true;
	}

	/**
	 * Check number of parameter in list must >=3, check null parameter, use
	 * autoFillParameter method and checkCalculatorParameter method
	 */
	@Override
	protected boolean checkParameter(Object... parameters) {
		if (parameters != null) {
			if (parameters.length >= 3) {
				autoFillParameter(parameters);
				if (parameters[0] instanceof BigDecimal
						&& parameters[1] instanceof Integer
						&& parameters[2] instanceof BigDecimal) {
					return checkCalculatorParameter((BigDecimal) parameters[0],
							(Integer) parameters[1], (BigDecimal) parameters[2]);
				} else {
					throw new InputMismatchException(
							"Wrong type of inputed parameter!");
				}
			}
		}
		return false;
	}

	@Override
	public boolean istype(String type) {
		return type.equals(TYPE);
	}
	
	public double getNCalculations() {
		return ((BigDecimal) parameters[0]).doubleValue();
	}

	public int getNThreads() {
		return (Integer) parameters[1];
	}

	public double getCalcEachThread() {
		return ((BigDecimal) parameters[2]).doubleValue();
	}

}
