/**
 * This code is use for training purpose in TMA Solution
 * Make a Pi number calculator
 */

package vn.com.tma.training.pi.algorithm.abstractclass;

/**
 * An interface of algorithm to calculate PI number. Concrete algorithms will
 * implement this and have own implementation. it extends Callable return a
 * IAlgorithm.
 * 
 * @author HNP
 * @version 1.0
 * @see Runnable
 */
public interface IAlgorithm {

	/**
	 * Set the end number to which the algorithm will stop
	 * 
	 * @param n
	 *            The number represent for the end of calculation
	 */
	public void setParameter(ParameterInput input);
	
	public double runAlgorithm();

	/**
	 * @return Sum of all the calculation from start number to end number
	 */
	public double getResult();	
}
