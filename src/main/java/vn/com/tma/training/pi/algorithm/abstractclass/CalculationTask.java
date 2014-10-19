package vn.com.tma.training.pi.algorithm.abstractclass;

import java.util.concurrent.Callable;

public class CalculationTask implements Callable<Double> {

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

	public Double call() throws Exception {
		double result = algorithm.runAlgorithm();
		return result;
	}
	
	/**
	 * Stop running algorithm.
	 */
	void stopAlgorithm() {

	}
}
