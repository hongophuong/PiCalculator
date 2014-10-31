package com.tma.gbst.pi.algorithm.abstractclass;

import java.util.concurrent.Callable;

public abstract class AlgorithmTask implements Callable<IAlgorithm> {

	private IAlgorithm algorithm;
	
	public AlgorithmTask() {
		this.algorithm = createAlgorithmObject();
	}

	public void setAlgorithmInput(ParameterInput input) {
		this.algorithm.setParameter(input);
	}
	
	protected abstract  IAlgorithm createAlgorithmObject();

	public IAlgorithm call() throws Exception {
		algorithm.runAlgorithm();
		return algorithm;
	}
		
	/**
	 * Stop running algorithm.
	 */
	public void stop() {
		algorithm.stopAlgorithm();
	}
}
