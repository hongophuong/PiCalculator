/**
 * This code is use for training purpose in TMA Solution
 * Make a Pi number calculator
 */

package vn.com.tma.training.pi.calculator;

import java.util.concurrent.ExecutorService;

import vn.com.tma.training.pi.algorithm.abstractclass.AlgorithmWorkShop;
import vn.com.tma.training.pi.algorithm.abstractclass.IAlgorithm;

/**
 * This class take parameter from user => check input => create thread pool =>
 * devide tasks pass to threads => update result and process => report result
 * 
 * @author HNP
 * @version 1.3
 * @see ExecutorService
 * @see IAlgorithm
 */
public class MultiThreadPiCalculator {
	private AlgorithmWorkShop workshop;

	public void setWorkShop(AlgorithmWorkShop workshop) {
		this.workshop = workshop;
	}

	public double calculate() {
		long startPoint = System.currentTimeMillis();
		double result = workshop.run();
		System.out.println("Eslapsed Time: "
				+ (System.currentTimeMillis() - startPoint));

		return result;
	}

	public void stopCalculation() {
		workshop.stop();
	}

}
