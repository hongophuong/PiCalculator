/**
 * This code is use for training purpose in TMA Solution
 * Make a Pi number calculator
 */

package vn.com.tma.training.pi.calculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
	public static final int MAX_THREAD = 100;
	private CalculatorParameter parameters;
	private List<IAlgorithm> taskList; // Instead of creating new instance for
										// each task, create a list of task
										// which size equal number of thread in
										// thread pool
	private ExecutorService executor; // To create thread pool
	private BigDecimal MAX_DOUBLE = new BigDecimal(Double.MAX_VALUE); // To
																		// check
																		// number
	// overflow
	private double Pi = 0.0; // store calculated result
	private double completedCalculation = 0.0; // store number of calculation
												// have completed

	public int getNumberOfThread() {
		return nThread;
	}

	public double getCompletedCalculation() {
		return completedCalculation;
	}

	/**
	 * Set number of thread will be created. This number must > 0 and <
	 * MAX_THEAD. Throw a Arithmetic Exception if condition is not met.
	 * 
	 * @param nOfThread
	 * @exception ArithmeticException
	 */
	public void setNumberOfThread(int nOfThread) {
		if (nOfThread == 0) {
			this.nThread = Runtime.getRuntime().availableProcessors();
		} else if (nOfThread < 0 || nOfThread > MAX_THREAD) {
			throw new ArithmeticException("Number of Thread out of range!");
		} else {
			this.nThread = nOfThread;
		}
	}

	public double getNumberOfCalculation() {
		return nCalculation;
	}

	/**
	 * Set number of calculation (total of loop). This number must >=0 and <=
	 * Double.MAX_DOUBLE Throw a Arithmetic Exception if condition is not met.
	 * 
	 * @param nCalculation
	 * @see BigDecimal
	 * @exception ArithmeticException
	 */
	public void setNumberOfCalculation(BigDecimal nCalculation) {
		if (nCalculation.compareTo(BigDecimal.ZERO) >= 0
				&& nCalculation.compareTo(MAX_DOUBLE) <= 0) {
			this.nCalculation = nCalculation.doubleValue();
		} else {
			throw new ArithmeticException("Number of calculation out of range!");
		}
	}

	public double getCalcEachThread() {
		return nCalcEachThread;
	}

	/**
	 * Set number of calculation each thread will handle. This number must >=0
	 * and <= number of calculations. Throw a Arithmetic Exception if condition
	 * is not met.
	 * 
	 * @param nEachThread
	 * @see BigDecimal
	 * @exception ArithmeticException
	 */
	public void setCalcEachThread(BigDecimal nEachThread) {
		if (nCalculation == 0) {
			this.nCalcEachThread = 0;
		} else if (nEachThread.compareTo(BigDecimal.ZERO) < 0
				|| nEachThread.doubleValue() > nCalculation) {
			throw new ArithmeticException(
					"Number of calculations each thread out of range!(<= 0 or > number of calculation)");
		} else {
			this.nCalcEachThread = nEachThread.doubleValue();
		}
	}

	/**
	 * Calculate number PI, use master-slave mechanism, create threadpool and a
	 * number of tasks, some tasks can be process in the same time
	 * 
	 * @param numberOfThread
	 * @param numberOfCalculation
	 * @param calculationEachThread
	 * @param algorithm
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public double calculate(int numberOfThread, BigDecimal numberOfCalculation,
			BigDecimal calculationEachThread, IAlgorithm algorithm) {
		int nTask;
		double lowerBound;

		// Set parameter
		setNumberOfThread(numberOfThread);
		setNumberOfCalculation(numberOfCalculation);
		setCalcEachThread(calculationEachThread);
		Pi = 0;

		// Create a threadpool have fixed number of threads
		executor = Executors.newFixedThreadPool(getNumberOfThread());
		if (nCalculation == 0) {
			nTask = 1;
		} else {
			nTask = (int) (nCalculation / nCalcEachThread); // compute number of
		}
		new Thread(new Runnable() {

			public void run() {
				long startWaitToPrintTime = System.currentTimeMillis();
				while (!executor.isTerminated()) {
					if ((System.currentTimeMillis() - startWaitToPrintTime) >= 2000) {
						startWaitToPrintTime = System.currentTimeMillis();
						System.out.println("Current Pi = " + getPi());
					}
				}
			}
		}).start();
		// task
		// Create a fixed number of task instance, it equal to number of threads
		// This help to not create too many task objects
		taskList = new ArrayList<IAlgorithm>();
		int taskListSize = Math.min(nTask, nThread);
		for (int i = 0; i < taskListSize; i++) {
			taskList.add(algorithm.newInstance());
		}

		Iterator<IAlgorithm> taskIterator = taskList.iterator();
		IAlgorithm temp;
		try {
			for (int i = 0; i < nTask; i++) {
				// devide calculation for each task
				if (taskIterator.hasNext()) {
					lowerBound = i * nCalcEachThread;
					temp = taskIterator.next();
					temp.setStartNumber(lowerBound + (i == 0 ? 0 : 1));
					temp.setEndNumber(lowerBound + nCalcEachThread);
				}
				// when devide calculation for all task instance, let the thread
				// pool do these tasks
				if (((i + 1) % taskListSize) == 0) {
					List<Future<IAlgorithm>> results = executor
							.invokeAll(taskList);
					for (Future<IAlgorithm> result : results) {
						temp = result.get();
						Pi += temp.getSum(); // update PI
						// update amoumt completed calculation
						completedCalculation = temp.getEndNumber();
						temp.reset(); // reset task instance

					}
					taskIterator = taskList.iterator(); // reset index of task
				}
			}

			// the last task contain calculation may remain
			if ((nCalculation - completedCalculation) > 0) {
				taskList.get(0).setStartNumber(completedCalculation + 1);
				taskList.get(0).setEndNumber(nCalculation);
				Future<IAlgorithm> result = executor.submit(taskList.get(0));
				Pi += result.get().getSum();
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}
		executor.shutdown(); // Thread pool accept no more task

		System.out.println("completed calculation: " + completedCalculation);
		System.out.println("Finished all threads. PI: " + Pi);
		System.out.println("Result in Math.PI:        " + Math.PI);
		return getPi();

	}

	public void stopCalculation() {
		System.out.println("Stop Calculation");
		executor.shutdownNow();
		System.out.println("Calculator stopped. Number of calculations: "
				+ getCompletedCalculation() + ". PI = " + getPi());

	}

	public void pauseCalculation() {
		System.out.println("Pause! Current PI = " + getPi());
		for (IAlgorithm a : taskList) {
			a.pauseAlgorithm();
		}
	}

	public void continueCalculation() {
		System.out.println("Continue calculating!");
		for (IAlgorithm a : taskList) {
			a.continueAlgorithm();
		}
	}

	public double getPi() {
		return Pi;
	}

	public boolean isRunning() {
		if (executor == null) {
			return false;
		}
		if (executor.isTerminated()) {
			return false;
		}
		return true;
	}
}
