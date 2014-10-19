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
	private CalculatorParameter parameters;
	private List<IAlgorithm> taskList;
	private ExecutorService executor; // To create thread pool

	private double Pi = 0.0; // store calculated result
	private double completedCalculation = 0.0; // store number of calculation
												// have completed

	public void setParameter(CalculatorParameter parameters) {
		this.parameters = parameters;
	}

	public double getCompletedCalculation() {
		return completedCalculation;
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
	public double calculate() {
		int nTask;
		double lowerBound;
		
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

			// the last task contain calculation may remained
			if ((nCalculation - completedCalculation) > 0) {
				taskList.get(0).setStartNumber(completedCalculation + 1);
				taskList.get(0).setEndNumber(nCalculation);
				Future<IAlgorithm> result = executor.submit(taskList.get(0));
				Pi += result.get().getSum();
			}
		} catch (Exception e) {
			// e.printStackTrace();
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

}
