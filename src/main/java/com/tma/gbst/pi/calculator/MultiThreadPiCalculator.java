/**
 * This code is use for training purpose in TMA Solution
 * Make a Pi number calculator
 */

package com.tma.gbst.pi.calculator;

import java.util.concurrent.ExecutorService;

import com.tma.gbst.pi.algorithm.abstractclass.AlgorithmWorkShop;
import com.tma.gbst.pi.algorithm.abstractclass.IAlgorithm;

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

	public void setWorkShop(AlgorithmWorkShop workshop,
			CalculatorParameter input) {
		if (workshop == null) {
			throw new NullPointerException(
					"Algorithm workshop can not be null!");
		}
		if (input == null) {
			throw new NullPointerException("Calculator Input can not be null!");
		}

		this.workshop = workshop;
		this.workshop.setParameter(input);
	}

	public double calculate() {
		long startPoint = System.currentTimeMillis();
		double result = workshop.run();
		System.out.println("Eslapsed Time: "
				+ (System.currentTimeMillis() - startPoint));

		return result;
	}

	public void showInfo() {
		System.out.println(workshop.showInfo());
	}

	public void stopCalculation() {
		workshop.stop();
	}

}
