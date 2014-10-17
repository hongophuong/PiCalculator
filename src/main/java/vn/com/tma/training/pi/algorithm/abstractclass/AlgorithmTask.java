package vn.com.tma.training.pi.algorithm.abstractclass;

import java.util.concurrent.Callable;

public interface AlgorithmTask extends Callable<Double> {
	/**
	 * Pause the current running algorithm.
	 */
	void pauseAlgorithm();

	/**
	 * Continue running algorithm from where it was paused.
	 */
	void continueAlgorithm();

	/**
	 * Stop running algorithm.
	 */
	void stopAlgorithm();

}
