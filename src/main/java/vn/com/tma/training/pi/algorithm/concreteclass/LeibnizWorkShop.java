package vn.com.tma.training.pi.algorithm.concreteclass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import vn.com.tma.training.pi.algorithm.abstractclass.AlgorithmWorkShop;
import vn.com.tma.training.pi.algorithm.abstractclass.CalculationTask;
import vn.com.tma.training.pi.algorithm.abstractclass.IAlgorithm;
import vn.com.tma.training.pi.calculator.CalculatorParameter;

public class LeibnizWorkShop implements AlgorithmWorkShop {
	private double nCalculation, nCalcEachThread;
	private int nThread;
	private List<CalculationTask> taskList;
	private ExecutorService executor; // To create thread pool
	private double Pi; // store calculated result
	private double nCompletedCalculation;

	public void setParameter(CalculatorParameter input) {
		List<?> parameters = input.getParameters();
		this.nCalculation = ((BigDecimal) parameters.get(0)).doubleValue();
		this.nThread = (Integer) parameters.get(1);
		this.nCalcEachThread = ((BigDecimal) parameters.get(2)).doubleValue();
	}

	public double run() {
		int nTask;
		double startNumber, endNumber, currentEndNumber;
		boolean stopped = false;
		Pi = 4;
		nCompletedCalculation = 0;

		/*
		 * Create a threadpool have fixed number of threads
		 */
		executor = Executors.newFixedThreadPool(nThread);

		/*
		 * Compute number of task
		 */
		nTask = (int) (nCalculation / nCalcEachThread);

		/*
		 * Create a fixed number of task instance, it equal to number of threads
		 * This help to not create too many task objects
		 */
		taskList = new ArrayList<CalculationTask>();
		int taskListSize = Math.min(nTask, nThread);
		for (int i = 0; i < taskListSize; i++) {
			CalculationTask temp = new CalculationTask();
			temp.setAlgorithm(new LeibnizAlgorithm());
			taskList.add(temp);
		}

		Iterator<CalculationTask> taskIterator = taskList.iterator();
		CalculationTask temp = null;
		try {
			LeibnizParameter input = new LeibnizParameter();
			List<Object> parameters = new ArrayList<Object>();
			currentEndNumber = 0;

			while (nCompletedCalculation < nCalculation && !stopped) {
				if (taskIterator.hasNext()) {
					temp = taskIterator.next();
					startNumber = currentEndNumber;
					endNumber = startNumber + nCalcEachThread;
					if (currentEndNumber >= nCalculation) {
						taskIterator.remove();
						continue;
					} else if (endNumber > nCalculation) {
						endNumber = nCalculation;
					}
					currentEndNumber = endNumber;
					parameters.add(startNumber + 1);
					parameters.add(endNumber);
					input.setParameters(parameters);
					temp.setAlgorithmInput(input);
					parameters.clear();
				} else {
					List<Future<IAlgorithm>> results = executor
							.invokeAll(taskList);
					for (Future<IAlgorithm> result : results) {
						Pi += result.get().getResult();
						nCompletedCalculation += result.get().getDoneNumber();
						if (result.get().getDoneNumber() < nCalcEachThread) {
							stopped = true;
							break;
						}
					}
					taskIterator = taskList.iterator(); // reset index of task
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		executor.shutdown(); // Thread pool accept no more task

		System.out.println("completed calculation: " + nCompletedCalculation);
		System.out.println("Finished all threads. PI: " + Pi);
		System.out.println("Result in Math.PI:        " + Math.PI);
		return Pi;

	}

	public void stop() {
		for (CalculationTask task : taskList) {
			task.stop();
		}
	}

}
