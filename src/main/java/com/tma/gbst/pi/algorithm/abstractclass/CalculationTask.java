package com.tma.gbst.pi.algorithm.abstractclass;

import java.util.concurrent.Callable;

public class CalculationTask implements Callable<IAlgorithm> {

	private IAlgorithm algorithm;
	
	private CalculationTask(IAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	public void setAlgorithmInput(ParameterInput input) {
		this.algorithm.setParameter(input);
	}
	
	public static CalculationTask newInstance(IAlgorithm algorithm) {
		if (algorithm == null) {
			throw new NullPointerException(
					"Please set concrete algorithm for task");
		}
		CalculationTask task = new CalculationTask(algorithm);
		return task;
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
