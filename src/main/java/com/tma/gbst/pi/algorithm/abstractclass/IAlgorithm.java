/**
 * This code is use for training purpose in TMA Solution
 * Make a Pi number calculator
 */

package com.tma.gbst.pi.algorithm.abstractclass;

/**
 * An interface of algorithm to calculate PI number. Concrete algorithms will
 * implement this and have own implementation.
 * 
 * @author HNP
 * @version 1.1
 * @see Runnable
 */
public interface IAlgorithm {

	/**
	 * Set parameters for algorithm, this input can not be null
	 * 
	 * @param input
	 *            The number represent for the end of calculation
	 */
	public void setParameter(ParameterInput input);

	public void runAlgorithm();

	public void stopAlgorithm();

	/**
	 * @return Sum of all the calculation of the inputed parameters
	 */
	public double getResult();

	public double getDoneNumber();
}
