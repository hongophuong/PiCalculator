/**
 * This code is use for training purpose in TMA Solution
 * Make a Pi number calculator
 */

package vn.com.tma.training.pi.algorithm.concreteclass;

import java.util.InputMismatchException;
import java.util.List;

import vn.com.tma.training.pi.algorithm.abstractclass.IAlgorithm;
import vn.com.tma.training.pi.algorithm.abstractclass.ParameterInput;

/**
 * An concrete implementation of IAlgorithm using Leibniz's algorithm PI/4 =
 * (-1)^n/(2*n+1) n from 0 to unlimit <=> PI/4 = 1 - 1/3 + 1/5 - 1/7 + 1/9 - ...
 * (-1)^n/(n*2+1) .... Approximately calculate number PI limit by upper bound n
 * 
 * @author HNP
 * @version 1.2
 * @see IAlgorithm
 */
public class LeibnizAlgorithm implements IAlgorithm {

	private double endNumber = 0; // The number to which stop calculating
	private double startNumber = 0; // The number from which start calculating
	private double sum = 0.0; // the sum using Leibniz Formula
	private double denominator; // Mean (2*n+1) in Leibniz fomular

	public double runAlgorithm() {
		// If startNumber is odd then sign = -1 else sign = 1
		double sign = (startNumber % 2 == 0) ? 1 : -1;
		double endDenominator = 2 * endNumber + 1;

		for (denominator = 2 * startNumber + 1; denominator <= endDenominator; denominator += 2) {
			// Sum = (-1)^k/(2*k+1)
			sum += (sign / denominator);
			// Change the sign instead of using pow of (-1)^k
			sign *= -1.0;
		}
		sum = sum * 4;
		return sum;
	}

	public void setParameter(ParameterInput input) {
		if (!input.istype(LeibnizParameter.TYPE)) {
			throw new InputMismatchException(
					"Wrong input for Leibniz formular!");
		}
		List<?> parameters = input.getParameters();
		startNumber = (Double) parameters.get(0);
		endNumber = (Double) parameters.get(1);
		sum = 0;
	}

	public double getResult() {
		return sum;
	}

	public double getCurrentNumber() {
		return (denominator - 1) / 2;
	}

}
