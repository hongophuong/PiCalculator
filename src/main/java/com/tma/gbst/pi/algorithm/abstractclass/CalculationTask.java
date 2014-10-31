package com.tma.gbst.pi.algorithm.abstractclass;

import java.util.concurrent.Callable;

public class CalculationTask implements Callable<IAlgorithm> {

	IAlgorithm algorithm;

	public void setAlgorithm(IAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	public void setAlgorithmInput(ParameterInput input) {
		if (algorithm == null) {
			throw new NullPointerException(
					"Please set concrete algorithm for task");
		}
		this.algorithm.setParameter(input);
	}

	public IAlgorithm call() throws Exception {
		algorithm.runAlgorithm();
		return algorithm;
	}
	
	/**
	 * Stop running algorithm.
	 */
	public void stop() {
		algorithm.stopAlgorithm();
		// System.out.println("stop calculation task");
	}
}
