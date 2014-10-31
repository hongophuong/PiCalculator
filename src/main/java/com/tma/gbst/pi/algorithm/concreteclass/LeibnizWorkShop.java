package com.tma.gbst.pi.algorithm.concreteclass;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.tma.gbst.pi.algorithm.abstractclass.AlgorithmTask;
import com.tma.gbst.pi.algorithm.abstractclass.AlgorithmWorkshop;
import com.tma.gbst.pi.algorithm.abstractclass.IAlgorithm;
import com.tma.gbst.pi.algorithm.abstractclass.ParameterInput;
import com.tma.gbst.pi.calculator.CalculatorParameter;

/**
 * Divide works into tasks, task will be done by workers. Works is calculations,
 * task is CalculationTask will have a part of all calculations, workers is
 * threads that ExcutorService supply
 * 
 * @author hngophuong
 * @see AlgorithmWorkshop
 */
public class LeibnizWorkshop implements AlgorithmWorkshop {
	private double nCalculations, nCalcEachThread;
	private int nThreads;
	private List<AlgorithmTask> taskPool;
	private ExecutorService executor; // To create thread pool
	private double pi; // store calculated result
	private double nCompletedCalculations;

	public void setParameter(ParameterInput input) {
		if (!input.istype(CalculatorParameter.TYPE)) {
			throw new InputMismatchException(
					"LeibnizWorkshop must take a CalculatorParameter object input");
		}
		CalculatorParameter parameter = (CalculatorParameter) input;
		this.nCalculations = parameter.getNCalculations();
		this.nThreads = parameter.getNThreads();
		this.nCalcEachThread = parameter.getCalcEachThread();
	}

	public double run() {
		double startNumber, endNumber, currentEndNumber;
		boolean stopped = false;
		pi = 4;
		nCompletedCalculations = 0;

		/*
		 * Create a threadpool have fixed number of threads
		 */
		executor = Executors.newFixedThreadPool(nThreads);

		taskPool = createTaskPool();

		Iterator<AlgorithmTask> taskIterator = taskPool.iterator();
		AlgorithmTask currentTask = null;
		try {
			LeibnizParameter input = new LeibnizParameter();
			currentEndNumber = 0;
			/*
			 * Loop until all calculations are completed. If taskList have
			 * enough task for threads in thread pool to do, then invokeAll
			 * these tasks. If the last remained calculation not enough to
			 * divide into task for each thread, then reduce size of taskList.
			 */
			while (nCompletedCalculations < nCalculations && !stopped) {
				if (taskIterator.hasNext()) {
					currentTask = taskIterator.next();
					startNumber = currentEndNumber;
					endNumber = startNumber + nCalcEachThread;

					if (currentEndNumber >= nCalculations) {
						taskIterator.remove();
						continue;
					} else if (endNumber > nCalculations) {
						endNumber = nCalculations;
					}

					currentEndNumber = endNumber;
					input.setParameters(startNumber + 1, endNumber);
					currentTask.setAlgorithmInput(input);
				} else {
					List<Future<IAlgorithm>> results = executor
							.invokeAll(taskPool);
					for (Future<IAlgorithm> result : results) {
						pi += result.get().getResult();
						nCompletedCalculations += result.get().getDoneNumber();
						/*
						 * When stop() method of algorithm is invoked,
						 * CalculationTask in task list turn on the sign
						 * stopping. All task is running continue to work but
						 * next task will be stopped
						 */
						if (result.get().getDoneNumber() < nCalcEachThread) {
							stopped = true;
							break;
						}
					}
					taskIterator = taskPool.iterator(); // reset index of task
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}

		/*
		 * Thread pool accept no more task
		 */
		executor.shutdown();

		// System.out.println("completed calculation: " +
		// nCompletedCalculations);
		// System.out.println("Finished all threads. PI: " + pi);
		// System.out.println("Result in Math.PI:        " + Math.PI);
		return pi;
	}

	public String showInfo() {
		return "Completed calculation: " + nCompletedCalculations
				+ " and current number PI: " + pi;
	}

	public void stop() {
		for (AlgorithmTask task : taskPool) {
			task.stop();
		}
	}

	private List<AlgorithmTask> createTaskPool() {
		/*
		 * Compute number of task
		 */
		int nTasks = (int) (nCalculations / nCalcEachThread);

		/*
		 * Create a fixed number of task instance, it equal to number of threads
		 * This help to not create too many task objects
		 */
		List<AlgorithmTask> tasks = new ArrayList<AlgorithmTask>();
		int taskListSize = Math.min(nTasks, nThreads);
		for (int i = 0; i < taskListSize; i++) {
			tasks.add(new LeibnizTask());
		}
		return tasks;
	}

}
